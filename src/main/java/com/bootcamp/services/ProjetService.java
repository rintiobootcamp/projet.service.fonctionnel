package com.bootcamp.services;

import com.bootcamp.commons.constants.DatabaseConstants;
import com.bootcamp.commons.exceptions.DatabaseException;
import com.bootcamp.commons.models.Criteria;
import com.bootcamp.commons.models.Criterias;
import com.bootcamp.commons.ws.utils.RequestParser;
import com.bootcamp.crud.PhaseCRUD;
import com.bootcamp.crud.ProjetCRUD;
import com.bootcamp.entities.Phase;
import com.bootcamp.entities.Projet;

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
     * @param projet
     * @return phases list
     */
    public List<Phase> getPhasesActuelles(Projet projet) {
        List<Phase> phasesActuelles = new ArrayList<>();

        for (Phase phase : projet.getPhases()) {
            if (phase.isActif()) {
                phasesActuelles.add(phase);
            }
        }
        return phasesActuelles;
    }

    /**
     * Get a project knowing its name
     *
     * @param nom
     * @return phases list
     * @throws java.sql.SQLException
     */
    public Projet getByName(String nom) throws SQLException {
        Criterias criterias = new Criterias();
        criterias.addCriteria(new Criteria("nom", "=", nom));
        List<Projet> projets = ProjetCRUD.read(criterias);
        return projets.get(0);
    }

    //Bignon cette methode n'a pas de raison d'etre
//    public List<Phase>  getPhasesActuelles (Projet projet){
//        List<Phase> phasesActuelles = new ArrayList<>();
//
//        for (Phase phase : projet.getPhases()){
//            if(phase.isActif()){
//                phasesActuelles.add( phase);
//            }
//        }
//        return phasesActuelles;
//    }
//    //@Bignon: met a jour la liste de phase actuelles
//    public List<Phase> setPhasesActuelles(Projet projet, Phase phase) throws Exception {
//        projet.getPhasesActuelles().add(phase);
//        this.update(projet);
//
//        List<Phase> phasesActuelles = projet.getPhasesActuelles();
//
//        return phasesActuelles;
//    }
    //@Bignon : calcul du taux d'avancement par budget d'un projet
    public double avancementBudget(int id) throws SQLException {
        Projet projet = read(id);
        double taux = (projet.getBudgetPrevisionnel() / projet.getCoutReel());

        return taux;
    }

    /**
     * Check if the given project exist in the database
     *
     * @param projet
     * @return
     * @throws Exception
     */
    public boolean exist(Projet projet) throws Exception {
        if (getByName(projet.getNom()) != null) {
            return true;
        }
        return false;
    }

    // @bignon : calcul selon les cas le temp en gain ou en perte d'un projet par rapport a ses phases actuelles
    public List<HashMap<Phase, List<HashMap<String, Long>>>> avancementPhase(int id) throws SQLException {
        List<HashMap<Phase, List<HashMap<String, Long>>>> toreturn = null;

        Projet projet = read(id);
        List<Phase> phaseActuelles = projet.getPhasesActuelles();

        for (int i = 0; i < phaseActuelles.size(); i++) {
            HashMap<Phase, List<HashMap<String, Long>>> toadd = null;
            List<HashMap<String, Long>> maps = null;
            HashMap<String, Long> mapDebut = null;
            HashMap<String, Long> mapFin = null;

            if (projet.getDateDebutPrevisionnel() <= System.currentTimeMillis()) {
                long md = Math.subtractExact(projet.getDateDebutPrevisionnel(), phaseActuelles.get(i).getDateDebut());

                if (md >= 0) {
                    mapDebut.put("Gain de temp de commencement :", md);
                    maps.add(mapDebut);
                    toadd.put(phaseActuelles.get(i), maps);
                } else {
                    mapDebut.put("Temp de retard commencement:", md);
                    maps.add(mapDebut);
                    toadd.put(phaseActuelles.get(i), maps);
                }

                long mf = Math.subtractExact(projet.getDateFinPrevisionnel(), phaseActuelles.get(i).getDateFin());

                if (mf >= 0) {
                    mapFin.put("Gain de temp de fin :", mf);
                    maps.add(mapFin);
                    toadd.put(phaseActuelles.get(i), maps);
                } else {
                    mapFin.put("Temp de retard fin :", mf);
                    maps.add(mapFin);
                    toadd.put(phaseActuelles.get(i), maps);
                }

            }
            toreturn.add(toadd);

        }

        return toreturn;
    }

    //@Bignon: A supprimer car ce control est deja ajoute par moi dans la methode precedente
//    public boolean exist(Projet projet) throws Exception{
//        if(getByName(projet.getNom())!=null)
//            return true;
//        return false;
//    }
    // @Bignon cette methode me semble inutile
    public boolean exist(int id) throws Exception {
        if (read(id) != null) {
            return true;
        }
        return false;
    }

}
