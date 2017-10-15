package com.apptuned.fantacybetting;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by davies on 9/10/17.
 */

public class Club implements Serializable{
    private int id;
    private String iconURL, name, nameShort, stadium, leagueName;
    private int leagueId;
    private int[] otherLeaguesIds;
    private int homeGroundLeagueRating, awayGroungLeagueRating;
    private int homeGroundNonLeagueRating, awayGroungNonLeagueRating;
    private int positionInLeague;
    private boolean isPlayingChampionsLeague, isPlayingEuropaLeague;

    public Club(int id){
        this.id = id;
    }

    public Club(int id, String iconURL, String name, String nameShort, String stadium,
                String leagueName, int leagueId, int[] otherLeaguesIds,
                int homeGroundLeagueRating, int awayGroungLeagueRating,
                int homeGroundNonLeagueRating, int awayGroungNonLeagueRating,
                int positionInLeague,
                boolean isPlayingChampionsLeague, boolean isPlayingEuropaLeague){
        this.id = id;
        this.iconURL = iconURL;
        this.name = name;
        this.nameShort = nameShort;
        this.stadium = stadium;
        this.leagueName = leagueName;
        this.leagueId = leagueId;
        this.otherLeaguesIds = otherLeaguesIds;
        this.homeGroundLeagueRating = homeGroundLeagueRating;
        this.awayGroungLeagueRating = awayGroungLeagueRating;
        this.homeGroundNonLeagueRating = homeGroundNonLeagueRating;
        this.awayGroungNonLeagueRating = awayGroungNonLeagueRating;
        this.positionInLeague = positionInLeague;
        this.isPlayingChampionsLeague = isPlayingChampionsLeague;
        this.isPlayingEuropaLeague = isPlayingEuropaLeague;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getIconURL(){
        return this.iconURL;
    }

    public void setIconURL(String iconURL){
        this.iconURL = iconURL;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setNameShort(String nameShort){
        this.nameShort = nameShort;
    }

    public String getNameShort(){
        return this.nameShort;
    }

    public String getStadium(){
        return this.stadium;
    }

    public void setStadium(String stadium){
        this.stadium = stadium;
    }

    public void setLeagueId(int leagueId){
        this.leagueId = leagueId;
    }

    public int getLeagueId(){
        return this.leagueId;
    }

    public String getLeagueName(){
        return this.leagueName;
    }

    public void setLeagueName(String leagueName){
        this.leagueName = leagueName;
    }

    public int[] getOtherLeaguesIds(){
        return this.otherLeaguesIds;
    }

    public void setOtherLeaguesIds(int[] otherLeaguesIds){
        this.otherLeaguesIds = otherLeaguesIds;
    }

    public int getHomeGroundLeagueRating(){
        return this.homeGroundLeagueRating;
    }

    public void setHomeGroundLeagueRating(int homeGroundLeagueRating){
        this.homeGroundLeagueRating = homeGroundLeagueRating;
    }

    public int getAwayGroungLeagueRating(){
        return this.awayGroungLeagueRating;
    }

    public void setAwayGroungLeagueRating(int awayGroungLeagueRating){
        this.awayGroungLeagueRating = awayGroungLeagueRating;
    }

    public int getHomeGroundNonLeagueRating(){
        return this.homeGroundNonLeagueRating;
    }

    public void setHomeGroundNonLeagueRating(int homeGroundNonLeagueRating){
        this.homeGroundNonLeagueRating = homeGroundNonLeagueRating;
    }

    public int getAwayGroungNonLeagueRating(){
        return this.awayGroungNonLeagueRating;
    }

    public void setAwayGroungNonLeagueRating(int awayGroungNonLeagueRating){
        this.awayGroungNonLeagueRating = awayGroungNonLeagueRating;
    }

    public int getPositionInLeague(){
        return this.positionInLeague;
    }

    public void setPositionInLeague(int positionInLeague){
        this.positionInLeague = positionInLeague;
    }

    public boolean isPlayingChampionsLeague(){
        return this.isPlayingChampionsLeague;
    }

    public void setPlayingChampionsLeague(boolean isPlayingChampionsLeague){
        this.isPlayingChampionsLeague = isPlayingChampionsLeague;
    }

    public boolean isPlayingEuropaLeague(){
        return this.isPlayingEuropaLeague;
    }

    public void  setPlayingEuropaLeague(boolean isPlayingEuropaLeague){
        this.isPlayingEuropaLeague = isPlayingEuropaLeague;
    }

}
