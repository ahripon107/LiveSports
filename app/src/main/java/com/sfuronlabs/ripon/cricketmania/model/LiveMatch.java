package com.sfuronlabs.ripon.cricketmania.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by amin on 8/24/16.
 */
public class LiveMatch implements Serializable {

    private String matchDescription;
    private String matchId;
    private String team1;
    private String team2;
    private String title;

    public LiveMatch(String matchDescription, String matchId, String team1, String team2, String title) {
        this.matchDescription = matchDescription;
        this.matchId = matchId;
        this.team1 = team1;
        this.team2 = team2;
        this.title = title;
    }

    public String getMatchDescription() {
        return matchDescription;
    }

    public void setMatchDescription(String matchDescription) {
        this.matchDescription = matchDescription;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "LiveMatch{" +
                "matchDescription='" + matchDescription + '\'' +
                ", matchId='" + matchId + '\'' +
                ", team1='" + team1 + '\'' +
                ", team2='" + team2 + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}