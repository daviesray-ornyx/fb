package com.apptuned.fantacybetting;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by davies on 9/10/17.
 */

public class JSONClubFileReader {

    public Context context;
    private String jsonString;

    public JSONClubFileReader(Context context){
        this.context = context;
    }

    public ArrayList<Club> getAllClubs(){
        /* Returns a list of all clubs in the database */
        ArrayList<Club> clubsArray = new ArrayList<Club>();
        try{
            jsonString = loadJSONFromAsset("clubs.json");
            JSONArray jsonClubsArray = new JSONArray(jsonString);
            for(int i = 0; i < jsonClubsArray.length(); i++){
                JSONObject jsonClubObject = jsonClubsArray.getJSONObject(i);
                JSONArray jsonOtherLeagueIdsArray = jsonClubObject.getJSONArray("otherLeaguesIds");
                int[] otherLeagueIdsArray = new int[jsonOtherLeagueIdsArray.length()];
                for(int j = 0; j < jsonOtherLeagueIdsArray.length(); j++){
                    otherLeagueIdsArray[j] = jsonOtherLeagueIdsArray.getInt(j);
                }

                Club club = new Club(
                        jsonClubObject.getInt("id"),
                        jsonClubObject.getString("iconURL"),
                        jsonClubObject.getString("name"),
                        jsonClubObject.getString("nameShort"),
                        jsonClubObject.getString("stadium"),
                        jsonClubObject.getString("leagueName"),
                        jsonClubObject.getInt("leagueId"),
                        otherLeagueIdsArray,
                        jsonClubObject.getInt("homeGroundLeagueRating"),
                        jsonClubObject.getInt("awayGroungLeagueRating"),
                        jsonClubObject.getInt("homeGroundNonLeagueRating"),
                        jsonClubObject.getInt("awayGroungNonLeagueRating"),
                        jsonClubObject.getInt("positionInLeague"),
                        jsonClubObject.getBoolean("isPlayingChampionsLeague"),
                        jsonClubObject.getBoolean("isPlayingEuropaLeague")

                );
                clubsArray.add(club);
            }
        } catch (JSONException e){
            e.printStackTrace(); // Error occurred parsing JSON array
        }

        return clubsArray;
    }

    public ArrayList<Club> getClubsList(int leagueId){
        ArrayList<Club> clubsArray = new ArrayList<Club>();
        try{
            jsonString = loadJSONFromAsset("clubs.json");
            JSONArray jsonClubsArray = new JSONArray(jsonString);
            for(int i = 0; i < jsonClubsArray.length(); i++){
                JSONObject jsonClubObject = jsonClubsArray.getJSONObject(i);
                int jsonClubObjectLeagueId = jsonClubObject.getInt("leagueId");

                boolean inOtherLeagueIds = false;

                JSONArray jsonOtherLeagueIdsArray = jsonClubObject.getJSONArray("otherLeaguesIds");
                int[] otherLeagueIdsArray = new int[jsonOtherLeagueIdsArray.length()];
                for(int j = 0; j < jsonOtherLeagueIdsArray.length(); j++){
                    int otherLeagueId = jsonOtherLeagueIdsArray.getInt(j);
                    otherLeagueIdsArray[j] = jsonOtherLeagueIdsArray.getInt(j);
                    if(otherLeagueId == leagueId)
                        inOtherLeagueIds = true;
                }
                if(leagueId != jsonClubObjectLeagueId && !inOtherLeagueIds)
                    continue;
                else {
                    // Add club to list

                    Club club = new Club(
                            jsonClubObject.getInt("id"),
                            jsonClubObject.getString("iconURL"),
                            jsonClubObject.getString("name"),
                            jsonClubObject.getString("nameShort"),
                            jsonClubObject.getString("stadium"),
                            jsonClubObject.getString("leagueName"),
                            jsonClubObject.getInt("leagueId"),
                            otherLeagueIdsArray,
                            jsonClubObject.getInt("homeGroundLeagueRating"),
                            jsonClubObject.getInt("awayGroungLeagueRating"),
                            jsonClubObject.getInt("homeGroundNonLeagueRating"),
                            jsonClubObject.getInt("awayGroungNonLeagueRating"),
                            jsonClubObject.getInt("positionInLeague"),
                            jsonClubObject.getBoolean("isPlayingChampionsLeague"),
                            jsonClubObject.getBoolean("isPlayingEuropaLeague")

                    );
                    clubsArray.add(club);
                }

            }
        } catch (JSONException e){
            e.printStackTrace(); // Error occurred parsing JSON array
        }
        return clubsArray;
    }

    public String loadJSONFromAsset(String fileName){
        String json = null;
        try{
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

        }catch (IOException ex){
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
