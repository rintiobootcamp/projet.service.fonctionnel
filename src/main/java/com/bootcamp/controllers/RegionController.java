package com.bootcamp.controllers;

import com.bootcamp.entities.Projet;
import com.bootcamp.entities.Region;
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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author Bello
 */
@RestController("RegionController")
@RequestMapping("/regions")
@CrossOrigin(origins = "*")
@Api(value = "Region API", description = "Region API")
public class RegionController {

    @Autowired
    ProjetService projetService;
    
    @Autowired
    HttpServletRequest request;

    /**
     * Insert the given region (location) in the database
     *
     * @param region
     * @return region
     * @throws java.sql.SQLException
     */
    @RequestMapping(method = RequestMethod.POST)
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Create a new project location", notes = "Create a new project location")
    public ResponseEntity<Region> createRegion(@RequestBody Region region) throws SQLException {
       Region result = projetService.createRegion( region );
       return new ResponseEntity<>( result,HttpStatus.OK );
    }

    /**
     * Get all the regions (locations) in the database
     *
     * @return regions list
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Get list of regions", notes = "Get list of regions")
    public ResponseEntity<List<Region>> findAllRegions() throws Exception {
        HttpStatus httpStatus = null;
        List<Region> regions = projetService.readAllRegions(request);
        httpStatus = HttpStatus.OK;
        return new ResponseEntity<>(regions, httpStatus);
    }

    /**
     * Get a region (location) by its id
     *
     * @param id
     * @return region
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{nom}")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Read a region", notes = "Read a region")
    public ResponseEntity<Region> readRegion(@PathVariable("nom") String nom) {

        Region region = new Region();
        HttpStatus httpStatus = null;

        try {
            region = projetService.readRegion(nom);
            httpStatus = HttpStatus.OK;
        } catch (SQLException ex) {
            Logger.getLogger(RegionController.class.getName()).log(Level.SEVERE, null, ex);
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(region, httpStatus);
    }

    /**
     * Update the given region (location) in the database
     *
     * @param region
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.PUT)
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Update a region", notes = "update a region")
    public ResponseEntity<Boolean> updateRegion(@RequestBody @Valid Region region) throws Exception {
        boolean done = projetService.updateRegion(region);
        return new ResponseEntity<>(done, HttpStatus.OK);
    }

    /**
     * Link or undo the link between the given region (location) and the given project
     *
     * @param idRegion
     * @param idProjet
     * @return region
     * @throws SQLException
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/link/{idProjet}/{nom}")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Add a region to a projet", notes = "Add a region to a projet")
    public ResponseEntity<Projet> addRegionToProject(@PathVariable("idProjet") int idProjet, @PathVariable("nom") String nom) throws Exception {
        Projet projet = projetService.addRegion(nom, idProjet);
        return new ResponseEntity<>(projet, HttpStatus.OK);
    }

    /**
     * Undo the link between the given region (location) to the given project
     *
     * @param idProjet
     * @param idRegion
     * @return region
     * @throws SQLException
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/unlink/{idRegion}/{idProjet}")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Remove a region from a projet", notes = "Remove a region from a projet")
    public ResponseEntity<Projet> removeProjetFromRegion(@PathVariable("idProjet") int idProjet, @PathVariable("nom") String nom) throws Exception {
        Projet projet = projetService.removeRegion(nom, idProjet);
        return new ResponseEntity<>(projet, HttpStatus.OK);
    }

    /**
     * Delete a region by its id
     *
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiVersions({"1.0"})
    @ApiOperation(value = "delete the regions", notes = "delete a particular regions")
    public ResponseEntity<Boolean> deleteRegion(@PathVariable("nom") String nom) throws Exception {
        boolean done = projetService.deleteRegion(nom);
        return new ResponseEntity<>(done, HttpStatus.OK);
    }
}
