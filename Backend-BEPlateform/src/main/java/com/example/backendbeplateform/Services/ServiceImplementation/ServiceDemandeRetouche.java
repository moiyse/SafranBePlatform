package com.example.backendbeplateform.Services.ServiceImplementation;

import com.example.backendbeplateform.DAO.Entities.Anomalie;
import com.example.backendbeplateform.DAO.Entities.DRE.DemandeRetouche;
import com.example.backendbeplateform.DAO.Repositories.AnomalieRepository;
import com.example.backendbeplateform.DAO.Repositories.DemandeRetoucheRepository;
import com.example.backendbeplateform.Services.ServiceInterfaces.IServiceDemandeRetouche;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceDemandeRetouche implements IServiceDemandeRetouche {


    @Autowired
    private DemandeRetoucheRepository demandeRetoucheRepo;
    @Autowired
    private AnomalieRepository anomalieRepo;


    @Override
    public List<DemandeRetouche> getAll() {
        return demandeRetoucheRepo.findAll();
    }

    @Override
    public DemandeRetouche getDemandeRetoucheById(Integer id) {
        return demandeRetoucheRepo.findById(id).get();
    }

    @Override
    public DemandeRetouche addDemandeRetouche(DemandeRetouche demandeRetouche) {
        return demandeRetoucheRepo.save(demandeRetouche);
    }

    @Override
    public DemandeRetouche updateDemandeRetouche(DemandeRetouche demandeRetouche) {
        return demandeRetoucheRepo.save(demandeRetouche);
    }

    @Override
    public void deleteDemandeRetouche(Integer id) {
        demandeRetoucheRepo.deleteById(id);
    }


    @Override
    public DemandeRetouche addDemandeRetoucheWithAnomalie(DemandeRetouche demandeRetouche, int idAnomalie){
        Optional<Anomalie> anomalieObject = anomalieRepo.findById(idAnomalie);
        DemandeRetouche lastDemandeRetouche = findLastDemandeRetouche();
        System.out.println("last Demande Retouche : "+lastDemandeRetouche);

        if(anomalieObject.isPresent()){
            demandeRetouche.setAnomalie(anomalieObject.get());
            if(lastDemandeRetouche != null){
                if(lastDemandeRetouche.getXValue() != 99 && lastDemandeRetouche.getYValue() == 999)
                {
                    if(lastDemandeRetouche.getYValue() != 999){
                        demandeRetouche.setYValue(lastDemandeRetouche.getYValue()+1);
                    }else{
                        demandeRetouche.setYValue(001);
                        demandeRetouche.setXValue(lastDemandeRetouche.getXValue()+1);
                    }
                }else{
                    demandeRetouche.setXValue(00);
                    demandeRetouche.setYValue(001);
                }
            }
            return demandeRetoucheRepo.save(demandeRetouche);
        }
        else
            throw new RuntimeException("No anomalie Present");
    }


    @Override
    public DemandeRetouche findLastDemandeRetouche() {
        List<DemandeRetouche> lastDemandes = demandeRetoucheRepo.findAllOrderedByIdDesc();

        if (!lastDemandes.isEmpty()) {
            return lastDemandes.get(0);
        } else {
            return null; // No records found
        }
    }

}
