package com.example.backendbeplateform.DAO.Repositories;

import com.example.backendbeplateform.DAO.Entities.Anomalie;
import com.example.backendbeplateform.DAO.Entities.DRE.DemandeRetouche;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DemandeRetoucheRepository extends JpaRepository<DemandeRetouche, Integer> {

    @Query(value = "SELECT d FROM DemandeRetouche d ORDER BY d.idDemandeRetouche DESC")
    List<DemandeRetouche> findAllOrderedByIdDesc();

}
