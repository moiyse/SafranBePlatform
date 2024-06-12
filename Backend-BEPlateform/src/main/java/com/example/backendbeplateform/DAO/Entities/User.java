package com.example.backendbeplateform.DAO.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idUser")
    private int idUser;

    private String nom;

    private String prenom;

    private String email;

    private String password;

    private String signature;

    ///// to delete //////
    private int phoneNumber;

    private String matricule;

    private String poste;

    private String uap;

    @Enumerated(EnumType.STRING)
    private ServiceStatus serviceStatus;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Anomalie> Anomalies;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dateCreation" , updatable = false)
    private Date dateCreation;


    public User(String nom, String prenom, String email, String password, ServiceStatus serviceStatus,String matricule,String poste,String uap) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.serviceStatus = serviceStatus;
        this.matricule = matricule;
        this.poste = poste;
        this.uap = uap;
    }

    @PrePersist
    protected void onCreate() {
        dateCreation = new Date();
    }


}
