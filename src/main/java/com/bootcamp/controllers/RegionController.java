package com.bootcamp.controllers;

import com.bootcamp.entities.Region;
import com.bootcamp.helpers.ProjetWS;
import com.bootcamp.helpers.RegionWS;
import com.bootcamp.services.ProjetService;
import com.bootcamp.version.ApiVersions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
    public ResponseEntity<RegionWS> createRegion(@RequestBody Region region) throws SQLException {
        RegionWS result = projetService.createRegion(region);
        return new ResponseEntity<>(result, HttpStatus.OK);
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
    public ResponseEntity<List<RegionWS>> findAllRegions() throws Exception {
        HttpStatus httpStatus = null;
        List<RegionWS> regions = projetService.readAllRegions(request);
        httpStatus = HttpStatus.OK;
        return new ResponseEntity<>(regions, httpStatus);
    }

    /**
     * Get a region (location) by its id
     *
     * @param nom
     * @return region
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{nom}")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Read a region", notes = "Read a region")
    public ResponseEntity<RegionWS> readRegion(@PathVariable("nom") String nom) throws Exception {

        RegionWS region = new RegionWS();
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
     * Link or undo the link between the given region (location) and the given
     * project
     *
     * @param nom
     * @param idProjet
     * @return region
     * @throws SQLException
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/link/{idProjet}/{nom}")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Add a region to a projet", notes = "Add a region to a projet")
    public ResponseEntity<ProjetWS> addRegionToProject(@PathVariable("idProjet") int idProjet, @PathVariable("nom") String nom) throws Exception {
        ProjetWS projet = projetService.addRegion(nom, idProjet);
        return new ResponseEntity<>(projet, HttpStatus.OK);
    }

    /**
     * Undo the link between the given region (location) to the given project
     *
     * @param idProjet
     * @param nom
     * @return region
     * @throws SQLException
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/unlink/{idProjet}/{nomRegion}")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Remove a region from a projet", notes = "Remove a region from a projet")
    public ResponseEntity<ProjetWS> removeRegionFromProjet(@PathVariable("idProjet") int idProjet, @PathVariable("nomRegion") String nom) throws Exception {
        ProjetWS projet = projetService.removeRegion(nom, idProjet);
        return new ResponseEntity<>(projet, HttpStatus.OK);
    }

    /**
     * Delete a region by its id
     *
     * @param nom
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.DELETE)
    @ApiVersions({"1.0"})
    @ApiOperation(value = "delete the regions", notes = "delete a particular regions")
    public ResponseEntity<Boolean> deleteRegion(@RequestParam("nom") String nom) throws Exception {
        boolean done = projetService.deleteRegion(nom);
        return new ResponseEntity<>(done, HttpStatus.OK);
    }
}
