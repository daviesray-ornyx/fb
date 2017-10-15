package com.apptuned.fantacybetting;

import android.content.Context;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by davies on 9/10/17.
 */

public class BetPairGroup{
    private ArrayList<BetPair> betPairs;
    private int correctBetPairNo, incorrectBetPairNo, totalBetPairs;
    private boolean isJackpot;
    private int amountWon;

    public ArrayList<BetPair> getBetPairs(){
        return this.betPairs;
    }

    public void setBetPairs(ArrayList<BetPair> betPairs){
        this.betPairs = betPairs;
    }

    public int getCorrectBetPairNo(){
        return this.correctBetPairNo;
    }

    public void setCorrectBetPairNo(int correctBetPairNo){
        this.correctBetPairNo = correctBetPairNo;
    }

    public int getIncorrectBetPairNo(){
        return this.incorrectBetPairNo;
    }

    public void setIncorrectBetPairNo(int incorrectBetPairNo){
        this.incorrectBetPairNo = incorrectBetPairNo;
    }

    public int getTotalBetPairs(){
        return this.totalBetPairs;
    }

    public void setTotalBetPairs(int totalBetPairs){
        this.totalBetPairs = totalBetPairs;
    }

    public boolean isJackpot(){
        return this.isJackpot;
    }

    public void setJackpot(boolean isJackpot){
        this.isJackpot = isJackpot;
    }

    public int getAmountWon(){
        return this.amountWon;
    }

    public void setAmountWon(int amountWon){
        this.amountWon = amountWon;
    }

    public boolean generate(Context context, boolean isJackpot, int itemsInGroup, int leagueId){
        /*
        *
        * This function is used to generate the bet group depending on the parameters passed
        *
        * Returns true if the process was successful!
        * */
        betPairs = new ArrayList<BetPair>(); // Instantiate thre BetPair ArrayList
        JSONClubFileReader jsonClubFileReader = new JSONClubFileReader(context);
        ArrayList<Club> clubArrayList = isJackpot? jsonClubFileReader.getAllClubs() : jsonClubFileReader.getClubsList(leagueId);

        ArrayList<Club> addedClubs = new ArrayList<Club>();
        // Use a while
        Random r = new Random();
        int i = 0;
        while(i < itemsInGroup && i < clubArrayList.size()/2){
            // We are still within our limit...
            // get home club
            BetPair newBetPair = new BetPair();
            boolean retry = true;
            while(retry){
                int randomIndex = r.nextInt(clubArrayList.size()-0) + 0; // thisi is the index to look for
                Club club = clubArrayList.get(randomIndex);
                if(addedClubs.contains(club)){
                    continue;
                }
                else{
                    // Item not yet added.
                    newBetPair.setHomeClub(club);
                    addedClubs.add(club);
                    retry = false;
                }
            }

            retry = true;
            while (retry){
                int randomIndex = r.nextInt(clubArrayList.size()-0) + 0; // thisi is the index to look for
                Club club = clubArrayList.get(randomIndex);
                if(addedClubs.contains(club)){
                    continue;
                }
                else{
                    // Item not yet added.
                    newBetPair.setAwayClub(club);
                    addedClubs.add(club);
                    retry = false;
                }
            }

            // New betPair generated... Generate the correct answer and
            newBetPair.setCorrectResultClub(newBetPair.getHomeClub());
            betPairs.add(newBetPair);
            i++;// Increment i
        }

//        Toast.makeText(context, "Bet Pairs created: " + betPairs.size(), Toast.LENGTH_LONG).show();

        return true;
    }

    public Club predictWinner(BetPair betPair){
        return betPair.getHomeClub(); //This is the default at the moment
    }
}
