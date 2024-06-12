package com.example.backendbeplateform.DAO.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Commentaire implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idCommentaire")
    private int idCommentaire;

    private String commentaire;

    @ManyToOne
    private User user;

    @ManyToOne
    @JsonIgnore
    private Anomalie anomalie;
}
