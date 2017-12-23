package com.bootcamp.integration;

import com.bootcamp.commons.enums.RegionType;
import com.bootcamp.commons.utils.GsonUtils;
import com.bootcamp.entities.*;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jayway.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import static com.jayway.restassured.RestAssured.given;

/**
 * <h2> The integration test for Projet controller</h2>
 * <p>
 * In this test class,
 * the methods :
 * <ul>
 * <li>create a projet </li>
 * <li>get one projet by it's id</li>
 * <li>get all projet</li>
 * <li>And update a projet have been implemented</li>
 * </ul>
 * before  getting started , make sure , the projet fonctionnel service is deploy and running as well.
 * you can also test it will the online ruuning service
 * As this test interact directly with the local database, make sure that the specific database has been created
 * and all it's tables.
 * If you have data in the table,make sure that before creating a data with it's id, do not use
 * an existing id.
 * </p>
 */


public class RegionControllerIntegrationTest {
    private static Logger logger = LogManager.getLogger(ProjetControllerIntegrationTest.class);
    /**
     *The Base URI of categorie fonctionnal service,
     * it can be change with the online URI of this service.
     */
    private String BASE_URI = "http://localhost:8081/projet";

    /**
     * The path of the Projet controller, according to this controller implementation
     */
    private String PROJET_PATH ="/projets";

    private String REGION_PATH ="/regions";


    /**
     * This ID is initialize for create , getById, and update method,
     * you have to change it if you have a save data on this ID otherwise
     * a error or conflit will be note by your test.
     */
    private int projetId = 0;

    private int regionId = 0;

    /**
     * This method create a new projet with the given id
     * @see Projet#id
     * <b>you have to chenge the name of
     * the projet if this name already exists in the database
     * @see Projet#getNom()
     * else, the projet  will be created but not wiht the given ID.
     * and this will accure an error in the getById and update method</b>
     * Note that this method will be the first to execute
     * If every done , it will return a 200 httpStatus code
     * @throws Exception
     */

    @Test(priority = 0, groups = {"RegionTest"})
    public void createRegionTest() throws Exception{
        String createURI = BASE_URI+REGION_PATH;
        Region region = loadDataRegionFromJsonFile().get( 1 );
        Gson gson = new Gson();
        String regionData = gson.toJson( region );
        Response response = given()
                .log().all()
                .contentType("application/json")
                .body(regionData)
                .expect()
                .when()
                .post(createURI);
        regionId = gson.fromJson( response.getBody().print(),Region.class ).getId();
        logger.debug(response.getBody().prettyPrint());
        Assert.assertEquals(response.statusCode(), 200) ;



    }




    /**
     * This method create a new projet with the given id
     * @see Projet#id
     * <b>you have to chenge the name of
     * the projet if this name already exists in the database
     * @see Projet#getNom()
     * else, the projet  will be created but not wiht the given ID.
     * and this will accure an error in the getById and update method</b>
     * Note that this method will be the first to execute
     * If every done , it will return a 200 httpStatus code
     * @throws Exception
     */
    @Test(priority = 1, groups = {"RegionTest"})
    public void createProjetTest() throws Exception{
        String createURI = BASE_URI+PROJET_PATH;
        Projet projet = getProjetById( 1 );
        projet.setId( projetId );
        Gson gson = new Gson();
        String projetData = gson.toJson( projet );
        Response response = given()
                .log().all()
                .contentType("application/json")
                .body(projetData)
                .expect()
                .when()
                .post(createURI);
        projetId = gson.fromJson( response.getBody().print(),Projet.class ).getId();
        logger.debug(response.getBody().prettyPrint());
        Assert.assertEquals(response.statusCode(), 200) ;

    }


