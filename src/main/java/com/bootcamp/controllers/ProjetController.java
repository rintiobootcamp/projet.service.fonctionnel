package com.bootcamp.controllers;

import com.bootcamp.commons.ws.constants.CommonsWsConstants;
import com.bootcamp.entities.Projet;
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

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.bind.annotation.PathVariable;

@RestController("ProjetController")
@RequestMapping("/projets")
@Api(value = "Projet API", description = "Projet API")
public class ProjetController {

    @Autowired
    ProjetService projetService;

    @RequestMapping(method = RequestMethod.GET)
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Get list of projects", notes = "Get list of projects")
    public ResponseEntity<List<Projet>> findAll() throws SQLException {
        HttpStatus httpStatus = null;
        List<Projet> projets = projetService.findAll();
        httpStatus = HttpStatus.OK;
        return new ResponseEntity<List<Projet>>(projets, httpStatus);
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

    @RequestMapping(method = RequestMethod.GET, value = "/count")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Get count of projects", notes = "Get count of projects")
    public ResponseEntity<HashMap<String, Integer>> count() throws SQLException {
        HttpStatus httpStatus = null;
        int count = projetService.getCountProject();
        HashMap<String, Integer> map = new HashMap<>();
        map.put(CommonsWsConstants.MAP_COUNT_KEY, count);

        return new ResponseEntity<HashMap<String, Integer>>(map, HttpStatus.OK);
    }

}
