/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.helpers;

/**
 *
 * @author Bello
 */
public class PhaseWS {

    private int id;
    private String nom;
    private long dateDebutPrevisionnel;
    private long dateFinPrevisionnel;
    private long dateDebutReel;
    private long dateFinReel;
    private boolean actif;

    /**
     * Get the step id
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Set the step id
     *
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the step name
     *
     * @return the name
     */
    public String getNom() {
        return nom;
    }

    /**
     * Set the step name
     *
     * @param nom the name to set
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Get the step estimate started date
     *
     * @return the estimate started date
     */
    public long getDateDebutPrevisionnel() {
        return dateDebutPrevisionnel;
    }

    /**
     * Set the step estimate started date
     *
     * @param dateDebutPrevisionnel the date to set
     */
    public void setDateDebutPrevisionnel(long dateDebutPrevisionnel) {
        this.dateDebutPrevisionnel = dateDebutPrevisionnel;
    }

    /**
     * Get the step estimate ended date
     *
     * @return the estimate ended date
     */
    public long getDateFinPrevisionnel() {
        return dateFinPrevisionnel;
    }

    /**
     * Set the step estimate ended date
     *
     * @param dateFinPrevisionnel the date to set
     */
    public void setDateFinPrevisionnel(long dateFinPrevisionnel) {
        this.dateFinPrevisionnel = dateFinPrevisionnel;
    }

    /**
     * Get the step real started date
     *
     * @return the real started date
     */
    public long getDateDebutReel() {
        return dateDebutReel;
    }

    /**
     * Set the step real started date
     *
     * @param dateDebutReel the date to set
     */
    public void setDateDebutReel(long dateDebutReel) {
        this.dateDebutReel = dateDebutReel;
    }

    /**
     * Get the step real ended date
     *
     * @return the real ended date
     */
    public long getDateFinReel() {
        return dateFinReel;
    }

    /**
     * Set the step real ended date
     *
     * @param dateFinReel the date to set
     */
    public void setDateFinReel(long dateFinReel) {
        this.dateFinReel = dateFinReel;
    }

    /**
     * Check if the step is enable
     *
     * @return
     */
    public boolean isActif() {
        return actif;
    }

    /**
     * Enable or disable the step
     *
     * @param actif
     */
    public void setActif(boolean actif) {
        this.actif = actif;
    }
}
