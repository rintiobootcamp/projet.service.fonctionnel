package com.bootcamp;

import com.bootcamp.commons.ws.constants.CommonsWsConstants;
import com.bootcamp.controllers.ProjetController;
import com.bootcamp.entities.Projet;
import com.bootcamp.services.ProjetService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by ibrahim on 11/29/17.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(ProjetController.class)
public class ProjectControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProjetService projetService;

    @Test
    public void getAllProjetToJson() throws Exception {
        Projet projet = new Projet();
        projet.setNom("Projet 1");

        List<Projet> allProjets = Arrays.asList(projet);

        given(projetService.findAll()).willReturn(allProjets);

        mvc.perform(get("/projets")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$[0].nom",is(projet.getNom())));
    }

    @Test
    public void getProjetByIdToJson() throws Exception{
        Projet projet = new Projet();
        projet.setId(1);
        projet.setNom("Projet 1");

        given(projetService.read(1)).willReturn(projet);

        mvc.perform(get("/projets/1")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom",is(projet.getNom())));

   }

   @Test
    public void countProjet() throws Exception{
       Projet projet = new Projet();
       projet.setNom("Projet 1");
       List<Projet> allProjets = Arrays.asList(projet);
       int count = allProjets.size();
       HashMap<String, Integer> map = new HashMap<>();
       map.put("count",count);
       given(projetService.getCountProject()).willReturn(count);
       mvc.perform(get("/projets/count")
       .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$",is(map)));




   }
}
