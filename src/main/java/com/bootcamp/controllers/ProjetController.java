package com.bootcamp.controllers;

import com.bootcamp.commons.exceptions.DatabaseException;
import com.bootcamp.commons.ws.models.PilierUWs;
import com.bootcamp.entities.Projet;
import com.bootcamp.services.ProjetService;
import com.bootcamp.version.ApiVersions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;


@RestController("ProjetController")
@RequestMapping("/projet")
@Api(value = "Projet API", description = "Projet API")
public class ProjetController {

    @Autowired
    ProjetService projetService;
    @Autowired
    HttpServletRequest request;

    @RequestMapping(method = RequestMethod.GET, value = "/")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Get list of projects", notes = "Get list of projects")
    public ResponseEntity<List<Projet>> findAll() throws SQLException {

        HttpStatus httpStatus = null;

        List<Projet> projets = projetService.findAll(request);
        httpStatus = HttpStatus.OK;

        return new ResponseEntity<List<Projet>>(projets, httpStatus);
    }

}
