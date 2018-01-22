package com.bootcamp.controllers;

import com.bootcamp.application.Application;
import com.bootcamp.commons.utils.GsonUtils;
import com.bootcamp.entities.*;
import com.bootcamp.helpers.PhaseWS;
import com.bootcamp.helpers.ProjetHelper;
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
@WebMvcTest(value = PhaseController.class, secure = false)
@ContextConfiguration(classes = {Application.class})
public class PhaseControllerTest {

    private static Logger logger = LogManager.getLogger(PhaseControllerTest.class);

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProjetService projetService;

    private ProjetHelper helper = new ProjetHelper();

    @Test
    public void getPhases() throws Exception {
        List<Phase> phases = loadDataPhaseFromJsonFile();
        List<PhaseWS> phaseWSs = helper.buildListPhaseWS(phases);

        HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
        when(projetService.readAllPhases(Mockito.any(HttpServletRequest.class))).thenReturn(phaseWSs);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/phases")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        logger.debug(response.getContentAsString());

        mockMvc.perform(requestBuilder).andExpect(status().isOk());

    }

    @Test
    public void getPhaseByIdTest() throws Exception {
        int id = 1;
        Phase phase = loadDataPhaseFromJsonFile().get(id);
        PhaseWS phaseWS = helper.buildPhaseWS(phase);

        when(projetService.readPhase(1)).thenReturn(phaseWS);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/phases/{id}", id)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        logger.debug(response.getContentAsString());
        mockMvc.perform(requestBuilder).andExpect(status().isOk());

    }

    @Test
    public void createPhaseTest() throws Exception {
        Phase phase = loadDataPhaseFromJsonFile().get(1);
        PhaseWS phaseWS = helper.buildPhaseWS(phase);

        when(projetService.createPhase(phase)).thenReturn(phaseWS);
        RequestBuilder requestBuilder
                = post("/phases")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(phase));

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        logger.debug(response.getContentAsString());

        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }

    @Test
    public void updatePhaseTest() throws Exception {
        int id = 1;
        Phase phase = loadDataPhaseFromJsonFile().get(id);
        phase.setNom("phase update");
        when(projetService.updatePhase(phase)).thenReturn(true);

        RequestBuilder requestBuilder
                = put("/phases")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(phase));

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        logger.debug(response.getContentAsString());

        mockMvc.perform(requestBuilder).andExpect(status().isOk());

    }

    @Test
    public void addPhaseToProjetTest() throws Exception {
        int idProjet = 1;
        int idPhase = 1;
        Phase phase = loadDataPhaseFromJsonFile().get(1);
        PhaseWS phaseWS = helper.buildPhaseWS(phase);

        when(projetService.addPhase(idPhase, idProjet)).thenReturn(phaseWS);

        RequestBuilder requestBuilder
                = put("/phases/link/{idProjet}/{idPhase}", idProjet, idPhase)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(phase));

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        logger.debug(response.getContentAsString());

        mockMvc.perform(requestBuilder).andExpect(status().isOk());

    }

    @Test
    public void removePrjetFromPhase() throws Exception {
        int idProjet = 1;
        int idPhase = 1;
        Phase phase = loadDataPhaseFromJsonFile().get(1);
        PhaseWS phaseWS = helper.buildPhaseWS(phase);

        when(projetService.removePhase(idPhase, idProjet)).thenReturn(phaseWS);

        RequestBuilder requestBuilder
                = put("/phases/unlink/{idPhase}/{idProjet}", idPhase, idProjet)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(phase));

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        logger.debug(response.getContentAsString());

        mockMvc.perform(requestBuilder).andExpect(status().isOk());

    }

    @Test
    public void deletePhaseTest() throws Exception {
        int id = 1;
        when(projetService.deletePhase(id)).thenReturn(true);

        RequestBuilder requestBuilder
                = delete("/phases/{id}", id)
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

    public List<Phase> loadDataPhaseFromJsonFile() throws Exception {
        //TestUtils testUtils = new TestUtils();
        File dataFile = getFile("data-json" + File.separator + "phases.json");

        String text = Files.toString(new File(dataFile.getAbsolutePath()), Charsets.UTF_8);

        Type typeOfObjectsListNew = new TypeToken<List<Phase>>() {
        }.getType();
        List<Phase> phases = GsonUtils.getObjectFromJson(text, typeOfObjectsListNew);

        return phases;
    }
}
