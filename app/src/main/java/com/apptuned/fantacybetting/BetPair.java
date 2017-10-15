package com.apptuned.fantacybetting;

import java.io.Serializable;

/**
 * Created by davies on 9/10/17.
 */

public class BetPair implements Serializable{
    /*
    *
    * This class holds a bet pair, bet result and whether user was right or not
    *
    * */

    private Club homeClub, awayClub, correctResultClub, userSelectedClub;

    public Club getHomeClub(){
        return this.homeClub;
    }

    public void setHomeClub(Club homeClub){
        this.homeClub = homeClub;
    }

    public Club getAwayClub(){
        return this.awayClub;
    }

    public void setAwayClub(Club awayClub){
        this.awayClub = awayClub;
    }

    public Club getCorrectResultClub(){
        return this.correctResultClub;
    }

    public void setCorrectResultClub(Club correctResultClub){
        this.correctResultClub = correctResultClub;
    }

    public void setUserSelectedClub(Club userSelectedClub){
        this.userSelectedClub = userSelectedClub;
    }

    public Club getUserSelectedClub(){
        return this.userSelectedClub;
    }

}