    /**
     * Get All the projets in the database
     * If every done , it will return a 200 httpStatus code
     * @throws Exception
     */
    @Test(priority = 2, groups = {"ProjetTest"})
    public void getAllProjetsTest()throws Exception{
        String getAllProjetURI = BASE_URI+PROJET_PATH;
        Gson gson =new Gson();
        Response response = given()
                .log().all()
                .contentType("application/json")
                .expect()
                .when()
                .get(getAllProjetURI);

        String text = response.getBody().print().toString();


        Type typeOfObjectsListNew = new TypeToken<List<Projet>>() {
        }.getType();
        List<Projet> projets = GsonUtils.getObjectFromJson(text, typeOfObjectsListNew);
        projetId = projets.get( 0 ).getId();

        logger.debug(response.getBody().prettyPrint());

        Assert.assertEquals(response.statusCode(), 200) ;

    }


    /**
     * Get All the regions in the database
     * If every done , it will return a 200 httpStatus code
     * @throws Exception
     */
    @Test(priority = 3, groups = {"RegionTest"})
    public void getAllRegionsTest()throws Exception{
        String getAllRegionURI = BASE_URI+REGION_PATH;
        Response response = given()
                .log().all()
                .contentType("application/json")
                .expect()
                .when()
                .get(getAllRegionURI);

        logger.debug(response.getBody().prettyPrint());

        Assert.assertEquals(response.statusCode(), 200) ;

    }

    /**
     * This method get a projet with the given id
     * @see Projet#id
     * <b>
     *     If the given ID doesn't exist it will log an error
     * </b>
     * Note that this method will be the second to execute
     * If every done , it will return a 200 httpStatus code
     * @throws Exception
     */

    @Test(priority =4, groups = {"RegionTest"})
    public void getRegionByNameTest() throws Exception{
        String regionName = "region 2";
        String getRegionById = BASE_URI+REGION_PATH+"/"+regionName;
        Response response = given()
                .log().all()
                .contentType("application/json")
                .expect()
                .when()
                .get(getRegionById);
        logger.debug(response.getBody().prettyPrint());
        Assert.assertEquals(response.statusCode(), 200) ;
    }



/*    @Test(priority = 5, groups = {"RegionTest"})
    public void enableRegionTest() throws Exception{
        String updateURI = BASE_URI+REGION_PATH+"/enable/"+regionId;
        Response response = given()
                .log().all()
                .contentType("application/json")
                .expect()
                .when()
                .put(updateURI);
        logger.debug(response.getBody().prettyPrint());
        Assert.assertEquals(response.statusCode(), 200) ;

    }*/



    @Test(priority = 5, groups = {"RegionTest"})
    public void addRegionToProjetTest() throws Exception{
        String regionName = "region 2";
        String updateURI = BASE_URI+REGION_PATH+"/link/"+projetId+"/"+regionName;
        Region region = loadDataRegionFromJsonFile().get( 1 );
        region.setId( regionId );
        Gson gson = new Gson();
        String regionData = gson.toJson( region );
        Response response = given()
                .log().all()
                .contentType("application/json")
                .body(regionData)
                .expect()
                .when()
                .put(updateURI);
        logger.debug(response.getBody().prettyPrint());
        Assert.assertEquals(response.statusCode(), 200) ;
    }

    /**
     * Update a projet with the given ID
     * <b>
     *     the projet must exist in the database
     *     </b>
     * Note that this method will be the third to execute
     * If every done , it will return a 200 httpStatus code
     * @throws Exception
     */

    @Test(priority = 6, groups = {"RegionTest"})
    public void updateRegionTest() throws Exception{
        String updateURI = BASE_URI+REGION_PATH;
        Region region = loadDataRegionFromJsonFile().get( 1 );
        region.setId( regionId );
        region.setNom( "region 3" );
        Gson gson = new Gson();
        String regionData = gson.toJson( region );
        Response response = given()
                .log().all()
                .contentType("application/json")
                .body(regionData)
                .expect()
                .when()
                .put(updateURI);

        logger.debug(response.getBody().prettyPrint());

        Assert.assertEquals(response.statusCode(), 200) ;

    }





