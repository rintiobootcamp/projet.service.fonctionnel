package com.bootcamp.service;

import com.bootcamp.application.Application;
import com.bootcamp.commons.utils.GsonUtils;
import com.bootcamp.crud.PhaseCRUD;
import com.bootcamp.crud.ProjetCRUD;
import com.bootcamp.entities.*;
import com.bootcamp.helpers.ProjetWS;
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
import java.util.List;

/**
 * Created by darextossa on 12/9/17.
 */
@RunWith(PowerMockRunner.class)
@WebMvcTest(value = ProjetService.class, secure = false)
@ContextConfiguration(classes = {Application.class})
@PrepareForTest({ProjetCRUD.class, PhaseCRUD.class})
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
        List<ProjetWS> resultProjets = projetService.readAll(mockRequest);
        Assert.assertEquals(projets.size(), resultProjets.size());
        LOG.info(" get all projet test done");

    }

    @Test
    public void getAllPhase() throws Exception {
        List<Phase> phases = loadDataPhaseFromJsonFile();
        PowerMockito.mockStatic(PhaseCRUD.class);
        HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
        Mockito.
                when(PhaseCRUD.read()).thenReturn(phases);

    }

    private Projet getProjetById(int id) throws Exception {
        List<Projet> projets = loadDataProjetFromJsonFile();
        Projet projet = projets.stream().filter(item -> item.getId() == id).findFirst().get();
        return projet;
    }

    private Projet avancementBudget() throws Exception {
        Projet projet = getProjetById(1);
        double taux = (projet.getBudgetPrevisionnel() / projet.getCoutReel());

        // ...
        return projet;
    }

//    private Projet consommationBudget() throws Exception {
//        Projet projet = getProjetById(1);
//        double taux = (projet.getConsummedCost() / projet.getCoutReel());
//
//        return projet;
//    }

    @Test
    public void createProjet() throws Exception {
        List<Projet> projets = loadDataProjetFromJsonFile();
        Projet projet = projets.get(1);

        PowerMockito.mockStatic(ProjetCRUD.class);
        Mockito.
                when(ProjetCRUD.create(projet)).thenReturn(true);
    }

    @Test
    public void createPhase() throws Exception {
        List<Phase> phases = loadDataPhaseFromJsonFile();
        Phase phase = phases.get(1);
        PowerMockito.mockStatic(PhaseCRUD.class);
        Mockito.
                when(PhaseCRUD.create(phase)).thenReturn(true);
    }

    @Test
    public void deleteProjet() throws Exception {
        List<Projet> projets = loadDataProjetFromJsonFile();
        Projet projet = projets.get(1);

        PowerMockito.mockStatic(ProjetCRUD.class);
        Mockito.
                when(ProjetCRUD.delete(projet)).thenReturn(true);
    }

    @Test
    public void deletePhase() throws Exception {
        List<Phase> phases = loadDataPhaseFromJsonFile();
        Phase phase = phases.get(1);

        PowerMockito.mockStatic(PhaseCRUD.class);
        Mockito.
                when(PhaseCRUD.delete(phase)).thenReturn(true);
    }

    @Test
    public void updateProjet() throws Exception {
        List<Projet> projets = loadDataProjetFromJsonFile();
        Projet projet = projets.get(1);

        PowerMockito.mockStatic(ProjetCRUD.class);
        Mockito.
                when(ProjetCRUD.update(projet)).thenReturn(true);
    }

    @Test
    public void updatePhase() throws Exception {
        List<Phase> phases = loadDataPhaseFromJsonFile();
        Phase phase = phases.get(1);

        PowerMockito.mockStatic(PhaseCRUD.class);
        Mockito.
                when(PhaseCRUD.update(phase)).thenReturn(true);
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

    public List<Phase> loadDataPhaseFromJsonFile() throws Exception {
        //TestUtils testUtils = new TestUtils();
        File dataFile = getFile("data-json" + File.separator + "phases.json");

        String text = Files.toString(new File(dataFile.getAbsolutePath()), Charsets.UTF_8);

        Type typeOfObjectsListNew = new TypeToken<List<Phase>>() {
        }.getType();
        List<Phase> phases = GsonUtils.getObjectFromJson(text, typeOfObjectsListNew);

        return phases;
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
