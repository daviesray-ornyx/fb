package com.apptuned.fantacybetting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

public class AccountActivity extends AppCompatActivity {

    private static final String ACCOUNT_BALANCE_VAL = "Account Balance";
    private static final String BANK_LOAN_VAL = "Bank Loan";
    private static final String TOTAL_BETS_VAL = "Total Bets";
    private static final String BETS_WON_VAL = "Bets Won";
    private static final String BETS_LOST_VAL = "Bets Lost";
    private static final String WIN_RATE_VAL = "Win Rate";
    private TextView tvAccountBalanceVal, tvBankLoanVal, tvTotalBetsVal, tvBetsWonVal, tvBetsLostVal, tvWinRate, tvHowToPlay;
    private Button btnGetBankLoan, btnPlaceBet;

    private SharedPreferences spConfig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spConfig =  getSharedPreferences("com.apptuned.fantacybetting.Config", this.MODE_PRIVATE);

        tvAccountBalanceVal = (TextView)findViewById(R.id.tv_accountBalanceVal);
        tvBankLoanVal = (TextView)findViewById(R.id.tv_bankLoanVal);
//        tvTotalBetsVal = (TextView)findViewById(R.id.tv_totalBetsVal);
//        tvBetsWonVal = (TextView)findViewById(R.id.tv_betsWonVal);
//        tvBetsLostVal = (TextView)findViewById(R.id.tv_betsLostVal);
//        tvWinRate = (TextView)findViewById(R.id.tv_winRate);

        tvAccountBalanceVal.setText(String.valueOf(spConfig.getInt(ACCOUNT_BALANCE_VAL, 0)));
        tvBankLoanVal.setText(String.valueOf(spConfig.getInt(BANK_LOAN_VAL, 0)));
//        tvTotalBetsVal.setText(String.valueOf(spConfig.getInt(TOTAL_BETS_VAL, 0)));
//        tvBetsWonVal.setText(String.valueOf(spConfig.getInt(BETS_WON_VAL, 0)));
//        tvBtvTotalBetsVal.setText(String.valueOf(spConfig.getInt(TOTAL_BETS_VAL, 0)));
//        tvBetsWonVal.setText(String.valueOf(spConfig.getInt(BETS_WON_VAL, 0)));
//        tetsLostVal.setText(String.valueOf(spConfig.getInt(BETS_LOST_VAL, 0)));
//        tvWinRate.setText("Win rate: " + spConfig.getInt(WIN_RATE_VAL, 0));

        btnGetBankLoan = (Button) findViewById(R.id.btn_getBankLoan);
        btnGetBankLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "You are trying to get a Bank Laon!!", Toast.LENGTH_SHORT).show();
                // TODO Take user to the loan application page!!

            }
        });

        btnPlaceBet = (Button) findViewById(R.id.btn_placeBet);
        btnPlaceBet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Take user to betting page where they choose what league or type of bet to choose!!
                Intent intent = new Intent(getApplicationContext(), LeagueSelectionActivity.class);
                startActivity(intent);
            }
        });

        //TODO Add onclicklistener to tutorial
        tvHowToPlay = (TextView) findViewById(R.id.tv_howToPlay);
        tvHowToPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "We are taking you to the tutorial page in a jiffy!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }



}
