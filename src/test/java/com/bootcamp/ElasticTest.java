package com.bootcamp;

import com.bootcamp.crud.PhaseCRUD;
import com.bootcamp.crud.ProjetCRUD;
import com.bootcamp.crud.RegionCRUD;
import com.bootcamp.crud.SecteurCRUD;
import com.bootcamp.entities.Phase;
import com.bootcamp.entities.Projet;
import com.bootcamp.entities.Region;
import com.bootcamp.entities.Secteur;
import com.rintio.elastic.client.ElasticClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.List;

public class ElasticTest {
    private final Logger LOG = LoggerFactory.getLogger(ElasticTest.class);


    @Test
    public void createIndexProjet()throws Exception{
        ElasticClient elasticClient = new ElasticClient();
        List<Projet> projets = ProjetCRUD.read();
        for (Projet projet : projets){
            elasticClient.creerIndexObjectNative("projets","projet",projet,projet.getId());
            LOG.info("projet "+projet.getNom()+" created");
        }
    }

    @Test
    public void createIndexPhase()throws Exception{
        ElasticClient elasticClient = new ElasticClient();
        List<Phase> Phases = PhaseCRUD.read();
        for (Phase Phase : Phases){
            elasticClient.creerIndexObjectNative("phases","phase",Phase,Phase.getId());
            LOG.info("Phase "+Phase.getNom()+" created");
        }
    }

    @Test
    public void createIndexRegion()throws Exception{
        ElasticClient elasticClient = new ElasticClient();
        List<Region> regions = RegionCRUD.read();
        for (Region region : regions){
            elasticClient.creerIndexObjectNative("regions","region",region,region.getId());
            LOG.info("region "+region.getNom()+" created");
        }
    }
}
