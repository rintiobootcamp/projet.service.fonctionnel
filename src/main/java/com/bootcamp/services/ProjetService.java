package com.bootcamp.services;

import com.bootcamp.client.NotificationClient;
import com.bootcamp.commons.constants.DatabaseConstants;
import com.bootcamp.commons.enums.Action;
import com.bootcamp.commons.enums.EtatProjet;
import com.bootcamp.commons.exceptions.DatabaseException;
import com.bootcamp.commons.models.Criteria;
import com.bootcamp.commons.models.Criterias;
import com.bootcamp.commons.models.Rule;
import com.bootcamp.commons.ws.usecases.pivotone.NotificationInput;
import com.bootcamp.commons.ws.utils.RequestParser;
import com.bootcamp.crud.PhaseCRUD;
import com.bootcamp.crud.ProjetCRUD;
import com.bootcamp.crud.RegionCRUD;
import com.bootcamp.entities.Phase;
import com.bootcamp.entities.Pilier;
import com.bootcamp.entities.Projet;
import com.bootcamp.entities.Region;

import com.bootcamp.helpers.PhaseStatHelper;
import com.bootcamp.helpers.PhaseWS;
import com.bootcamp.helpers.ProjetHelper;
import com.bootcamp.helpers.ProjetStatHelper;
import com.bootcamp.helpers.ProjetWS;
import com.bootcamp.helpers.RegionWS;
import java.io.IOException;

import com.rintio.elastic.client.ElasticClient;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;

@Component
public class ProjetService {
    ElasticClient elasticClient;
    NotificationClient client;
    ProjetHelper helper = new ProjetHelper();
    @PostConstruct
    public void ProjetService(){
        elasticClient = new ElasticClient();
    }

    public boolean createIndexProjet()throws Exception{
//        ElasticClient elasticClient = new ElasticClient();
        List<Projet> projets = ProjetCRUD.read();
        for (Projet projet : projets){
            elasticClient.creerIndexObjectNative("projets","projet",projet,projet.getId());
//            LOG.info("projet "+projet.getNom()+" created");
        }
        return true;
    }

    public boolean createIndexPhase()throws Exception{
//        ElasticClient elasticClient = new ElasticClient();
        List<Phase> Phases = PhaseCRUD.read();
        for (Phase Phase : Phases){
            elasticClient.creerIndexObjectNative("phases","phase",Phase,Phase.getId());
//            LOG.info("Phase "+Phase.getNom()+" created");
        }
        return true;
    }

    public boolean createIndexRegion()throws Exception{
//        ElasticClient elasticClient = new ElasticClient();
        List<Region> regions = RegionCRUD.read();
        for (Region region : regions){
            elasticClient.creerIndexObjectNative("regions","region",region,region.getId());
//            LOG.info("region "+region.getNom()+" created");
        }
        return  true;
    }
    /**
     * Loading Projet Web Service client
     */
    @PostConstruct
    public void init() {
        client = new NotificationClient();


    }

