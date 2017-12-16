package com.bootcamp.service;

import com.bootcamp.application.Application;
import com.bootcamp.commons.utils.GsonUtils;
import com.bootcamp.crud.ProjetCRUD;
import com.bootcamp.entities.Axe;
import com.bootcamp.entities.Pilier;
import com.bootcamp.entities.Projet;
import com.bootcamp.entities.Secteur;
import com.bootcamp.services.ProjetService;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.reflect.TypeToken;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by darextossa on 12/9/17.
 */

@RunWith(PowerMockRunner.class)
@WebMvcTest(value = ProjetService.class, secure = false)
@ContextConfiguration(classes = {Application.class})
@PrepareForTest(ProjetCRUD.class)
@PowerMockRunnerDelegate(SpringRunner.class)
public class ProjetServiceTest {
    private final Logger LOG = LoggerFactory.getLogger(ProjetServiceTest.class);

    @InjectMocks
    private ProjetService projetService;

    @Test
    public void getAllProjet() throws Exception {
        List<Projet> projets = loadDataProjetFromJsonFile();
        PowerMockito.mockStatic(ProjetCRUD.class);
        HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
        Mockito.
                when(ProjetCRUD.read()).thenReturn(projets);
        List<Projet> resultProjets = projetService.readAll(mockRequest);
        Assert.assertEquals(projets.size(), resultProjets.size());
        LOG.info(" get all projet test done");

    }

    @Test
    public void create() throws Exception{
        List<Projet> projets = loadDataProjetFromJsonFile();
        Projet projet = projets.get(1);

        PowerMockito.mockStatic(ProjetCRUD.class);
        Mockito.
                when(ProjetCRUD.create(projet)).thenReturn(true);
    }

    @Test
    public void delete() throws Exception{
        List<Projet> projets = loadDataProjetFromJsonFile();
        Projet projet = projets.get(1);

        PowerMockito.mockStatic(ProjetCRUD.class);
        Mockito.
                when(ProjetCRUD.delete(projet)).thenReturn(true);
    }

    @Test
    public void update() throws Exception{
        List<Projet> projets = loadDataProjetFromJsonFile();
        Projet projet = projets.get(1);

        PowerMockito.mockStatic(ProjetCRUD.class);
        Mockito.
                when(ProjetCRUD.update(projet)).thenReturn(true);
    }


    public File getFile(String relativePath) throws Exception {

        File file = new File(getClass().getClassLoader().getResource(relativePath).toURI());

        if (!file.exists()) {
            throw new FileNotFoundException("File:" + relativePath);
        }

        return file;
    }

    public List<Projet> getProjectsFromJson() throws Exception {
        //TestUtils testUtils = new TestUtils();
        File dataFile = getFile("data-json" + File.separator + "projets.json");

        String text = Files.toString(new File(dataFile.getAbsolutePath()), Charsets.UTF_8);

        Type typeOfObjectsListNew = new TypeToken<List<Projet>>() {
        }.getType();
        List<Projet> projets = GsonUtils.getObjectFromJson(text, typeOfObjectsListNew);

        return projets;
    }

    public List<Secteur> loadDataSecteurFromJsonFile() throws Exception {
        //TestUtils testUtils = new TestUtils();
        File dataFile = getFile("data-json" + File.separator + "secteurs.json");

        String text = Files.toString(new File(dataFile.getAbsolutePath()), Charsets.UTF_8);

        Type typeOfObjectsListNew = new TypeToken<List<Secteur>>() {
        }.getType();
        List<Secteur> secteurs = GsonUtils.getObjectFromJson(text, typeOfObjectsListNew);

        return secteurs;
    }

    private Secteur getSecteurById(int id) throws Exception {
        List<Secteur> secteurs = loadDataSecteurFromJsonFile();
        Secteur secteur = secteurs.stream().filter(item -> item.getId() == id).findFirst().get();

        return secteur;
    }

    public Axe getAxeById(int id) throws Exception {
        List<Axe> projets = loadDataAxeFromJsonFile();
        Axe projet = projets.stream().filter(item -> item.getId() == id).findFirst().get();

        return projet;
    }


    public List<Axe> loadDataAxeFromJsonFile() throws Exception {
        //TestUtils testUtils = new TestUtils();
        File dataFile = getFile("data-json" + File.separator + "projets.json");

        String text = Files.toString(new File(dataFile.getAbsolutePath()), Charsets.UTF_8);

        Type typeOfObjectsListNew = new TypeToken<List<Axe>>() {
        }.getType();
        List<Axe> projets = GsonUtils.getObjectFromJson(text, typeOfObjectsListNew);

        for (int i = 0; i < projets.size(); i++) {
            Axe projet = projets.get(i);
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
            projet.setSecteurs(secteurs);
        }

        return projets;
    }

    public List<Pilier> loadDataPilierFromJsonFile() throws Exception {
        //TestUtils testUtils = new TestUtils();
        File dataFile = getFile("data-json" + File.separator + "piliers.json");

        String text = Files.toString(new File(dataFile.getAbsolutePath()), Charsets.UTF_8);

        Type typeOfObjectsListNew = new TypeToken<List<Pilier>>() {
        }.getType();
        List<Pilier> piliers = GsonUtils.getObjectFromJson(text, typeOfObjectsListNew);
        for (int i = 0; i < piliers.size(); i++) {
            List<Axe> projets = new LinkedList();
            Pilier pilier = piliers.get(i);
            switch (i) {
                case 0:
                    projets.add(getAxeById(1));
                    projets.add(getAxeById(2));
                    break;
                case 1:
                    projets.add(getAxeById(3));
                    projets.add(getAxeById(4));
                    projets.add(getAxeById(5));
                    break;
                case 2:
                    projets.add(getAxeById(6));
                    projets.add(getAxeById(7));
                    break;
            }
            pilier.setAxes(projets);
        }

        return piliers;
    }


    public List<Projet> loadDataProjetFromJsonFile() throws Exception {
        //TestUtils testUtils = new TestUtils();
        File dataFile = getFile("data-json" + File.separator + "projets.json");

        String text = Files.toString(new File(dataFile.getAbsolutePath()), Charsets.UTF_8);

        Type typeOfObjectsListNew = new TypeToken<List<Projet>>() {
        }.getType();
        List<Projet> projets = GsonUtils.getObjectFromJson(text, typeOfObjectsListNew);

        return projets;
    }

}