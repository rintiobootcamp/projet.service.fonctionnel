package com.bootcamp.controllers;

import com.bootcamp.commons.exceptions.DatabaseException;
import com.bootcamp.commons.ws.constants.CommonsWsConstants;
import com.bootcamp.entities.Commentaire;
import com.bootcamp.entities.Phase;
import com.bootcamp.entities.Projet;
import com.bootcamp.entities.Secteur;
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

import java.lang.reflect.Array;
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

@RestController("ProjetController")
@RequestMapping("/projets")
@CrossOrigin(origins = "*")
@Api(value = "Projet API", description = "Projet API")
public class ProjetController {

    @Autowired
    ProjetService projetService;

    @Autowired
    HttpServletRequest request;

    //@bignon control s'il n'existe pas de projet de meme nom dans le programme avant d'enregistrer
    //Bignon: mais je ne sais pas cmt envoyer une erreur ou cas ou ...
    @RequestMapping(method = RequestMethod.POST)
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Create a new project", notes = "Create a new project")
    public ResponseEntity<Projet> create(@RequestBody @Valid Projet projet) throws SQLException {

        HttpStatus httpStatus = null;
            if(!projetService.checkByName(projet.getNom(),projet.getIdProgramme())){
                try {
                projet = projetService.create(projet);
                httpStatus = HttpStatus.OK;
                } catch (SQLException ex) {
                    Logger.getLogger(ProjetController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }



        return new ResponseEntity<Projet>(projet, httpStatus);
    }


    @RequestMapping(method = RequestMethod.GET)
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Get list of projects", notes = "Get list of projects")
    public ResponseEntity<List<Projet>> findAll() throws Exception {
        HttpStatus httpStatus = null;
        List<Projet> projets = projetService.readAll(request);
        httpStatus = HttpStatus.OK;
        return new ResponseEntity<List<Projet>>(projets,httpStatus);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Read a projet", notes = "Read a projet")
    public ResponseEntity<Projet> read(@PathVariable int id) {

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

    @RequestMapping(method = RequestMethod.PUT)
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Update a projet", notes = "update a projet")
    public ResponseEntity<Boolean> update(@RequestBody @Valid Projet projet) throws Exception {
        boolean done =  projetService.update(projet);
        return new ResponseEntity<>(done, HttpStatus.OK);
    }

    //Bignon: cette methode met a jour la liste de phase actuelles
    @RequestMapping(method = RequestMethod.PUT, value="/phasesActuelles")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Update a projet currents phases", notes = "update a projet currents phases")
    public  ResponseEntity<List<Phase>> updatePhasesList(@RequestBody @Valid Projet projet, Phase phase ) throws Exception {

        List<Phase> phasesActuelles =  projetService.setPhasesActuelles(projet,phase);
        return new ResponseEntity<>(phasesActuelles, HttpStatus.OK);
    }

  
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiVersions({"1.0"})
    @ApiOperation(value = "delete Projets", notes = "delete a particular Projets")
    public ResponseEntity<Boolean> delete(@PathVariable int id) throws Exception, IllegalAccessException, DatabaseException, InvocationTargetException {
        if(projetService.exist(id));
        boolean done = projetService.delete(id);
        return new ResponseEntity<>(done, HttpStatus.OK);

    }

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

    //@Bignon
    @RequestMapping(method = RequestMethod.GET, value = "/stat")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Get statistics of a project", notes = "Get statistics of a project")
    public ResponseEntity<List<HashMap<String, Object>>> statistics(int id) throws SQLException {
        HttpStatus httpStatus = null;
        Object obj = projetService.avancementPhase(id).toArray();
        double taux = projetService.avancementBudget(id);

        List<HashMap<String, Object>> list = null;
        HashMap<String, Object> map = null;

        map.put("Taux budget", taux);
        list.add(map);
        map.put("temp par phase",obj);
        list.add(map);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }


}
