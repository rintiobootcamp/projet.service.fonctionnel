package com.bootcamp.services;

import com.bootcamp.commons.constants.DatabaseConstants;
import com.bootcamp.commons.exceptions.DatabaseException;
import com.bootcamp.commons.models.Criteria;
import com.bootcamp.commons.models.Criterias;
import com.bootcamp.commons.ws.utils.RequestParser;
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
     * Count all the projects of the database
     *
     * @return count
     * @throws SQLException
     */
    public int getCountProject() throws SQLException {
        return ProjetCRUD.read().size();
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


    public Projet getByName(String nom) throws SQLException {
        Criterias criterias = new Criterias();
        criterias.addCriteria( new Criteria( "nom", "=", nom ) );
        List<Projet> projets = ProjetCRUD.read( criterias );
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

    //@Bignon: met a jour la liste de phase actuelles
    public List<Phase> setPhasesActuelles (Projet projet, Phase phase) throws Exception {
        projet.getPhasesActuelles().add(phase);
        this.update(projet);

        List<Phase> phasesActuelles = projet.getPhasesActuelles();

        return phasesActuelles;
    }



    //@Bignon : calcul du taux d'avancement par budget d'un projet
    public double avancementBudget(int id) throws SQLException {
        Projet projet = read(id);
        double taux = ( projet.getBudgetPrevisionnel() / projet.getCoutReel() ) ;

        return taux ;
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
     public List<HashMap<Phase, List<HashMap<String, Long>>>> avancementPhase ( int id) throws SQLException {
                List<HashMap<Phase, List<HashMap<String, Long>>>> toreturn = null;

                Projet projet = read( id );
                List<Phase> phaseActuelles = projet.getPhasesActuelles();

                for (int i = 0; i < phaseActuelles.size(); i++) {
                    HashMap<Phase, List<HashMap<String, Long>>> toadd = null;
                    List<HashMap<String, Long>> maps = null;
                    HashMap<String, Long> mapDebut = null;
                    HashMap<String, Long> mapFin = null;

                    if (projet.getDateDebutPrevisionnel() <= System.currentTimeMillis()) {
                        long md = Math.subtractExact( projet.getDateDebutPrevisionnel(), phaseActuelles.get( i ).getDateDebut() );

                        if (md >= 0) {
                            mapDebut.put( "Gain de temp de commencement :", md );
                            maps.add( mapDebut );
                            toadd.put( phaseActuelles.get( i ), maps );
                        } else {
                            mapDebut.put( "Temp de retard commencement:", md );
                            maps.add( mapDebut );
                            toadd.put( phaseActuelles.get( i ), maps );
                        }

                        long mf = Math.subtractExact( projet.getDateFinPrevisionnel(), phaseActuelles.get( i ).getDateFin() );

                        if (mf >= 0) {
                            mapFin.put( "Gain de temp de fin :", mf );
                            maps.add( mapFin );
                            toadd.put( phaseActuelles.get( i ), maps );
                        } else {
                            mapFin.put( "Temp de retard fin :", mf );
                            maps.add( mapFin );
                            toadd.put( phaseActuelles.get( i ), maps );
                        }

                    }
                    toreturn.add( toadd );

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
        public boolean exist ( int id) throws Exception {
            if (read( id ) != null)
                return true;
            return false;
        }

}



