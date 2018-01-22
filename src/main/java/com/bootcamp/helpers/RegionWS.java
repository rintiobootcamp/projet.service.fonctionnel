/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.helpers;

import com.bootcamp.commons.enums.RegionType;

/**
 *
 * @author Bello
 */
public class RegionWS {

    private int id;
    private String nom;
    private RegionType type;
    private double longitude;
    private double latitude;

    /**
     * Get the location id
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Set the location id
     *
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the location name
     *
     * @return the name
     */
    public String getNom() {
        return nom;
    }

    /**
     * Set the location name
     *
     * @param nom the name to set
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Get the location type
     *
     * @return the type
     */
    public RegionType getType() {
        return type;
    }

    /**
     * Set the location type
     *
     * @param type the type to set
     */
    public void setType(RegionType type) {
        this.type = type;
    }

    /**
     * Get the location longitude
     *
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Set the location longitude
     *
     * @param longitude the longitude to set
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * Get the location latitude
     *
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Set the location latitude
     *
     * @param latitude the latitude to set
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
