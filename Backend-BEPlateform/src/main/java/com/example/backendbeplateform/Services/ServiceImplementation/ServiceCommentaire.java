package com.example.backendbeplateform.Services.ServiceImplementation;

import com.example.backendbeplateform.DAO.Entities.Anomalie;
import com.example.backendbeplateform.DAO.Entities.Commentaire;
import com.example.backendbeplateform.DAO.Entities.User;
import com.example.backendbeplateform.DAO.Repositories.AnomalieRepository;
import com.example.backendbeplateform.DAO.Repositories.CommentaireRepository;
import com.example.backendbeplateform.DAO.Repositories.UserRepository;
import com.example.backendbeplateform.Services.ServiceInterfaces.IServiceCommentaire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceCommentaire implements IServiceCommentaire {

    @Autowired
    private CommentaireRepository commentaireRepo;

    @Autowired
    private AnomalieRepository anomalieRepo;

    @Autowired
    private UserRepository userRepo;

    @Override
    public List<Commentaire> getAll() {
        return commentaireRepo.findAll();
    }


    @Override
    public Commentaire getCommentaireById(Integer id) {
        return commentaireRepo.findById(id).get();
    }

    @Override
    public Commentaire addCommentaire(Commentaire commentaire) {
        /*if(commentaireRepo.findCommentaireByCodeCommentaire(commentaire.getCodeCommentaire()).isPresent()){
            throw new RuntimeException("Code Commentaire Exists");
        }else{
            return commentaireRepo.save(commentaire);
        }*/
        return commentaireRepo.save(commentaire);
    }

    @Override
    public Commentaire updateCommentaire(Commentaire commentaire) {
        return commentaireRepo.save(commentaire);
    }

    @Override
    public void deleteCommentaire(Integer id) {

        try{
            commentaireRepo.deleteById(id);
        }catch(Exception e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public Commentaire addCommentaireWithAnomalie(Commentaire commentaire, int idAnomalie, int idUser){
        Optional<Anomalie> anomalieObject = anomalieRepo.findById(idAnomalie);
        Optional<User> userObject = userRepo.findById(idUser);
        if(anomalieObject.isPresent() && userObject.isPresent()){

            commentaire.setAnomalie(anomalieObject.get());
                return commentaireRepo.save(commentaire);

        }
        else
            throw new RuntimeException("No anomalie or user found !!!");
    }
}