    @Test(priority = 7, groups = {"RegionTest"})
    public void removeProjetFromRegionTest() throws Exception{
        String regionName = "region 3";
        String updateURI = BASE_URI+REGION_PATH+"/unlink/"+projetId+"/"+regionName;
        Response response = given()
                .log().all()
                .contentType("application/json")
                .expect()
                .when()
                .put(updateURI);
        logger.debug(response.getBody().prettyPrint());
        Assert.assertEquals(response.statusCode(), 200) ;
    }





    /**
     * Delete a region for the given Name
     * will return a 200 httpStatus code if OK
     * @throws Exception
     */
    @Test(priority = 8, groups = {"ProjetTest"})
    public void deleteRegionTest() throws Exception{
        String regionName = "region 3";
        String deleteRegionUI = BASE_URI+REGION_PATH;
        Response response = given()
                .log().all()
                .queryParam( "nom",regionName )
                .contentType("application/json")
                .expect()
                .when()
                .delete(deleteRegionUI);
        Assert.assertEquals(response.statusCode(), 200) ;
    }


    /**
     * Delete a projet for the given ID
     * will return a 200 httpStatus code if OK
     * @throws Exception
     */
    @Test(priority = 9, groups = {"ProjetTest"})
    public void deleteProjetTest() throws Exception{
        String deleteProjetUI = BASE_URI+PROJET_PATH+"/"+projetId;
        Response response = given()
                .log().all()
                .contentType("application/json")
                .expect()
                .when()
                .delete(deleteProjetUI);
        Assert.assertEquals(response.statusCode(), 200) ;
    }
    private int getLastRegiontId(List<Region> list) throws Exception {
        Region input = list.get( list.size() - 1 );
        return input.getId();
    }


    /**
     * Convert a relative path file into a File Object type
     * @param relativePath
     * @return  File
     * @throws Exception
     */
    private File getFile(String relativePath) throws Exception {

        File file = new File(getClass().getClassLoader().getResource(relativePath).toURI());

        if (!file.exists()) {
            throw new FileNotFoundException("File:" + relativePath);
        }

        return file;
    }


    public List<Region> loadDataRegionFromJsonFile() throws Exception {
        //TestUtils testUtils = new TestUtils();
        File dataFile = getFile("data-json" + File.separator + "regions.json");

        String text = Files.toString(new File(dataFile.getAbsolutePath()), Charsets.UTF_8);

        Type typeOfObjectsListNew = new TypeToken<List<Region>>() {
        }.getType();
        List<Region> regions = GsonUtils.getObjectFromJson(text, typeOfObjectsListNew);

        return regions;
    }

    /**
     * Convert a projets json data to a projet objet list
     * this json file is in resources
     * @return a list of projet in this json file
     * @throws Exception
     */

    private List<Projet> getProjectsFromJson() throws Exception {
        //TestUtils testUtils = new TestUtils();
        File dataFile = getFile( "data-json" + File.separator + "projets.json");

        String text = Files.toString(new File(dataFile.getAbsolutePath()), Charsets.UTF_8);

        Type typeOfObjectsListNew = new TypeToken<List<Projet>>() {
        }.getType();
        List<Projet> projets = GsonUtils.getObjectFromJson(text, typeOfObjectsListNew);

        return projets;
    }

    /**
     * Convert a secteurs json data to a secteur objet list
     * this json file is in resources
     * @return a list of secteur in this json file
     * @throws Exception
     */
    private List<Secteur> loadDataSecteurFromJsonFile() throws Exception {
        //TestUtils testUtils = new TestUtils();
        File dataFile = getFile( "data-json" + File.separator + "secteurs.json");

        String text = Files.toString(new File(dataFile.getAbsolutePath()), Charsets.UTF_8);

        Type typeOfObjectsListNew = new TypeToken<List<Secteur>>() {
        }.getType();
        List<Secteur> secteurs = GsonUtils.getObjectFromJson(text, typeOfObjectsListNew);

        return secteurs;
    }

