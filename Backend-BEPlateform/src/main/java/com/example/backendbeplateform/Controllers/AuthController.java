package com.example.backendbeplateform.Controllers;


import com.example.backendbeplateform.DAO.Entities.User;
import com.example.backendbeplateform.DAO.Repositories.UserRepository;
import com.example.backendbeplateform.Services.ServiceInterfaces.IServiceUser;
import com.example.backendbeplateform.payload.request.LoginRequest;
import com.example.backendbeplateform.payload.request.SignupRequest;
import com.example.backendbeplateform.payload.response.JwtResponse;
import com.example.backendbeplateform.payload.response.MessageResponse;
import com.example.backendbeplateform.security.jwt.JwtUtils;
import com.example.backendbeplateform.security.services.UserDetailsImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

@CrossOrigin(origins = "http://localhost:4200" )
@RestController
public class AuthController {
    @Autowired
    private IServiceUser serviceUser;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;



    @PostMapping("/auth/login")
    public User login(@RequestBody User user){
        Optional<User> userObject = serviceUser.getUserByEmail(user.getEmail());
        System.out.println("user by email : "+userObject.get().getPassword() + "user from front : "+user.getPassword());
        if(userObject.isPresent())
        {
            if(userObject.get().getPassword().equals(user.getPassword()))
            {
                return userObject.get();
            }
            else
                throw new RuntimeException("wrong password");
        }
        else{
            throw new RuntimeException("wrong email");
        }

    }


    @PostMapping("/auth/signup")
    public User signup(@RequestBody User user){
        Optional<User> userObject = serviceUser.getUserByEmail(user.getEmail());
        if(!userObject.isPresent())
        {
            String randomPassword = generateRandomPassword(10); // Change 10 to the desired password length
            user.setPassword(randomPassword);
            return serviceUser.addUser(user);
        }
        else
        {
            throw new RuntimeException("email exists");
        }
    }


    private String generateRandomPassword(int length) {
        String allowedCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
        Random random = new Random();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(allowedCharacters.length());
            password.append(allowedCharacters.charAt(randomIndex));
        }

        return password.toString();
    }

    @PostMapping("/oauth/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) throws JsonProcessingException {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        System.out.println("userDetails : "+userDetails);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        System.out.println("ow : "+ow);
        String userDetailsJson = ow.writeValueAsString(userDetails);
        System.out.println("userDetailsJson : "+userDetailsJson);


        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetailsJson
                ));
    }

    @PostMapping("/oauth/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

        System.out.println("the result signuup"+signUpRequest);
        if (serviceUser.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        String randomPassword = generateRandomPassword(10); // Change 10 to the desired password length

        System.out.println("password generated is : "+randomPassword);

        // Create new user's account
        User user = new User(signUpRequest.getNom(),
                signUpRequest.getPrenom(),
                signUpRequest.getEmail(),
                encoder.encode(randomPassword),
                signUpRequest.getServiceStatus(),
                signUpRequest.getMatricule(),
                signUpRequest.getPoste(),
                signUpRequest.getUAP());




        userRepo.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

}
