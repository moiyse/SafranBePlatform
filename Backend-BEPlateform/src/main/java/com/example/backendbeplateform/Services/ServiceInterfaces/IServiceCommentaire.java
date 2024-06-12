package com.example.backendbeplateform.Services.ServiceInterfaces;

import com.example.backendbeplateform.DAO.Entities.Commentaire;

import java.util.List;

public interface IServiceCommentaire {

    List<Commentaire> getAll();

    Commentaire getCommentaireById(Integer id);

    Commentaire addCommentaire(Commentaire commentaire);

    Commentaire updateCommentaire(Commentaire commentaire);

    void deleteCommentaire(Integer id);

    Commentaire addCommentaireWithAnomalie(Commentaire commentaire, int idAnomalie, int idUser);
}
