/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.helpers;

import com.bootcamp.entities.Impact;
import com.bootcamp.entities.Objectif;
import com.bootcamp.entities.Phase;
import com.bootcamp.entities.Projet;
import com.bootcamp.entities.Region;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bello
 */
public class ProjetHelper {

    public ProjetWS buildProjetWS(Projet projet) {
        ProjetWS projetWS = new ProjetWS();

        projetWS.setId(projet.getId());
        projetWS.setNom(projet.getNom());
        projetWS.setIdProgramme(projet.getIdProgramme());
        projetWS.setIdSecteur(projet.getIdSecteur());
        projetWS.setReference(projet.getReference());
        projetWS.setDescription(projet.getDescription());
        projetWS.setEtat(projet.getEtat());
        projetWS.setBudgetPrevisionnel(projet.getBudgetPrevisionnel());
        projetWS.setCoutReel(projet.getCoutReel());
        projetWS.setDateDebutPrevisionnel(projet.getDateDebutPrevisionnel());
        projetWS.setDateFinPrevisionnel(projet.getDateFinPrevisionnel());
        projetWS.setDateDebutReel(projet.getDateDebutReel());
        projetWS.setDateFinReel(projet.getDateFinReel());
        projetWS.setFinancementPrivePrevisionnel(projet.getFinancementPrivePrevisionnel());
        projetWS.setFinancementPublicPrevisionnel(projet.getFinancementPublicPrevisionnel());
        projetWS.setFinancementPriveReel(projet.getFinancementPriveReel());
        projetWS.setFinancementPublicReel(projet.getFinancementPublicReel());

        if (projet.getImpactList() != null) {
            projetWS.setImpactList(this.buildListImpactWS(projet.getImpactList()));
        }
        if (projet.getObjectifList() != null) {
            projetWS.setObjectifList(this.buildListObjectifWS(projet.getObjectifList()));
        }
        if (projet.getPhases() != null) {
            projetWS.setPhases(this.buildListPhaseWS(projet.getPhases()));
        }
        if (projet.getRegions() != null) {
            projetWS.setRegions(this.buildListRegionWS(projet.getRegions()));
        }

        return projetWS;
    }

    public Projet buildProjet(ProjetWS projetWS) {
        Projet projet = new Projet();

        projet.setId(projetWS.getId());
        projet.setNom(projetWS.getNom());
        projet.setIdProgramme(projetWS.getIdProgramme());
        projet.setIdSecteur(projetWS.getIdSecteur());
        projet.setReference(projetWS.getReference());
        projet.setDescription(projetWS.getDescription());
        projet.setEtat(projetWS.getEtat());
        projet.setBudgetPrevisionnel(projetWS.getBudgetPrevisionnel());
        projet.setCoutReel(projetWS.getCoutReel());
        projet.setDateDebutPrevisionnel(projetWS.getDateDebutPrevisionnel());
        projet.setDateFinPrevisionnel(projetWS.getDateFinPrevisionnel());
        projet.setDateDebutReel(projetWS.getDateDebutReel());
        projet.setDateFinReel(projetWS.getDateFinReel());
        projet.setFinancementPrivePrevisionnel(projetWS.getFinancementPrivePrevisionnel());
        projet.setFinancementPublicPrevisionnel(projetWS.getFinancementPublicPrevisionnel());
        projet.setFinancementPriveReel(projetWS.getFinancementPriveReel());
        projet.setFinancementPublicReel(projetWS.getFinancementPublicReel());

        return projet;
    }

    public List<ProjetWS> buildListProjetWS(List<Projet> projets) {
        List<ProjetWS> projetWSs = new ArrayList<>();
        for (Projet projet : projets) {
            projetWSs.add(this.buildProjetWS(projet));
        }
        return projetWSs;
    }