    /**
     * Get on pilier by a given ID from the List of pilier
     * @param id
     * @return
     * @throws Exception
     */
    private Projet getProjetById(int id) throws Exception {
        List<Projet> projets = getProjectsFromJson();
        Projet projet = projets.stream().filter(item -> item.getId() == id).findFirst().get();
        return projet;
    }


    /**
     * Get on axe by a given ID from the List of axes
     * @param id
     * @return
     * @throws Exception
     */
    private Axe getAxeById(int id) throws Exception {
        List<Axe> axes = loadDataAxeFromJsonFile();
        Axe axe = axes.stream().filter(item -> item.getId() == id).findFirst().get();

        return axe;
    }


    /**
     * Get on secteur by a given ID from the List of secteurs
     * @param id
     * @return
     * @throws Exception
     */
    private Secteur getSecteurById(int id) throws Exception {
        List<Secteur> secteurs = loadDataSecteurFromJsonFile();
        Secteur secteur = secteurs.stream().filter(item -> item.getId() == id).findFirst().get();

        return secteur;
    }

    /**
     * Convert a axes json data to a axe objet list
     * this json file is in resources
     * @return a list of axe in this json file
     * @throws Exception
     */

    private List<Axe> loadDataAxeFromJsonFile() throws Exception {
        //TestUtils testUtils = new TestUtils();
        File dataFile = getFile( "data-json" + File.separator + "axes.json");

        String text = Files.toString(new File(dataFile.getAbsolutePath()), Charsets.UTF_8);

        Type typeOfObjectsListNew = new TypeToken<List<Axe>>() {
        }.getType();
        List<Axe> axes = GsonUtils.getObjectFromJson(text, typeOfObjectsListNew);

        for (int i = 0; i < axes.size(); i++) {
            Axe axe = axes.get(i);
            List<Secteur> secteurs = new LinkedList();
            switch (i) {
                case 0:
                    secteurs.add(getSecteurById(8));
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    secteurs.add(getSecteurById(1));
                    secteurs.add(getSecteurById(2));
                    secteurs.add(getSecteurById(5));
                    secteurs.add(getSecteurById(9));
                    break;
                case 4:
                    secteurs.add(getSecteurById(3));
                    break;
                case 5:
                    secteurs.add(getSecteurById(8));
                    break;
                case 6:
                    secteurs.add(getSecteurById(6));
                    break;
            }
            axe.setSecteurs(secteurs);
        }

        return axes;
    }

    /**
     * Convert a piliers json data to a pilier objet list
     * this json file is in resources
     * @return a list of pilier in this json file
     * @throws Exception
     */

    public List<Pilier> loadDataPilierFromJsonFile() throws Exception {
        //TestUtils testUtils = new TestUtils();
        File dataFile = getFile( "data-json" + File.separator + "piliers.json");

        String text = Files.toString(new File(dataFile.getAbsolutePath()), Charsets.UTF_8);

        Type typeOfObjectsListNew = new TypeToken<List<Pilier>>() {
        }.getType();
        List<Pilier> piliers = GsonUtils.getObjectFromJson(text, typeOfObjectsListNew);
        //List<Axe> axes = axeRepository.findAll();
        for (int i = 0; i < piliers.size(); i++) {
            List<Axe> axes = new LinkedList();
            Pilier pilier = piliers.get(i);
            switch (i) {
                case 0:
                    axes.add(getAxeById(1));
                    axes.add(getAxeById(2));
                    break;
                case 1:
                    axes.add(getAxeById(3));
                    axes.add(getAxeById(4));
                    axes.add(getAxeById(5));
                    break;
                case 2:
                    axes.add(getAxeById(6));
                    axes.add(getAxeById(7));
                    break;
            }
            pilier.setAxes(axes);
        }

        return piliers;
    }

}