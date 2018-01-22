/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.helpers;

import com.bootcamp.commons.enums.TypeObjectif;

/**
 *
 * @author Bello
 */
public class ObjectifWS {
    private int id;
    private String nom;
    private String description;
    private TypeObjectif typeObjectif;

        /**
     * Get the goal id
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Set the goal id
     *
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the goal name
     *
     * @return the name
     */
    public String getNom() {
        return nom;
    }

    /**
     * Set the goal name
     *
     * @param nom the name to set
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Get the goal description
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the goal description
     *
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the goal type
     *
     * @return the goal type
     */
    public TypeObjectif getTypeObjectif() {
        return typeObjectif;
    }

    /**
     * Get the goal type
     *
     * @param typeObjectif the type to set
     */
    public void setTypeObjectif(TypeObjectif typeObjectif) {
        this.typeObjectif = typeObjectif;
    }
}
