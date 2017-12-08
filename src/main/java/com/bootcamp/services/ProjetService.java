package com.bootcamp.services;

import com.bootcamp.commons.constants.DatabaseConstants;
import com.bootcamp.commons.models.Criteria;
import com.bootcamp.commons.models.Criterias;
import com.bootcamp.crud.CommentaireCRUD;
import com.bootcamp.crud.ProjetCRUD;
import com.bootcamp.entities.Commentaire;
import com.bootcamp.entities.Phase;
import com.bootcamp.entities.Projet;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by darextossa on 11/27/17.
 */
@Component
public class ProjetService implements DatabaseConstants {

    public Projet create(Projet projet) throws SQLException {
        ProjetCRUD.create(projet);
        return projet;
    }
    public List<Projet> findAll() throws SQLException {
        return ProjetCRUD.read();
    }
    
    public Projet read(int id) throws SQLException {
        Criterias criterias = new Criterias();
        criterias.addCriteria(new Criteria("id", "=", id));
        Projet projet = ProjetCRUD.read(criterias).get(0);
        return projet;
    }

    public int getCountProject() throws SQLException {
        return ProjetCRUD.read().size();
    }


    public Phase getPhaseActuelle (Projet projet){
        Phase phaseActuelle = new Phase();

        for (Phase phase : projet.getPhases()){
            if(phase.isActif()){
                phaseActuelle = phase;
            }
        }
        return phaseActuelle;
    }

}
