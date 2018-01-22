/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.helpers;

import com.bootcamp.commons.enums.EtatProjet;
import com.bootcamp.entities.Impact;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bello
 */
public class ProjetWS {

    private int id;
    private String nom;
    private String reference;
    private String description;
    private long dateDebutReel;
    private long dateFinReel;
    private long dateDebutPrevisionnel;
    private long dateFinPrevisionnel;
    private double budgetPrevisionnel;
    private double coutReel;
    private EtatProjet etat;
    private List<PhaseWS> phases;
    private int idSecteur;
    private List<RegionWS> regions;
    private List<ImpactWS> impactList;
    private List<ObjectifWS> objectifList;
    private double financementPrivePrevisionnel;
    private double financementPublicPrevisionnel;
    private double financementPriveReel;
    private double financementPublicReel;
    private int idProgramme;
    
    /**
     * Get the project id
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Set the project id
     *
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the project name
     *
     * @return the name
     */
    public String getNom() {
        return nom;
    }

    /**
     * Set the project name
     *
     * @param nom the name to set
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Get the project reference
     *
     * @return the reference
     */
    public String getReference() {
        return reference;
    }

    /**
     * Set the project reference
     *
     * @param reference the reference to set
     */
    public void setReference(String reference) {
        this.reference = reference;
    }

    /**
     * Get the project description
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the project description
     *
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the project real started date
     *
     * @return the real started date
     */
    public long getDateDebutReel() {
        return dateDebutReel;
    }

    /**
     * Set the project real started date
     *
     * @param dateDebutReel the date to set
     */
    public void setDateDebutReel(long dateDebutReel) {
        this.dateDebutReel = dateDebutReel;
    }

    /**
     * Get the project real ended date
     *
     * @return the real ended date
     */
    public long getDateFinReel() {
        return dateFinReel;
    }

    /**
     * Set the project real ended date
     *
     * @param dateFinReel the date to set
     */
    public void setDateFinReel(long dateFinReel) {
        this.dateFinReel = dateFinReel;
    }

    /**
     * Get the project estimate started date
     *
     * @return the estimate started date
     */
    public long getDateDebutPrevisionnel() {
        return dateDebutPrevisionnel;
    }

    /**
     * Set the project estimate started date
     *
     * @param dateDebutPrevisionnel the date to set
     */
    public void setDateDebutPrevisionnel(long dateDebutPrevisionnel) {
        this.dateDebutPrevisionnel = dateDebutPrevisionnel;
    }

    /**
     * Get the project estimate ended date
     *
     * @return the estimate ended date
     */
    public long getDateFinPrevisionnel() {
        return dateFinPrevisionnel;
    }

    /**
     * Set the project estimate ended date
     *
     * @param dateFinPrevisionnel the date to set
     */
    public void setDateFinPrevisionnel(long dateFinPrevisionnel) {
        this.dateFinPrevisionnel = dateFinPrevisionnel;
    }

    /**
     * Get the project estimate funds
     *
     * @return the estimate funds
     */
    public double getBudgetPrevisionnel() {
        return budgetPrevisionnel;
    }

    /**
     * Set the project estimate funds
     *
     * @param budgetPrevisionnel the funds to set
     */
    public void setBudgetPrevisionnel(double budgetPrevisionnel) {
        this.budgetPrevisionnel = budgetPrevisionnel;
    }

    /**
     * Get the project real cost
     *
     * @return the real cost
     */
    public double getCoutReel() {
        return coutReel;
    }

    /**
     * Set the project real cost
     *
     * @param coutReel the cost to set
     */
    public void setCoutReel(double coutReel) {
        this.coutReel = coutReel;
    }

    /**
     * Get the project step list
     *
     * @return the step list
     */
    public List<PhaseWS> getPhases() {    
        return phases;
    }

    /**
     * Set the project step list
     *
     * @param phases the step list to set
     */
    public void setPhases(List<PhaseWS> phases) {    
        this.phases = phases;
    }

    /**
     * Get the project sector id
     *
     * @return the sector id
     */
    public int getIdSecteur() {
        return idSecteur;
    }

    /**
     * Set the project sector id
     *
     * @param idSecteur the project sector id to set
     */
    public void setIdSecteur(int idSecteur) {
        this.idSecteur = idSecteur;
    }

    /**
     * Get the project location list
     *
     * @return the location list
     */
    public List<RegionWS> getRegions() {
        return regions;
    }

    /**
     * Set the project location list
     *
     * @param regions the location list to set
     */
    public void setRegions(List<RegionWS> regions) {
        this.regions = regions;
    }

    /**
     * Get the project impact list
     *
     * @return the impact list
     */
    public List<ImpactWS> getImpactList() {
        return impactList;
    }

    /**
     * Set the project impact list
     *
     * @param impactList the impact list to set
     */
    public void setImpactList(List<ImpactWS> impactList) {
        this.impactList = impactList;
    }

    /**
     * Get the project goal list
     *
     * @return the goal list
     */
    public List<ObjectifWS> getObjectifList() {
        return objectifList;
    }

    /**
     * Set the project goal list
     *
     * @param objectifList the goal list to set
     */
    public void setObjectifList(List<ObjectifWS> objectifList) {
        this.objectifList = objectifList;
    }

    /**
     *
     * @return
     */
    public double getFinancementPrivePrevisionnel() {
        return financementPrivePrevisionnel;
    }

    /**
     *
     * @param financementPrivePrevisionnel
     */
    public void setFinancementPrivePrevisionnel(double financementPrivePrevisionnel) {
        this.financementPrivePrevisionnel = financementPrivePrevisionnel;
    }

    /**
     *
     * @return
     */
    public double getFinancementPublicPrevisionnel() {
        return financementPublicPrevisionnel;
    }

    /**
     *
     * @param financementPublicPrevisionnel
     */
    public void setFinancementPublicPrevisionnel(double financementPublicPrevisionnel) {
        this.financementPublicPrevisionnel = financementPublicPrevisionnel;
    }

    /**
     *
     * @return
     */
    public double getFinancementPriveReel() {
        return financementPriveReel;
    }

    /**
     *
     * @param financementPriveReel
     */
    public void setFinancementPriveReel(double financementPriveReel) {
        this.financementPriveReel = financementPriveReel;
    }

    /**
     *
     * @return
     */
    public double getFinancementPublicReel() {
        return financementPublicReel;
    }

    /**
     *
     * @param financementPublicReel
     */
    public void setFinancementPublicReel(double financementPublicReel) {
        this.financementPublicReel = financementPublicReel;
    }

    /**
     * Get the project program id
     *
     * @return the program id
     */
    public int getIdProgramme() {
        return idProgramme;
    }

    /**
     * Set the project program id
     *
     * @param idProgramme the program id to set
     */
    public void setIdProgramme(int idProgramme) {
        this.idProgramme = idProgramme;
    }

    /**
     *
     * @return
     */
    public EtatProjet getEtat() {
        return etat;
    }

    /**
     *
     * @param etat
     */
    public void setEtat(EtatProjet etat) {
        this.etat = etat;
    }
}
