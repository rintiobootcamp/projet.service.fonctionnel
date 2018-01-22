/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.helpers;

import com.bootcamp.commons.enums.TypeImpact;

/**
 *
 * @author Bello
 */
public class ImpactWS {

    private int id;
    private TypeImpact typeImpact;
    private String unite;
    private double value;
    private String autres;

    /**
     * Get the impact id
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Set the impact id
     *
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the impact type
     *
     * @return the impact type
     */
    public TypeImpact getTypeImpact() {
        return typeImpact;
    }

    /**
     * Set the impact type
     *
     * @param typeImpact impact type to set
     */
    public void setTypeImpact(TypeImpact typeImpact) {
        this.typeImpact = typeImpact;
    }

    /**
     * Get the impact unit
     *
     * @return the unit
     */
    public String getUnite() {
        return unite;
    }

    /**
     * Set the impact unit
     *
     * @param unite the unit to set
     */
    public void setUnite(String unite) {
        this.unite = unite;
    }

    /**
     * Get the impact value
     *
     * @return the value
     */
    public double getValue() {
        return value;
    }

    /**
     * Set the impact value
     *
     * @param value the value to set
     */
    public void setValue(double value) {
        this.value = value;
    }

    /**
     * Get the other impact
     *
     * @return the other impact
     */
    public String getAutres() {
        return autres;
    }

    /**
     * Set the other impact
     *
     * @param autres the other impact to set
     */
    public void setAutres(String autres) {
        this.autres = autres;
    }
}
