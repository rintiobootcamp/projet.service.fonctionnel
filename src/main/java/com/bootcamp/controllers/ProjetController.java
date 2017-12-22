package com.bootcamp.controllers;

import com.bootcamp.commons.enums.EtatProjet;
import com.bootcamp.commons.exceptions.DatabaseException;
import com.bootcamp.commons.ws.constants.CommonsWsConstants;
import com.bootcamp.entities.Phase;
import com.bootcamp.entities.Projet;
import com.bootcamp.helpers.ProjetStatHelper;
import com.bootcamp.services.ProjetService;
import com.bootcamp.version.ApiVersions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Bello
 */
@RestController("ProjetController")
@RequestMapping("/projets")
@CrossOrigin(origins = "*")
@Api(value = "Projet API", description = "Projet API")
public class ProjetController {

    @Autowired
    ProjetService projetService;

    @Autowired
    HttpServletRequest request;

    /**
     * Insert the given project in the database
     *
     * @param projet
     * @return projet
     * @throws java.sql.SQLException
     */
    @RequestMapping(method = RequestMethod.POST)
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Create a new project", notes = "Create a new project")
    public ResponseEntity<Projet> create(@RequestBody Projet projet) throws SQLException {

        HttpStatus httpStatus = null;

        try {
            projet = projetService.create(projet);
            httpStatus = HttpStatus.OK;
        } catch (SQLException ex) {
            Logger.getLogger(ProjetController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ResponseEntity<Projet>(projet, httpStatus);
    }

    /**
     * Insert the given phase (step) in the database
     *
     * @param phase
     * @return phase
     * @throws java.sql.SQLException
     */
    @RequestMapping(method = RequestMethod.POST, value = "/phases")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Create a new project step", notes = "Create a new project step")
    public ResponseEntity<Phase> createPhase(@RequestBody Phase phase) throws SQLException {
       Phase result = projetService.createPhase( phase );
       return new ResponseEntity<>( result,HttpStatus.OK );
    }

    /**
     * Get all the projects in the database
     *
     * @return projects list
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Get list of projects", notes = "Get list of projects")
    public ResponseEntity<List<Projet>> findAll() throws Exception {
        HttpStatus httpStatus = null;
        List<Projet> projets = projetService.readAll(request);
        httpStatus = HttpStatus.OK;
        return new ResponseEntity<List<Projet>>(projets, httpStatus);
    }

    /**
     * Get all the phases (steps) in the database
     *
     * @return projects list
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET, value = "/phases")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Get list of phases", notes = "Get list of phases")
    public ResponseEntity<List<Phase>> findAllPhases() throws Exception {
        HttpStatus httpStatus = null;
        List<Phase> phases = projetService.readAllPhases(request);
        httpStatus = HttpStatus.OK;
        return new ResponseEntity<>(phases, httpStatus);
    }

    /**
     * Get a project by its id
     *
     * @param id
     * @return project
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Read a projet", notes = "Read a projet")
    public ResponseEntity<Projet> read(@PathVariable("id") int id) {

        Projet projet = new Projet();
        HttpStatus httpStatus = null;

        try {
            projet = projetService.read(id);
            httpStatus = HttpStatus.OK;
        } catch (SQLException ex) {
            Logger.getLogger(ProjetController.class.getName()).log(Level.SEVERE, null, ex);
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<Projet>(projet, httpStatus);
    }

    /**
     * Get a phase (step) by its id
     *
     * @param id
     * @return phase
     */
    @RequestMapping(method = RequestMethod.GET, value = "/phases/{id}")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Read a phase", notes = "Read a phase")
    public ResponseEntity<Phase> readPhase(@PathVariable("id") int id) {

        Phase phase = new Phase();
        HttpStatus httpStatus = null;

        try {
            phase = projetService.readPhase(id);
            httpStatus = HttpStatus.OK;
        } catch (SQLException ex) {
            Logger.getLogger(ProjetController.class.getName()).log(Level.SEVERE, null, ex);
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(phase, httpStatus);
    }

    /**
     * Update the given project in the database
     *
     * @param projet
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.PUT)
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Update a projet", notes = "update a projet")
    public ResponseEntity<Boolean> update(@RequestBody @Valid Projet projet) throws Exception {
        boolean done = projetService.update(projet);
        return new ResponseEntity<>(done, HttpStatus.OK);
    }

    /**
     * Update the given phase (step) in the database
     *
     * @param phase
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/phases")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Update a phase", notes = "update a phase")
    public ResponseEntity<Boolean> updatePhase(@RequestBody @Valid Phase phase) throws Exception {
        boolean done = projetService.updatePhase(phase);
        return new ResponseEntity<>(done, HttpStatus.OK);
    }

    /**
     * Link or undo the link between the given phase (step) and the given project
     *
     * @param idPhase
     * @param idProjet
     * @return phase
     * @throws SQLException
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/phases/create/{idProjet}/{idPhase}")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Add a phase to a projet", notes = "Add a phase to a projet")
    public ResponseEntity<Phase> addPhaseToProject(@PathVariable("idProjet") int idProjet, @PathVariable("idPhase") int idPhase) throws Exception {
        Phase phase = projetService.addPhase(idPhase, idProjet);
        return new ResponseEntity<>(phase, HttpStatus.OK);
    }

    /**
     * Undo the link between the given phase (step) to the given project
     *
     * @param idProjet
     * @param idPhase
     * @return phase
     * @throws SQLException
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/phases/delete/{idPhase}/{idProjet}")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Remove a phase from a projet", notes = "Remove a phase from a projet")
    public ResponseEntity<Phase> removeProjetFromPhase(@PathVariable("idProjet") int idProjet, @PathVariable("idPhase") int idPhase) throws Exception {
        Phase phase = projetService.removePhase(idPhase, idProjet);
        return new ResponseEntity<>(phase, HttpStatus.OK);
    }

//    //Bignon: cette methode met a jour la liste de phase actuelles
//    @RequestMapping(method = RequestMethod.PUT, value = "/phasesActuelles")
//    @ApiVersions({"1.0"})
//    @ApiOperation(value = "Update a projet currents phases", notes = "update a projet currents phases")
//    public ResponseEntity<List<Phase>> updatePhasesList(@RequestBody @Valid Projet projet, Phase phase) throws Exception {
//
//        List<Phase> phasesActuelles = projetService.setPhasesActuelles(projet, phase);
//        return new ResponseEntity<>(phasesActuelles, HttpStatus.OK);
//    }
    /**
     * Delete a project by its id
     *
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiVersions({"1.0"})
    @ApiOperation(value = "delete Projets", notes = "delete a particular Projets")
    public ResponseEntity<Boolean> delete(@PathVariable("id") int id) throws Exception {

        boolean done = projetService.delete(id);
        return new ResponseEntity<>(done, HttpStatus.OK);

    }

    /**
     * Delete a phase by its id
     *
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/phases/{id}", method = RequestMethod.DELETE)
    @ApiVersions({"1.0"})
    @ApiOperation(value = "delete the phases", notes = "delete a particular phases")
    public ResponseEntity<Boolean> deletePhase(@PathVariable("id") int id) throws Exception {
        boolean done = projetService.deletePhase(id);
        return new ResponseEntity<>(done, HttpStatus.OK);
    }

    /**
     * Count all the projects in the database
     *
     * @return count
     * @throws SQLException
     */
    @RequestMapping(method = RequestMethod.GET, value = "/count")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Get count of projects", notes = "Get count of projects")
    public ResponseEntity<HashMap<String, Integer>> count() throws SQLException {
        HttpStatus httpStatus = null;
        int count = projetService.getCountProject();
        HashMap<String, Integer> map = new HashMap<>();
        map.put(CommonsWsConstants.MAP_COUNT_KEY, count);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    /**
     * Get all the statistics relate to the given project
     *
     * @param id
     * @return
     * @throws SQLException
     */
    @RequestMapping(method = RequestMethod.GET, value = "/stats/{id}")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Get statistics of a project", notes = "Get statistics of a project")
    public ResponseEntity<ProjetStatHelper> statistics(@PathVariable("id") int id) throws SQLException {
        HttpStatus httpStatus = null;
        ProjetStatHelper projetStatHelper = new ProjetStatHelper();

        try {
            double tauxBudget = projetService.avancementBudget(id);
            double tauxFPrive = projetService.avancementFinancementPrive(id);
            double tauxtFPublic = projetService.avancementFinancementPublic(id);

            projetStatHelper = projetService.timeStatistics(id);
            projetStatHelper.setTauxBuget(tauxBudget);
            projetStatHelper.setTauxFinancementPrive(tauxFPrive);
            projetStatHelper.setTauxFinancementPublic(tauxtFPublic);

            httpStatus = HttpStatus.OK;
        } catch (SQLException ex) {
            Logger.getLogger(ProjetController.class.getName()).log(Level.SEVERE, null, ex);
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (DatabaseException e) {
            e.printStackTrace();
        }

        return new ResponseEntity(projetStatHelper, httpStatus);
    }

    /**
     * Update the state of the given project
     *
     * @param idProjet
     * @param etat
     * @return
     * @throws SQLException
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/etats/{idProjet}/{etat}")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Change the state of the project", notes = "Change the state of the project")
    public ResponseEntity<Void> setEtat(@PathVariable("idProjet") int idProjet, @PathVariable("etat") String etat) throws SQLException {
        HttpStatus httpStatus = null;
        
        EtatProjet etatProjet = EtatProjet.valueOf(etat);

        try{
            projetService.changeProjectstate(idProjet, etatProjet);
            httpStatus = HttpStatus.OK;
        } catch (Exception ex) {
            Logger.getLogger(ProjetController.class.getName()).log(Level.SEVERE, null, ex);
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(httpStatus);
    }
    
        /**
     * Enable or disable the given phase (step)
     *
     * @param idPhase
     * @return
     * @throws SQLException
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/phases/enable/{idPhase}")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Enable or disable the given phase", notes = "Enable or disable the given phase")
    public ResponseEntity<Void> activerPhase(@PathVariable("idPhase") int idPhase) throws SQLException {
        HttpStatus httpStatus = null;
        
        try{
            projetService.activateOrDesactivatePhase(idPhase);
            httpStatus = HttpStatus.OK;
        } catch (Exception ex) {
            Logger.getLogger(ProjetController.class.getName()).log(Level.SEVERE, null, ex);
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(httpStatus);
    }

}
