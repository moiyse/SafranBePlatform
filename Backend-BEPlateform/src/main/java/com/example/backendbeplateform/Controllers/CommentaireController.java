package com.example.backendbeplateform.Controllers;

import com.example.backendbeplateform.DAO.Entities.Commentaire;
import com.example.backendbeplateform.DAO.Entities.DecisionProduitExtends.Decision;
import com.example.backendbeplateform.Services.ServiceInterfaces.IServiceAnomalie;
import com.example.backendbeplateform.Services.ServiceInterfaces.IServiceCommentaire;
import com.example.backendbeplateform.Services.ServiceInterfaces.IServiceCommentaire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200" )
@RestController
public class CommentaireController {

    @Autowired
    private IServiceCommentaire serviceCommentaire;


    @GetMapping("/commentaire/get")
    public List<Commentaire> GetAll() {
        return serviceCommentaire.getAll();
    }

    @GetMapping("/commentaire/get/{id}")
    public Commentaire Get(@PathVariable Integer id) {
        return serviceCommentaire.getCommentaireById(id);
    }

    @PostMapping("/commentaire/add")
    public Commentaire Post(@RequestBody Commentaire commentaire) {
        return serviceCommentaire.addCommentaire(commentaire);
    }

    @PutMapping("/commentaire/update")
    public Commentaire Update(@RequestBody Commentaire commentaire) {
        return serviceCommentaire.updateCommentaire(commentaire);
    }

    @DeleteMapping("/commentaire/delete/{id}")
    public void Delete(@PathVariable Integer id) {
        serviceCommentaire.deleteCommentaire(id);
    }

    @PostMapping("/commentaire/add/{idAnomalie}/{idUser}")
    public Commentaire addDecisionWithAnomalie(@RequestBody Commentaire commentaire, @PathVariable("idAnomalie") int idAnomalie, @PathVariable("idUser") int idUser){
        return serviceCommentaire.addCommentaireWithAnomalie(commentaire, idAnomalie,idUser);
    }


}
