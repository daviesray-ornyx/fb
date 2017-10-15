package com.apptuned.fantacybetting;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

public class JackpotBettingActivity extends AppCompatActivity {

    private CircularImageView leagueLogo;
    private League selectedLeague;
    private Button btnPlaceJackpotBet;
    private BetPairGroup betPairGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jackpot_betting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        selectedLeague = (League) getIntent().getSerializableExtra("leagueObj");

        RecyclerView rvJackpot = (RecyclerView)findViewById(R.id.rv_jackpot);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvJackpot.setLayoutManager(linearLayoutManager);

        betPairGroup = new BetPairGroup();
        betPairGroup.generate(this, true, 10, 5);

        JackpotListAdapter jackpotListAdapter = new JackpotListAdapter(JackpotBettingActivity.this, betPairGroup.getBetPairs());
        rvJackpot.setAdapter(jackpotListAdapter);

        btnPlaceJackpotBet = (Button)findViewById(R.id.btn_placeJackpotBet);
        btnPlaceJackpotBet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean completeSelection = true;
                ArrayList<BetPair> betPairArrayList =  betPairGroup.getBetPairs();
                for(int i = 0; i < betPairArrayList.size(); i++){
                    if(betPairArrayList.get(i).getUserSelectedClub() == null){
                        completeSelection = false;
                    }
                }

                if(completeSelection){
                    //TODO Process bet and give results
                    boolean win = true;

                    for (BetPair betPair: betPairArrayList) {
                        if(betPair.getUserSelectedClub() != betPair.getCorrectResultClub()){
                            win = false;
                            break;
                        }
                    }
                    // Check if win and give message
                    if(win){
                        Toast.makeText(view.getContext(), "You have won the jackpot. Your account has been credited with 231,000,000!", Toast.LENGTH_SHORT).show();
                        // TODO If successful, show celebrations,

                        // TODO update account balances,

                        // TODO navigate to accounts page to show the new balance

                    }
                    else {
                        Toast.makeText(view.getContext(), "You had some wrong predictions. Try again later!!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(view.getContext(), LeagueSelectionActivity.class);
                        startActivity(intent);
                    }
                }
                else {
                    // // TODO: Create custom alert
                    Toast.makeText(getApplicationContext(), "Please select options for all games in the list!!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}
