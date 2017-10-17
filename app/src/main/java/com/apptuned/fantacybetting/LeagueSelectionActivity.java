package com.apptuned.fantacybetting;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.InterstitialAd;

public class LeagueSelectionActivity extends AppCompatActivity {

    private static final String ACCOUNT_BALANCE = "Account Balance";
    private static final String SINGLE_BET_COST = "Single bet cost";
    private static final String JACKPOT_BET_COST = "Jackpot cost";

    private MenuItem miAccountBalance, miGetUnits, miShare;

    private SharedPreferences spConfig;

    private ArrayList<League> leaguesArray;

    private AdView mAdView;

    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_league_selection);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAdView = (AdView) findViewById(R.id.adView);
        final AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712"); // ca-app-pub-3940256099942544/1033173712 Test
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed(){
                // This is the only add we had. Close the app for now
                getUnits();
                mInterstitialAd.loadAd(adRequest); // TODO This is very important and needs to be replicated over all responses of ad listeners
            }
        });

        spConfig = getSharedPreferences("com.apptuned.fantacybetting.Config", this.MODE_PRIVATE);

        RecyclerView rvLeaguesList = (RecyclerView) findViewById(R.id.rv_leagues_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvLeaguesList.setLayoutManager(linearLayoutManager);

        try{
            String jsonFileContent = loadJSONFromAsset("leagues.json");
            JSONArray jsonLeaguesArray = new JSONArray(jsonFileContent);
            leaguesArray = new ArrayList<League>();
            for(int i = 0; i < jsonLeaguesArray.length(); i++){
                JSONObject jsonLeagueObject = jsonLeaguesArray.getJSONObject(i);
                int leagueId = jsonLeagueObject.getInt("id");
                String iconURL = jsonLeagueObject.getString("iconURL");
                String leagueName = jsonLeagueObject.getString("name");
                String leagueNameShort = jsonLeagueObject.getString("nameShort");
                String leagueCountry = jsonLeagueObject.getString("country");
                League league = new League(leagueId, iconURL, leagueName, leagueNameShort, leagueCountry);
                leaguesArray.add(league);
            }

        }catch (JSONException e){
            e.printStackTrace();
        }
        LeaguesListAdapter leaguesListAdapter = new LeaguesListAdapter(LeagueSelectionActivity.this, leaguesArray);
        rvLeaguesList.setAdapter(leaguesListAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_account, menu);
        this.miAccountBalance = menu.findItem(R.id.miAccountBalance);
        int accountBalance = spConfig.getInt(ACCOUNT_BALANCE, 0);
        this.miAccountBalance.setTitle("Balance: " + accountBalance + " Units");

        this.miGetUnits = menu.findItem(R.id.miShare);
        this.miShare = menu.findItem(R.id.miShare);

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.miShare:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBodyText = "Check out the free Fantasy Betting App at https://play.google.com/store/apps/details?id=com.apptuned.fantasybetting";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Fantasy Betting app");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
                startActivity(Intent.createChooser(sharingIntent, "Shearing Option"));
                return true;
            case R.id.miAccountBalance:

                return true;
            case R.id.miGetUnits:
                // First show interstitial
                if(mInterstitialAd.isLoaded()){
                    mInterstitialAd.show();
                }else {
                    // Just go ahead and get units
                    getUnits();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        //Refresh your stuff here
        final Handler handler = new Handler();
        final int delay = 1000;

        handler.postDelayed(new Runnable(){
            int trials = 0;
            public void run(){
                //do something
                if(miAccountBalance == null){
                    if(trials < 20)
                        handler.postDelayed(this, delay);
                }
                else {
                    int accountBalance = spConfig.getInt(ACCOUNT_BALANCE, 0);
                    miAccountBalance.setTitle("Balance: " + accountBalance + " Units");
                }
                trials++;
            }
        }, delay);
    }

    private void getUnits(){
        final int accountBalance = spConfig.getInt(ACCOUNT_BALANCE, 0);
        final int singleBetCost = spConfig.getInt(SINGLE_BET_COST, 0);
        final int jackpotBetCost = spConfig.getInt(JACKPOT_BET_COST, 0);

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Betting Units Request");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        if(accountBalance < singleBetCost || accountBalance < jackpotBetCost){
            // Not enough units. Tailor message for success and credit account
            int newBalance = accountBalance + 100;
            spConfig.edit().putInt(ACCOUNT_BALANCE, newBalance).commit();
            // Update new balance in menu item
            this.miAccountBalance.setTitle("Balance: " + newBalance + " Units");
            alertDialog.setMessage("Congratulations!!! Your account has been credited with " + 100 + " Fantasy Betting Units....");
            alertDialog.setIcon(R.drawable.core_success_dark);
        }else{
            alertDialog.setMessage("Your account has sufficient Fantasy Betting Units.");
            alertDialog.setIcon(R.drawable.core_warning_dark);
        }

        alertDialog.show();
    }


    public String loadJSONFromAsset(String fileName){
        String json = null;
        try{
            InputStream is = getAssets().open(fileName);
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
