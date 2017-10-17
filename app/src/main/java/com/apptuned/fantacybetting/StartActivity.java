package com.apptuned.fantacybetting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.hbb20.CountryCodePicker;

public class StartActivity extends AppCompatActivity {

    private static final String APP_INITIALIZED = "Application Initialized";
    private static final String DATE_INITIALIZED = "Date Initialized or first access";
    private static final String COUNTRY_CODE = "Country Code";
    private static final String COUNTRY_CODE_WITH_PLUS = "Country Code with Plus";
    private static final String COUNTRY_NAME = "Country Name";
    private static final String ACCOUNT_BALANCE = "Account Balance";
    private static final String SINGLE_BET_COST = "Single bet cost";
    private static final String JACKPOT_BET_COST = "Jackpot cost";

    private String countryCode, countryCodeWithPlus, countryName;

    private SharedPreferences spConfig;
    public CountryCodePicker ccp;
    private Button btnGetStarted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        spConfig = getSharedPreferences("com.apptuned.fantacybetting.Config", this.MODE_PRIVATE);

        ccp = (CountryCodePicker) findViewById(R.id.ccp);

        btnGetStarted = (Button) findViewById(R.id.btn_getStarted);
        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Check if country is selected
                // If country is not selected, show an alert for country to be selected
                if(ccp.getSelectedCountryName() == "" || ccp.getSelectedCountryName() == null){
                    // Country is not selected
                    Toast.makeText(getApplicationContext(), "Select Country to Proceed.", Toast.LENGTH_SHORT).show();
                }
                else{
                    // If country is selected
                    // TODO Flag app as initialized and take user to the accounts page
                    // TODO get COUNTRY_CODE, COUNTRY_CODE_WITTH_PLUS, COUNTRY_NAME
                    countryCode = ccp.getSelectedCountryCode();
                    countryCodeWithPlus = ccp.getSelectedCountryCodeWithPlus();
                    countryName = ccp.getSelectedCountryName();

                    spConfig.edit().putString(COUNTRY_CODE, countryCode).commit();
                    spConfig.edit().putString(COUNTRY_CODE_WITH_PLUS, countryCodeWithPlus).commit();
                    spConfig.edit().putString(COUNTRY_NAME, countryName).commit();
                    spConfig.edit().putInt(ACCOUNT_BALANCE, 20).commit();
                    spConfig.edit().putInt(SINGLE_BET_COST, 10).commit();
                    spConfig.edit().putInt(JACKPOT_BET_COST, 25).commit();

                    spConfig.edit().putString(DATE_INITIALIZED,
                            (new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()))).commit();
                    spConfig.edit().putBoolean(APP_INITIALIZED, true).commit();

                    // Initialization complete... Take user to league selection activity.
                        // Account activity will be removed from this release
                    Intent intent = new Intent(getApplicationContext(), LeagueSelectionActivity.class);
                    startActivity(intent);
                }

            }
        });

        if(spConfig.getBoolean(APP_INITIALIZED, false)){
            // App is initializad, Move to accounts page
            Intent intent = new Intent(getApplicationContext(), LeagueSelectionActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

}
