package com.bootcamp.helpers;

import com.bootcamp.entities.Phase;

import java.util.List;

public class ProjetStatHelper {
    private double tauxBuget;
    private double consumedBuget;
    private double tauxFinancementPrive;
    private double tauxFinancementPublic;
    private List<PhaseStatHelper> phaseStatHelperList;
    public double getTauxBuget() {
        return tauxBuget;
    }

    public void setTauxBuget(double tauxBuget) {
        this.tauxBuget = tauxBuget;
    }

    public double getTauxFinancementPrive() {
        return tauxFinancementPrive;
    }

    public void setTauxFinancementPrive(double tauxFinancementPrive) {
        this.tauxFinancementPrive = tauxFinancementPrive;
    }

    public double getTauxFinancementPublic() {
        return tauxFinancementPublic;
    }

    public void setTauxFinancementPublic(double tauxFinancementPublic) {
        this.tauxFinancementPublic = tauxFinancementPublic;
    }

    public List<PhaseStatHelper> getPhaseStatHelperList() {
        return phaseStatHelperList;
    }

    public void setPhaseStatHelperList(List<PhaseStatHelper> phaseStatHelperList) {
        this.phaseStatHelperList = phaseStatHelperList;
    }

    public double getConsumBuget() {
        return consumedBuget;
    }

    public void setConsumBuget(double consumBuget) {
        this.consumedBuget = consumBuget;
    }
}
