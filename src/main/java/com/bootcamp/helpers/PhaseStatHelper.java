package com.bootcamp.helpers;

public class PhaseStatHelper {
    private int idPhase;
    private String nomPhase;
    private long tempRetardDateDebut;
    private long tempRetardDateDebutFin;

    private long tempAvanceDateDebut;
    private long tempAvanceDateFin;

    public int getIdPhase() {
        return idPhase;
    }

    public void setIdPhase(int idPhase) {
        this.idPhase = idPhase;
    }

    public String getNomPhase() {
        return nomPhase;
    }

    public void setNomPhase(String nomPhase) {
        this.nomPhase = nomPhase;
    }

    public long getTempRetardDateDebut() {
        return tempRetardDateDebut;
    }

    public void setTempRetardDateDebut(long tempRetardDateDebut) {
        this.tempRetardDateDebut = tempRetardDateDebut;
    }

    public long getTempRetardDateDebutFin() {
        return tempRetardDateDebutFin;
    }

    public void setTempRetardDateDebutFin(long tempRetardDateDebutFin) {
        this.tempRetardDateDebutFin = tempRetardDateDebutFin;
    }

    public long getTempAvanceDateDebut() {
        return tempAvanceDateDebut;
    }

    public void setTempAvanceDateDebut(long tempAvanceDateDebut) {
        this.tempAvanceDateDebut = tempAvanceDateDebut;
    }

    public long getTempAvanceDateFin() {
        return tempAvanceDateFin;
    }

    public void setTempAvanceDateFin(long tempAvanceDateDebutFin) {
        this.tempAvanceDateFin = tempAvanceDateDebutFin;
    }
}
