package com.apptuned.fantacybetting;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
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
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

public class JackpotBettingActivity extends AppCompatActivity {

    private static final String ACCOUNT_BALANCE = "Account Balance";
    private static final String SINGLE_BET_COST = "Single bet cost";
    private static final String JACKPOT_BET_COST = "Jackpot cost";

    private SharedPreferences spConfig;

    private MenuItem miAccountBalance, miGetUnits, miShare;

    private CircularImageView leagueLogo;
    private League selectedLeague;
    private Button btnPlaceJackpotBet;
    private BetPairGroup betPairGroup;

    private AdView mAdView;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jackpot_betting);
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

        RecyclerView rvJackpot = (RecyclerView)findViewById(R.id.rv_jackpot);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvJackpot.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvJackpot.getContext(),linearLayoutManager.getOrientation());
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider_recyclerview));
        rvJackpot.addItemDecoration(dividerItemDecoration);



        betPairGroup = new BetPairGroup();
        betPairGroup.generate(this, true, 10, 5);

        JackpotListAdapter jackpotListAdapter = new JackpotListAdapter(JackpotBettingActivity.this, betPairGroup.getBetPairs());
        rvJackpot.setAdapter(jackpotListAdapter);

        btnPlaceJackpotBet = (Button)findViewById(R.id.btn_placeJackpotBet);
        btnPlaceJackpotBet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity hostActivity = (Activity) view.getContext();
                boolean completeSelection = true;

                MessageDialogActivity messageDialogActivity = new MessageDialogActivity(hostActivity);

                ArrayList<BetPair> betPairArrayList =  betPairGroup.getBetPairs();
                for(int i = 0; i < betPairArrayList.size(); i++){
                    if(betPairArrayList.get(i).getUserSelectedClub() == null){
                        completeSelection = false;
                    }
                }

                if(completeSelection){
                    int netEffect = -20;
                    boolean win = true;

                    for (BetPair betPair: betPairArrayList) {
                        if(betPair.getUserSelectedClub() != betPair.getCorrectResultClub()){
                            win = false;
                            break;
                        }
                    }
                    // Check if win and give message
                    if(win){
                        netEffect += 5000;
                        messageDialogActivity.setTitle("Congratulations!!!");
                        messageDialogActivity.setMessage("You have won the jackpot. Your account has been credited with 5,000!");
                        messageDialogActivity.setStatus(1);
                    }
                    else {
                        messageDialogActivity.setTitle("Jackpot Bet Results");
                        messageDialogActivity.setMessage("You had some wrong predictions. Play again to win!!");
                        messageDialogActivity.setStatus(0);
                    }
                    //TODO Update account values
                    int accountBalance = spConfig.getInt(ACCOUNT_BALANCE, 0);
                    spConfig.edit().putInt(ACCOUNT_BALANCE, accountBalance + netEffect).commit();
                    //TODO Update menuItem on accounts
                    miAccountBalance.setTitle("Balance: " + accountBalance + netEffect + " Units");

                    // Add Handler and show
                    messageDialogActivity.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            Intent intent = new Intent(getApplicationContext(), LeagueSelectionActivity.class);
                            startActivity(intent);
                        }
                    });
                    messageDialogActivity.show();
                }
                else {
                    // // TODO: Create custom alert
                    messageDialogActivity.setTitle("Incomplete Bet");
                    messageDialogActivity.setMessage("Please select options for all games in the list!!");
                    messageDialogActivity.setStatus(-1);
                    messageDialogActivity.show();
                }
            }
        });

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

}