    public PhaseWS buildPhaseWS(Phase phase) {
        PhaseWS phaseWS = new PhaseWS();

        phaseWS.setId(phase.getId());
        phaseWS.setNom(phase.getNom());
        phaseWS.setDateDebutPrevisionnel(phase.getDateDebutPrevisionnel());
        phaseWS.setDateDebutReel(phase.getDateDebutReel());
        phaseWS.setDateFinPrevisionnel(phase.getDateFinPrevisionnel());
        phaseWS.setDateFinReel(phase.getDateFinReel());
        phaseWS.setActif(phase.isActif());

        return phaseWS;
    }

    public Phase buildPhase(PhaseWS phaseWS) {
        Phase phase = new Phase();

        phase.setId(phaseWS.getId());
        phase.setNom(phaseWS.getNom());
        phase.setDateDebutPrevisionnel(phaseWS.getDateDebutPrevisionnel());
        phase.setDateDebutReel(phaseWS.getDateDebutReel());
        phase.setDateFinPrevisionnel(phaseWS.getDateFinPrevisionnel());
        phase.setDateFinReel(phaseWS.getDateFinReel());
        phase.setActif(phaseWS.isActif());

        return phase;
    }

    public List<PhaseWS> buildListPhaseWS(List<Phase> phases) {
        List<PhaseWS> phaseWSs = new ArrayList<>();
        for (Phase phase : phases) {
            phaseWSs.add(this.buildPhaseWS(phase));
        }
        return phaseWSs;
    }

    public List<Phase> buildListPhase(List<PhaseWS> phaseWSs) {
        List<Phase> phases = new ArrayList<>();
        for (PhaseWS phaseWS : phaseWSs) {
            phases.add(this.buildPhase(phaseWS));
        }
        return phases;
    }

    public RegionWS buildRegionWS(Region region) {
        RegionWS regionWS = new RegionWS();

        regionWS.setId(region.getId());
        regionWS.setLatitude(region.getLatitude());
        regionWS.setLongitude(region.getLongitude());
        regionWS.setNom(region.getNom());
        regionWS.setType(region.getType());

        return regionWS;
    }

    public Region buildRegion(RegionWS regionWS) {
        Region region = new Region();

        region.setId(regionWS.getId());
        region.setLatitude(regionWS.getLatitude());
        region.setLongitude(regionWS.getLongitude());
        region.setNom(regionWS.getNom());
        region.setType(regionWS.getType());

        return region;
    }

    public List<RegionWS> buildListRegionWS(List<Region> regions) {
        List<RegionWS> regionWSs = new ArrayList<>();
        for (Region region : regions) {
            regionWSs.add(this.buildRegionWS(region));
        }
        return regionWSs;
    }

    public ImpactWS buildImpactWS(Impact impact) {
        ImpactWS impactWS = new ImpactWS();

        impactWS.setId(impact.getId());
        impactWS.setAutres(impact.getAutres());
        impactWS.setTypeImpact(impact.getTypeImpact());
        impactWS.setUnite(impact.getUnite());
        impactWS.setValue(impact.getValue());

        return impactWS;
    }

    public List<ImpactWS> buildListImpactWS(List<Impact> impacts) {
        List<ImpactWS> impactWSs = new ArrayList<>();
        for (Impact impact : impacts) {
            impactWSs.add(this.buildImpactWS(impact));
        }
        return impactWSs;
    }

    public ObjectifWS buildObjectifWS(Objectif objectif) {
        ObjectifWS objectifWS = new ObjectifWS();

        objectifWS.setId(objectif.getId());
        objectifWS.setDescription(objectif.getDescription());
        objectifWS.setNom(objectif.getNom());
        objectifWS.setTypeObjectif(objectif.getTypeObjectif());

        return objectifWS;
    }

    public List<ObjectifWS> buildListObjectifWS(List<Objectif> objectifs) {
        List<ObjectifWS> objectifWSs = new ArrayList<>();
        for (Objectif objectif : objectifs) {
            objectifWSs.add(this.buildObjectifWS(objectif));
        }
        return objectifWSs;
    }
}
