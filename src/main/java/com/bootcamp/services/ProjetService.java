package com.bootcamp.services;

import com.bootcamp.commons.constants.DatabaseConstants;
import com.bootcamp.commons.enums.EtatProjet;
import com.bootcamp.commons.exceptions.DatabaseException;
import com.bootcamp.commons.models.Criteria;
import com.bootcamp.commons.models.Criterias;
import com.bootcamp.commons.models.Rule;
import com.bootcamp.commons.ws.utils.RequestParser;
import com.bootcamp.crud.PhaseCRUD;
import com.bootcamp.crud.ProjetCRUD;
import com.bootcamp.entities.Phase;
import com.bootcamp.entities.Projet;

import com.bootcamp.helpers.PhaseStatHelper;
import com.bootcamp.helpers.ProjetStatHelper;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by darextossa on 11/27/17.
 */
@Component
public class ProjetService implements DatabaseConstants {

    /**
     * Insert the given project in the database
     *
     * @param projet
     * @return projet
     * @throws SQLException
     */
    public Projet create(Projet projet) throws SQLException {
        ProjetCRUD.create(projet);
        return projet;
    }

    /**
     * Insert the given phase (step) in the database
     *
     * @param phase
     * @return phase
     * @throws SQLException
     */
    public Phase createPhase(Phase phase) throws SQLException {
        PhaseCRUD.create(phase);
        return phase;
    }

    /**
     * Get a project by its id
     *
     * @param id
     * @return project
     * @throws SQLException
     */
    public Projet read(int id) throws SQLException {
        Criterias criterias = new Criterias();
        criterias.addCriteria(new Criteria("id", "=", id));
        Projet projet = ProjetCRUD.read(criterias).get(0);
        return projet;
    }

    /**
     * Get a phase by its id
     *
     * @param id
     * @return phase
     * @throws SQLException
     */
    public Phase readPhase(int id) throws SQLException {
        Criterias criterias = new Criterias();
        criterias.addCriteria(new Criteria("id", "=", id));
        Phase phase = PhaseCRUD.read(criterias).get(0);
        return phase;
    }

    /**
     * Update the given project in the database
     *
     * @param projet
     * @return
     * @throws Exception
     */
    public boolean update(Projet projet) throws Exception {
        return ProjetCRUD.update(projet);
    }

    /**
     * Update the given phase in the database
     *
     * @param phase
     * @return
     * @throws Exception
     */
    public boolean updatePhase(Phase phase) throws Exception {
        return PhaseCRUD.update(phase);
    }

    /**
     * Delete a project by its id
     *
     * @param id
     * @return
     * @throws Exception
     */
    public boolean delete(int id) throws Exception {
        Projet projet = read(id);
        return ProjetCRUD.delete(projet);
    }

    /**
     * Delete a phase (step) by its id
     *
     * @param id
     * @return
     * @throws Exception
     */
    public boolean deletePhase(int id) throws Exception {
        Phase phase = readPhase(id);
        return PhaseCRUD.delete(phase);
    }

