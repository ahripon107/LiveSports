package com.sfuronlabs.ripon.cricketmania.model;

/**
 * Created by Ripon on 3/25/16.
 */
public class Bowler {

    private String name, over, maiden, run, wicket, economy;

    public Bowler(String name, String over, String maiden, String run, String wicket, String economy) {
        this.name = name;
        this.over = over;
        this.maiden = maiden;
        this.run = run;
        this.wicket = wicket;
        this.economy = economy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOver() {
        return over;
    }

    public void setOver(String over) {
        this.over = over;
    }

    public String getMaiden() {
        return maiden;
    }

    public void setMaiden(String maiden) {
        this.maiden = maiden;
    }

    public String getRun() {
        return run;
    }

    public void setRun(String run) {
        this.run = run;
    }

    public String getWicket() {
        return wicket;
    }

    public void setWicket(String wicket) {
        this.wicket = wicket;
    }

    public String getEconomy() {
        return economy;
    }

    public void setEconomy(String economy) {
        this.economy = economy;
    }

    @Override
    public String toString() {
        return "Bowler{" +
                "name='" + name + '\'' +
                ", over='" + over + '\'' +
                ", maiden='" + maiden + '\'' +
                ", run='" + run + '\'' +
                ", wicket='" + wicket + '\'' +
                ", economy='" + economy + '\'' +
                '}';
    }
}
