package com.bootcamp.controllers;

import com.bootcamp.application.Application;
import com.bootcamp.commons.utils.GsonUtils;
import com.bootcamp.entities.*;
import com.bootcamp.helpers.ProjetHelper;
import com.bootcamp.helpers.ProjetStatHelper;
import com.bootcamp.helpers.ProjetWS;
import com.bootcamp.services.ProjetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.reflect.TypeToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @author Ibrahim@abladon
 */
@RunWith(SpringRunner.class)
@WebMvcTest(value = ProjetController.class, secure = false)
@ContextConfiguration(classes = {Application.class})
public class ProjetControllerTest {

    private static Logger logger = LogManager.getLogger(ProjetControllerTest.class);

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProjetService projetService;

    private ProjetHelper helper = new ProjetHelper();

    @Test
    public void getProjets() throws Exception {
        List<Projet> projets = loadDataProjetFromJsonFile();
        List<ProjetWS> projetWSs = helper.buildListProjetWS(projets);
        System.out.println(projets.size());
        HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
        when(projetService.readAll(Mockito.any(HttpServletRequest.class))).thenReturn(projetWSs);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/projets")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        logger.debug(response.getContentAsString());

        mockMvc.perform(requestBuilder).andExpect(status().isOk());

    }

    @Test
    public void getProjetByIdTest() throws Exception {
        Projet projet = getProjetById(1);
        ProjetWS projetWS = helper.buildProjetWS(projet);

        when(projetService.read(1)).thenReturn(projetWS);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/projets/1")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        logger.debug(response.getContentAsString());

        mockMvc.perform(requestBuilder).andExpect(status().isOk());

    }

    @Test
    public void createProjetTest() throws Exception {
        Projet projet = loadDataProjetFromJsonFile().get(2);
        ProjetWS projetWS = helper.buildProjetWS(projet);

        when(projetService.create(projet)).thenReturn(projetWS);

        RequestBuilder requestBuilder
                = post("/projets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(projet));

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        logger.debug(response.getContentAsString());

        mockMvc.perform(requestBuilder).andExpect(status().isOk());

    }

    @Test
    public void updateProjettest() throws Exception {
        Projet projet = new Projet();
        projet.setNom("projet update");
        when(projetService.update(projet)).thenReturn(true);

        RequestBuilder requestBuilder
                = put("/projets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(projet));

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        logger.debug(response.getContentAsString());

        mockMvc.perform(requestBuilder).andExpect(status().isOk());

    }

    @Test
    public void countProjetTest() throws Exception {
        int count = loadDataProjetFromJsonFile().size();
        when(projetService.getCountProject()).thenReturn(count);

        RequestBuilder requestBuilder
                = get("/projets/count")
                        .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        logger.debug(response.getContentAsString());
        mockMvc.perform(requestBuilder).andExpect(status().isOk());

    }

    @Test
    public void getProjetStatTest() throws Exception {
        int id = 1;
        ProjetStatHelper projetStatHelper = new ProjetStatHelper();
        Projet projet = getProjetById(id);
        double tauxBA = (projet.getCoutReel() / projet.getBudgetPrevisionnel()) * 100;
        when(projetService.avancementBudget(id)).thenReturn(tauxBA);
        double tauxFPrive = (projet.getFinancementPriveReel() / projet.getFinancementPrivePrevisionnel()) * 100;
        when(projetService.avancementFinancementPrive(id)).thenReturn(tauxFPrive);
        double tauxtFPublic = (projet.getFinancementPublicReel() / projet.getFinancementPublicPrevisionnel()) * 100;
        when(projetService.avancementFinancementPublic(id)).thenReturn(tauxtFPublic);
        when(projetService.timeStatistics(id)).thenReturn(projetStatHelper);
        RequestBuilder requestBuilder
                = get("/projets/stats/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        logger.debug(response.getContentAsString());
        mockMvc.perform(requestBuilder).andExpect(status().isOk());

    }

    @Test
    public void deleteProjetTest() throws Exception {
        int id = 1;
        Projet projet = getProjetById(id);
        when(projetService.delete(id)).thenReturn(true);

        RequestBuilder requestBuilder
                = delete("/projets/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        logger.debug(response.getContentAsString());

        mockMvc.perform(requestBuilder).andExpect(status().isOk());

    }

    public static String objectToJson(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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

    public Projet getProjetById(int id) throws Exception {
        List<Projet> projets = loadDataProjetFromJsonFile();
        Projet projet = projets.stream().filter(item -> item.getId() == id).findFirst().get();

        return projet;
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