    /**
     * Link the given phase (step) to the given project
     *
     * @param idPhase
     * @param idProjet
     * @return phase
     * @throws SQLException
     */
    public Phase addPhase(int idPhase, int idProjet) throws SQLException {
        Phase phase = this.readPhase(idPhase);
        Projet projet = this.read(idProjet);
        phase.setProjet(projet);

        try {
            this.updatePhase(phase);
        } catch (Exception ex) {
            Logger.getLogger(ProjetService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return phase;
    }

    /**
     * Undo the link between the given phase (step) to the given project
     *
     * @param idPhase
     * @return phase
     * @throws SQLException
     */
    public Phase removePhase(int idPhase) throws SQLException {
        Phase phase = this.readPhase(idPhase);
        phase.setProjet(null);

        try {
            this.updatePhase(phase);
        } catch (Exception ex) {
            Logger.getLogger(ProjetService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return phase;
    }

    /**
     * Get all the projects of the database matching the request
     *
     * @param request
     * @return projects list
     * @throws SQLException
     * @throws IllegalAccessException
     * @throws DatabaseException
     * @throws InvocationTargetException
     */
    public List<Projet> readAll(HttpServletRequest request) throws SQLException, IllegalAccessException, DatabaseException, InvocationTargetException {
        Criterias criterias = RequestParser.getCriterias(request);
        List<String> fields = RequestParser.getFields(request);
        List<Projet> projets = null;
        if (criterias == null && fields == null) {
            projets = ProjetCRUD.read();
        } else if (criterias != null && fields == null) {
            projets = ProjetCRUD.read(criterias);
        } else if (criterias == null && fields != null) {
            projets = ProjetCRUD.read(fields);
        } else {
            projets = ProjetCRUD.read(criterias, fields);
        }

        return projets;

    }
    
        /**
     * Get all the phases of the database matching the request
     *
     * @param request
     * @return phases list
     * @throws SQLException
     * @throws IllegalAccessException
     * @throws DatabaseException
     * @throws InvocationTargetException
     */
    public List<Phase> readAllPhases(HttpServletRequest request) throws SQLException, IllegalAccessException, DatabaseException, InvocationTargetException {
        Criterias criterias = RequestParser.getCriterias(request);
        List<String> fields = RequestParser.getFields(request);
        List<Phase> phases = null;
        if (criterias == null && fields == null) {
            phases = PhaseCRUD.read();
        } else if (criterias != null && fields == null) {
            phases = PhaseCRUD.read(criterias);
        } else if (criterias == null && fields != null) {
            phases = PhaseCRUD.read(fields);
        } else {
            phases = PhaseCRUD.read(criterias, fields);
        }

        return phases;

    }

    /**
     * Count all the projects of the database
     *
     * @return count
     * @throws SQLException
     */
    public int getCountProject() throws SQLException {
        return ProjetCRUD.read().size();
    }

    /**
     * Get the given project actual phases
     *
     * @param idProjet
     * @return phases list
     */
    public List<Phase> getPhasesActuelles(int idProjet) throws SQLException, IllegalAccessException, DatabaseException, InvocationTargetException {

        Criterias criterias = new Criterias();
//        criterias.addCriteria(new Criteria(new Rule("projet.id","=",idProjet),"AND"));
        criterias.addCriteria(new Criteria(new Rule("actif","=",true),null));

        List<Phase> phases = PhaseCRUD.read(criterias);
        List<Phase> phasesActuelles = new ArrayList<>();
            phasesActuelles.clear();
        for (Phase phase : phases) {
         if(phase.getProjet().getId() == idProjet){
            phasesActuelles.add(phase);
         }
             
        }
//
//    List<Phase> phases = PhaseCRUD.read();
//    List<Phase> phaseActuelles = null;
//        for (Phase phase : phases) {
//            if(phase.getProjet().getId() == idProjet && phase.isActif())
//                phaseActuelles.add(phase);
//        }


        return phasesActuelles;
    }

    //@Bignon: Activate or desactivate phase
    public void activateOrDesactivatePhase (int idPhase) throws Exception {
        Phase phase = readPhase(idPhase);
        if (phase.isActif())
            phase.setActif(false);
        else
            phase.setActif(true);
    }


    //@Bignon : calcul du taux d'avancement par budget d'un projet
    public double avancementBudget(int id) throws SQLException {
        Projet projet = read(id);
        double taux = (projet.getCoutReel() / projet.getBudgetPrevisionnel())*100;

        return taux;
    }

    //@Bignon: calcul du taux de financement Prive
    public double avancementFinancementPrive(int id) throws SQLException {
        Projet projet = read(id);
        double taux = (projet.getFinancementPriveReel() / projet.getFinancementPrivePrevisionnel())*100;

        return taux;
    }

    //@bignon: calcul du taux de financement Public
    public double avancementFinancementPublic(int id) throws SQLException {
        Projet projet = read(id);
        double taux = (projet.getFinancementPublicReel() / projet.getFinancementPublicPrevisionnel())*100;

        return taux;
    }
     //@bignon: temp de retard ou d'avancement de la phase
     public ProjetStatHelper timeStatistics(int id) throws SQLException, IllegalAccessException, DatabaseException, InvocationTargetException {
         ProjetStatHelper projetStatHelper = new ProjetStatHelper();
         
         List<PhaseStatHelper> phaseStatHelpers = new ArrayList<>();

         List<Phase> phasesActuelles = getPhasesActuelles(id);
         phasesActuelles.add(readPhase(1));
         
         for (int i = 0; i < phasesActuelles.size(); i++) {
             PhaseStatHelper phaseStatHelper = new PhaseStatHelper();
             Phase phaseActuelle = phasesActuelles.get(i);
            long tpD = Math.subtractExact(phaseActuelle.getDateDebutPrevisionnel(),phaseActuelle.getDateDebutReel());
             long tpF = Math.subtractExact(phaseActuelle.getDateFinPrevisionnel(),phaseActuelle.getDateFinReel());

             phaseStatHelper.setIdPhase(phaseActuelle.getId());
             phaseStatHelper.setNomPhase(phaseActuelle.getNom());

             if (tpD >= 0)
             phaseStatHelper.setTempAvanceDateDebut(tpD);
             else
             phaseStatHelper.setTempRetardDateDebut(-tpD);

             if (tpF >= 0)
                 phaseStatHelper.setTempAvanceDateFin(tpF);
             else
                 phaseStatHelper.setTempRetardDateDebutFin(-tpF);

             phaseStatHelpers.add(phaseStatHelper);

         }
         projetStatHelper.setPhaseStatHelperList(phaseStatHelpers);
         return projetStatHelper;
     }

     public void changeProjectstate(int idProjet, EtatProjet etatProjet) throws Exception {
        Projet projet =read(idProjet);
         projet.setEtat(etatProjet);

         update(projet);
     }

    // @Bignon cette methode me semble inutile
    public boolean exist(int id) throws Exception {
        if (read(id) != null) {
            return true;
        }
        return false;
    }

}
