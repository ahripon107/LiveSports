package com.sfuronlabs.ripon.cricketmania.model;

/**
 * Created by Ripon on 3/25/16.
 */
public class Batsman {

    private String name;
    private String out;
    private String runs, balls, fours, sixes;

    public Batsman(String name, String out, String runs, String balls, String fours, String sixes) {
        this.name = name;
        this.out = out;
        this.runs = runs;
        this.balls = balls;
        this.fours = fours;
        this.sixes = sixes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }

    public String getRuns() {
        return runs;
    }

    public void setRuns(String runs) {
        this.runs = runs;
    }

    public String getBalls() {
        return balls;
    }

    public void setBalls(String balls) {
        this.balls = balls;
    }

    public String getFours() {
        return fours;
    }

    public void setFours(String fours) {
        this.fours = fours;
    }

    public String getSixes() {
        return sixes;
    }

    public void setSixes(String sixes) {
        this.sixes = sixes;
    }

    @Override
    public String toString() {
        return "Batsman{" +
                "name='" + name + '\'' +
                ", out='" + out + '\'' +
                ", runs='" + runs + '\'' +
                ", balls='" + balls + '\'' +
                ", fours='" + fours + '\'' +
                ", sixes='" + sixes + '\'' +
                '}';
    }
}
