package com.apptuned.fantacybetting;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;

public class NormalBettingActivity extends AppCompatActivity {

    private static final String ACCOUNT_BALANCE = "Account Balance";
    private static final String SINGLE_BET_COST = "Single bet cost";
    private static final String JACKPOT_BET_COST = "Jackpot cost";

    private SharedPreferences spConfig;

    private MenuItem miAccountBalance, miGetUnits, miShare;

    private League selectedLeague;
    private BetPairGroup betPairGroup;
    private RecyclerView rvNormalBetting;

    private AdView mAdView;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_betting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        selectedLeague = (League) getIntent().getSerializableExtra("leagueObj");

        // Set Actionbar title
        getSupportActionBar().setTitle(selectedLeague.getName());

        rvNormalBetting = (RecyclerView)findViewById(R.id.rv_normalBetting);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvNormalBetting.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvNormalBetting.getContext(),linearLayoutManager.getOrientation());
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider_recyclerview));
        rvNormalBetting.addItemDecoration(dividerItemDecoration);

        betPairGroup = new BetPairGroup();
        betPairGroup.generate(this, false, 10, selectedLeague.getId());

        NormalBettingtListAdapter normalBettingtListAdapter = new NormalBettingtListAdapter(NormalBettingActivity.this, betPairGroup.getBetPairs());
        rvNormalBetting.setAdapter(normalBettingtListAdapter);
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

    private void getUnits(){
        final int accountBalance = spConfig.getInt(ACCOUNT_BALANCE, 0);
        final int singleBetCost = spConfig.getInt(SINGLE_BET_COST, 0);
        final int jackpotBetCost = spConfig.getInt(JACKPOT_BET_COST, 0);

        if(accountBalance < singleBetCost || accountBalance < jackpotBetCost){
            // Not enough units. Tailor message for success and credit account
            int newBalance = accountBalance + 100;
            spConfig.edit().putInt(ACCOUNT_BALANCE, newBalance).commit();
            // Update new balance in menu item
            this.miAccountBalance.setTitle("Balance: " + newBalance + " Units");

            MessageDialogActivity messageDialogActivity = new MessageDialogActivity(this, "Fantasy Betting Units Request",
                    "Congratulations!!! Your account has been credited with " + 100 + " Fantasy Betting Units....", 1);
            messageDialogActivity.show();
        }else {
            MessageDialogActivity messageDialogActivity = new MessageDialogActivity(this, "Fantasy Betting Units Request", "Your account has sufficient Fantasy Betting Units", 0);
            messageDialogActivity.show();
        }
    }

    public MenuItem getMiAccountBalance(){
        return this.miAccountBalance;
    }

}
