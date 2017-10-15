package com.apptuned.fantacybetting;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class NormalBettingActivity extends AppCompatActivity {

    private League selectedLeague;
    private BetPairGroup betPairGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_betting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        selectedLeague = (League) getIntent().getSerializableExtra("leagueObj");

        // Set Actionbar title
        getSupportActionBar().setTitle(selectedLeague.getName());

        RecyclerView rvNormalBetting = (RecyclerView)findViewById(R.id.rv_normalBetting);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvNormalBetting.setLayoutManager(linearLayoutManager);

        betPairGroup = new BetPairGroup();
        betPairGroup.generate(this, false, 10, selectedLeague.getId());

        NormalBettingtListAdapter normalBettingtListAdapter = new NormalBettingtListAdapter(NormalBettingActivity.this, betPairGroup.getBetPairs());
        rvNormalBetting.setAdapter(normalBettingtListAdapter);
    }

}
