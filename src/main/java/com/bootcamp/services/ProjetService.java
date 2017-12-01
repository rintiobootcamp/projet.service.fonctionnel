package com.bootcamp.services;

import com.bootcamp.commons.constants.DatabaseConstants;
import com.bootcamp.commons.models.Criteria;
import com.bootcamp.commons.models.Criterias;
import com.bootcamp.commons.ws.models.PhaseUWs;
import com.bootcamp.commons.ws.models.ProjetUWs;
import com.bootcamp.crud.ProjetCRUD;
import com.bootcamp.entities.Phase;
import com.bootcamp.entities.Projet;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by darextossa on 11/27/17.
 */
@Component
public class ProjetService implements DatabaseConstants {

    public List<Projet> findAll() throws SQLException {
        return ProjetCRUD.read();
    }
    
    public Projet read(int id) throws SQLException {
        Criterias criterias = new Criterias();
        criterias.addCriteria(new Criteria("id", "=", id));
        Projet projet = ProjetCRUD.read(criterias).get(0);
        return projet;
    }

//    public int getCountProject() throws SQLException {
//        return ProjetCRUD.read().size();
//    }
//
//    public ProjetUWs read(int id) throws SQLException {
//        ProjetUWs projetUWs = new ProjetUWs();
//        Criterias criterias = new Criterias();
//        criterias.addCriteria(new Criteria("id", "=", id));
//        List<Projet> projets = ProjetCRUD.read(criterias);
//
//        List<Phase> phases = new ArrayList<Phase>();
//        Projet projet = projets.get(0);
//
//        projetUWs = this.buildProjetUWs(projet);
//
//        return projetUWs;
//    }
//    
//    public Phase getPhaseActuelle (Projet projet){
//        Phase phaseActuelle = new Phase();
//        
//        for (Phase phase : projet.getPhases()){
//            if(phase.isActif()){
//                phaseActuelle = phase;
//            }
//        }
//        return phaseActuelle;
//    }
//    
//    public PhaseUWs buildPhaseUWs (Phase phase){
//        PhaseUWs phaseUWs = new PhaseUWs();
//        
//        phaseUWs.setId(phase.getId());
//        phaseUWs.setNom(phase.getNom());
//        phaseUWs.setDateDebut(phase.getDateDebut());
//        phaseUWs.setDateFin(phase.getDateFin());
//        
//        return phaseUWs;
//    }
//    
//    public ProjetUWs buildProjetUWs (Projet projet){
//        ProjetUWs projetUWs = new ProjetUWs();
//        Phase phaseActuelle = this.getPhaseActuelle(projet);
//        
//        projetUWs.setId(projet.getId());
//        projetUWs.setNom(projet.getNom());
//        projetUWs.setReference(projet.getReference());
//        projetUWs.setDescription(projet.getDescription());
//        projetUWs.setPhaseActuelle(this.buildPhaseUWs(phaseActuelle));
//        projetUWs.setBudgetPrevisionnel(projet.getBudgetPrevisionnel());
//        projetUWs.setBudgetReel(projet.getBudgetReel());
//        projetUWs.setCoutReel(projet.getCoutReel());
//        projetUWs.setDateDebutPrevisionnel(projet.getDateDebutPrevisionnel());
//        projetUWs.setDateDebutReel(projet.getDateDebutReel());
//        projetUWs.setDateFinPrevisionnel(projet.getDateFinPrevisionnel());
//        projetUWs.setDateFinReel(projet.getDateFinReel());
//        
//        return projetUWs;
//    }
}