    /**
     * Insert the given project in the database
     *
     * @param projet
     * @return projet
     * @throws SQLException
     */
    public ProjetWS create(Projet projet) throws SQLException {
        ProjetCRUD.create(projet);
        NotificationInput input = new NotificationInput();
        input.setAction(Action.NEW_PROJECT);
        input.setEntityId(projet.getId());
        input.setEntityType("PROJET");
        input.setTitre(projet.getNom());
        try {
            client.sendNotification(input);
        } catch (IOException ex) {
            Logger.getLogger(ProjetService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return helper.buildProjetWS(projet);
    }

    /**
     * Insert the given phase (step) in the database
     *
     * @param phase
     * @return phase
     * @throws SQLException
     */
    public PhaseWS createPhase(Phase phase) throws SQLException {
        PhaseCRUD.create(phase);
        return helper.buildPhaseWS(phase);
    }

    /**
     * Get a project by its id
     *
     * @param id
     * @return project
     * @throws SQLException
     */
    public ProjetWS read(int id) throws Exception {
        Criterias criterias = new Criterias();
        criterias.addCriteria(new Criteria("id", "=", id));
//        Projet projet = ProjetCRUD.read(criterias).get(0);
        Projet projet = getAllProjet().stream().filter(t->t.getId()==id).findFirst().get();
        return helper.buildProjetWS(projet);
    }

    /**
     * Get a phase by its id
     *
     * @param id
     * @return phase
     * @throws SQLException
     */
    public PhaseWS readPhase(int id) throws Exception {
        Criterias criterias = new Criterias();
        criterias.addCriteria(new Criteria("id", "=", id));
        Phase phase = getAllPhase().stream().filter(t->t.getId()==id).findFirst().get();
//        Phase phase = PhaseCRUD.read(criterias).get(0);
        return helper.buildPhaseWS(phase);
    }

    /**
     * Update the given project in the database
     *
     * @param projet
     * @return
     * @throws Exception
     */
    public boolean update(Projet projet) throws Exception {
        return ProjetCRUD.update(projet);
    }

    /**
     * Update the given phase in the database
     *
     * @param phase
     * @return
     * @throws Exception
     */
    public boolean updatePhase(Phase phase) throws Exception {
        return PhaseCRUD.update(phase);
    }

    /**
     * Delete a project by its id
     *
     * @param id
     * @return
     * @throws Exception
     */
    public boolean delete(int id) throws Exception {
        Projet projet = helper.buildProjet(read(id));
        return ProjetCRUD.delete(projet);
    }

    /**
     * Delete a phase (step) by its id
     *
     * @param id
     * @return
     * @throws Exception
     */
    public boolean deletePhase(int id) throws Exception {
        Phase phase = helper.buildPhase(readPhase(id));
        return PhaseCRUD.delete(phase);
    }

    /**
     * Link the given phase (step) to the given project
     *
     * @param idPhase
     * @param idProjet
     * @return phase
     * @throws SQLException
     */
    public PhaseWS addPhase(int idPhase, int idProjet) throws Exception {
        List<Phase> phases = new ArrayList<>();

        Phase phase = helper.buildPhase(this.readPhase(idPhase));
        Projet projet = helper.buildProjet(this.read(idProjet));

        if (projet.getPhases().isEmpty()) {
            phases.add(phase);
            projet.setPhases(phases);
        } else {
            projet.getPhases().add(phase);
        }

        projet.setPhases(phases);

        this.update(projet);
        return helper.buildPhaseWS(phase);
    }

    /**
     * Undo the link between the given phase (step) to the given project
     *
     * @param idPhase
     * @param idProjet
     * @return phase
     * @throws SQLException
     */
    public PhaseWS removePhase(int idPhase, int idProjet) throws Exception {
        Phase phase = helper.buildPhase(this.readPhase(idPhase));
        Projet projet = helper.buildProjet(this.read(idProjet));
        int index = -1;
        for (Phase ph : projet.getPhases()) {
            if (ph.getId() == phase.getId()) {
                index = projet.getPhases().indexOf(ph);
            }
        }
        projet.getPhases().remove(index);
        this.update(projet);
        return helper.buildPhaseWS(phase);
    }

    /**
     * Link the given region (location) to the given project
     *
     * @param nomRegion
     * @param idProjet
     * @return phase
     * @throws SQLException
     */
    public ProjetWS addRegion(String nomRegion, int idProjet) throws Exception {
        Region region = helper.buildRegion(this.readRegion(nomRegion));
        Projet projet = helper.buildProjet(this.read(idProjet));
        projet.getRegions().add(region);
        this.update(projet);
        return helper.buildProjetWS(projet);
    }

    /**
     * Undo the link between the given region (location) to the given project
     *
     * @param nomRegion
     * @param idProjet
     * @return phase
     * @throws SQLException
     */
    public ProjetWS removeRegion(String nomRegion, int idProjet) throws Exception {
        Region region = helper.buildRegion(this.readRegion(nomRegion));
        Projet projet = helper.buildProjet(this.read(idProjet));
        int index = -1;

        for (Region rg : projet.getRegions()) {
            if (rg.getId() == region.getId()) {
                index = projet.getRegions().indexOf(rg);
                projet.getRegions().remove(index);
                break;
            }
        }
        this.update(projet);
        return helper.buildProjetWS(projet);
    }

    /**
     * Get all the projects of the database matching the request
     *
     * @param request
     * @return projects list
     * @throws SQLException
     * @throws IllegalAccessException
     * @throws DatabaseException
     * @throws InvocationTargetException
     */
    public List<ProjetWS> readAll(HttpServletRequest request) throws SQLException, Exception, DatabaseException, InvocationTargetException {
        Criterias criterias = RequestParser.getCriterias(request);
        List<String> fields = RequestParser.getFields(request);
        List<Projet> projets = new ArrayList<>();
        if (criterias == null && fields == null) {
            projets = getAllProjet();
        } else if (criterias != null && fields == null) {
            projets = ProjetCRUD.read(criterias);
        } else if (criterias == null && fields != null) {
            projets = ProjetCRUD.read(fields);
        } else {
            projets = ProjetCRUD.read(criterias, fields);
        }

        return helper.buildListProjetWS(projets);

    }

    public List<Projet> getAllProjet() throws Exception{
         elasticClient = new ElasticClient();
        List<Object> objects = elasticClient.getAllObject("projets");
        ModelMapper modelMapper = new ModelMapper();
        List<Projet> rest = new ArrayList<>();
        for(Object obj:objects){
            rest.add(modelMapper.map(obj,Projet.class));
        }
        return rest;
    }

//    public void createProjetIndex(Projet projet) throws Exception{
//        ElasticClient elasticClient = new ElasticClient();
//        elasticClient.creerIndexObject("projets","projet",projet,projet.getId());
//
//    }

    public List<Phase> getAllPhase() throws Exception{
         elasticClient = new ElasticClient();
        List<Object> objects = elasticClient.getAllObject("phases");
        ModelMapper modelMapper = new ModelMapper();
        List<Phase> rest = new ArrayList<>();
        for(Object obj:objects){
            rest.add(modelMapper.map(obj,Phase.class));
        }
        return rest;
    }

//    public void createPhaseIndex(Phase phase) throws Exception{
//        ElasticClient elasticClient = new ElasticClient();
//        elasticClient.creerIndexObject("phases","phase",phase,phase.getId());
//
//    }


    public List<Region> getAllRegion() throws Exception{
//         elasticClient = new ElasticClient();
        List<Object> objects = elasticClient.getAllObject("regions");
        ModelMapper modelMapper = new ModelMapper();
        List<Region> rest = new ArrayList<>();
        for(Object obj:objects){
            rest.add(modelMapper.map(obj,Region.class));
        }
        return rest;
    }
//
//    public void createRegionIndex(Region region) throws Exception{
//        ElasticClient elasticClient = new ElasticClient();
//        elasticClient.creerIndexObject("regions","region",region,region.getId());
//
//    }

    /**
     * Get all the phases of the database matching the request
     *
     * @param request
     * @return phases list
     * @throws SQLException
     * @throws IllegalAccessException
     * @throws DatabaseException
     * @throws InvocationTargetException
     */
    public List<PhaseWS> readAllPhases(HttpServletRequest request) throws Exception, IllegalAccessException, DatabaseException, InvocationTargetException {
        Criterias criterias = RequestParser.getCriterias(request);
        List<String> fields = RequestParser.getFields(request);
        List<Phase> phases = new ArrayList<>();
        if (criterias == null && fields == null) {
            phases = getAllPhase();
        } else if (criterias != null && fields == null) {
            phases = PhaseCRUD.read(criterias);
        } else if (criterias == null && fields != null) {
            phases = PhaseCRUD.read(fields);
        } else {
            phases = PhaseCRUD.read(criterias, fields);
        }

        return helper.buildListPhaseWS(phases);

    }

    /**
     * Count all the projects of the database
     *
     * @return count
     * @throws SQLException
     */
    public int getCountProject() throws Exception {
        return (int)getAllProjet().stream().count();
    }

    /**
     * Get the given project actual phases
     *
     * @param idProjet
     * @return phases list
     */
    public List<PhaseWS> getPhasesActuelles(int idProjet) throws SQLException, Exception, DatabaseException, InvocationTargetException {

        Criterias criterias = new Criterias();
//        criterias.addCriteria(new Criteria(new Rule("projet.id","=",idProjet),"AND"));
        criterias.addCriteria(new Criteria(new Rule("actif", "=", true), null));

//        List<Phase> phases = PhaseCRUD.read(criterias);
        List<Phase> phases = getAllPhase().stream().filter(t->t.isActif()).collect(Collectors.toList());
        List<Phase> phasesActuelles = new ArrayList<>();
        phasesActuelles.clear();
        for (Phase phase : phases) {
            if (phase.isActif()) {
                phasesActuelles.add(phase);
            }
        }
//
//    List<Phase> phases = PhaseCRUD.read();
//    List<Phase> phaseActuelles = null;
//        for (Phase phase : phases) {
//            if(phase.getProjet().getId() == idProjet && phase.isActif())
//                phaseActuelles.add(phase);
//        }

        return helper.buildListPhaseWS(phasesActuelles);
    }

    //@Bignon: Activate or desactivate phase
    public void activateOrDesactivatePhase(int idPhase) throws Exception {
        Phase phase = helper.buildPhase(readPhase(idPhase));
        if (phase.isActif()) {
            phase.setActif(false);
        } else {
            phase.setActif(true);
            try {
                this.updatePhase(phase);
            } catch (Exception ex) {
                Logger.getLogger(ProjetService.class.getName()).log(Level.SEVERE, null, ex);
            }

            NotificationInput input = new NotificationInput();
            input.setAction(Action.UPDATE_PROJECT_PHASE);
            input.setEntityId(phase.getProjet().getId());
            input.setEntityType("PROJET");
            input.setTitre(phase.getProjet().getNom());
            input.setAttributName("phase");
            input.setCurrentVersion(phase.getNom());

            try {
                client.sendNotification(input);
            } catch (IOException ex) {
                Logger.getLogger(ProjetService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //@Bignon : calcul du taux d'avancement par budget d'un projet
    public double avancementBudget(int id) throws Exception {
        Projet projet = helper.buildProjet(read(id));
        double taux = (projet.getCoutReel() / projet.getBudgetPrevisionnel()) * 100;

        return taux;
    }

    //@Bignon : calcul du taux de consommation du budget d'un projet
    public double consommationBudget(int id) throws Exception {
        Projet projet = helper.buildProjet(read(id));
        double taux = (projet.getCoutReel() / projet.getBudgetPrevisionnel()) * 100;

        return taux;
    }

    public double avancementFinancementPrive(int id) throws Exception {
        Projet projet = helper.buildProjet(read(id));
        double taux = (projet.getFinancementPriveReel() / projet.getFinancementPrivePrevisionnel()) * 100;

        return taux;
    }

    //@bignon: calcul du taux de financement Public
    public double avancementFinancementPublic(int id) throws Exception {
        Projet projet = helper.buildProjet(read(id));
        double taux = (projet.getFinancementPublicReel() / projet.getFinancementPublicPrevisionnel()) * 100;

        return taux;
    }

    //@bignon: temp de retard ou d'avancement de la phase
    public ProjetStatHelper timeStatistics(int id) throws SQLException, Exception, DatabaseException, InvocationTargetException {
        ProjetStatHelper projetStatHelper = new ProjetStatHelper();

        List<PhaseStatHelper> phaseStatHelpers = new ArrayList<>();

        List<Phase> phasesActuelles = helper.buildListPhase(getPhasesActuelles(id));
        phasesActuelles.add(helper.buildPhase(readPhase(1)));

        for (int i = 0; i < phasesActuelles.size(); i++) {
            PhaseStatHelper phaseStatHelper = new PhaseStatHelper();
            Phase phaseActuelle = phasesActuelles.get(i);
            long tpD = Math.subtractExact(phaseActuelle.getDateDebutPrevisionnel(), phaseActuelle.getDateDebutReel());
            long tpF = Math.subtractExact(phaseActuelle.getDateFinPrevisionnel(), phaseActuelle.getDateFinReel());

            phaseStatHelper.setIdPhase(phaseActuelle.getId());
            phaseStatHelper.setNomPhase(phaseActuelle.getNom());

            if (tpD >= 0) {
                phaseStatHelper.setTempAvanceDateDebut(tpD);
            } else {
                phaseStatHelper.setTempRetardDateDebut(-tpD);
            }

            if (tpF >= 0) {
                phaseStatHelper.setTempAvanceDateFin(tpF);
            } else {
                phaseStatHelper.setTempRetardDateDebutFin(-tpF);
            }

            phaseStatHelpers.add(phaseStatHelper);

        }
        projetStatHelper.setPhaseStatHelperList(phaseStatHelpers);
        return projetStatHelper;
    }

    public void changeProjectstate(int idProjet, EtatProjet etatProjet) throws Exception {
        Projet projet = helper.buildProjet(read(idProjet));
        projet.setEtat(etatProjet);
        update(projet);

        NotificationInput input = new NotificationInput();
        input.setAction(Action.UPDATE_PROJECT_ETAT);
        input.setEntityId(projet.getId());
        input.setEntityType("PROJET");
        input.setTitre(projet.getNom());
        input.setAttributName("etat");
        input.setCurrentVersion(etatProjet.name());

        System.out.println(input.getTitre());

        try {
            client.sendNotification(input);
        } catch (IOException ex) {
            Logger.getLogger(ProjetService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // @Bignon cette methode me semble inutile
    public boolean exist(int id) throws Exception {
        if (read(id) != null) {
            return true;
        }
        return false;
    }

    /**
     * Insert the given region (step) in the database
     *
     * @param region
     * @return region
     * @throws SQLException
     */
    public RegionWS createRegion(Region region) throws SQLException {
        RegionCRUD.create(region);
        return helper.buildRegionWS(region);
    }

    /**
     * Get a region by its id
     *
     * @param nom
     * @return region
     * @throws SQLException
     */
    public RegionWS readRegion(String nom) throws Exception {
//        Criterias criterias = new Criterias();
//        criterias.addCriteria(new Criteria("nom", "=", nom));
//        Region region = RegionCRUD.read(criterias).get(0);
        Region region = getAllRegion().stream().filter(t->t.getNom().equalsIgnoreCase(nom)).findFirst().get();
        return helper.buildRegionWS(region);
    }

    /**
     * Update the given region in the database
     *
     * @param region
     * @return
     * @throws Exception
     */
    public boolean updateRegion(Region region) throws Exception {
        return RegionCRUD.update(region);
    }

    /**
     * Delete a region (step) by its id
     *
     * @param nom
     * @return
     * @throws Exception
     */
    public boolean deleteRegion(String nom) throws Exception {
        Region region = helper.buildRegion(readRegion(nom));
        return RegionCRUD.delete(region);
    }

    /**
     * Get all the regions of the database matching the request
     *
     * @param request
     * @return regions list
     * @throws SQLException
     * @throws IllegalAccessException
     * @throws DatabaseException
     * @throws InvocationTargetException
     */
    public List<RegionWS> readAllRegions(HttpServletRequest request) throws SQLException, Exception, DatabaseException, InvocationTargetException {
        Criterias criterias = RequestParser.getCriterias(request);
        List<String> fields = RequestParser.getFields(request);
        List<Region> regions = null;
        if (criterias == null && fields == null) {
            regions = getAllRegion();
        } else if (criterias != null && fields == null) {
            regions = RegionCRUD.read(criterias);
        } else if (criterias == null && fields != null) {
            regions = RegionCRUD.read(fields);
        } else {
            regions = RegionCRUD.read(criterias, fields);
        }

        return helper.buildListRegionWS(regions);

    }



}
