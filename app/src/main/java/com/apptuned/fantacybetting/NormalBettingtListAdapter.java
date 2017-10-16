package com.apptuned.fantacybetting;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

/**
 * Created by davies on 9/11/17.
 */

public class NormalBettingtListAdapter extends RecyclerView.Adapter<NormalBettingtListAdapter.NormalBettingListViewHolder> {

    private static final String ACCOUNT_BALANCE = "Account Balance";
    private static final String SINGLE_BET_COST = "Single bet cost";
    private static final String JACKPOT_BET_COST = "Jackpot cost";

    private SharedPreferences spConfig;

    private MenuItem miAccountBalance;

    private Context context;
    private ArrayList<BetPair> betPairArrayList;

    public NormalBettingtListAdapter(Context context, ArrayList<BetPair> betPairArrayList){
        this.context = context;
        this.betPairArrayList = betPairArrayList;
        this.miAccountBalance = miAccountBalance;
    }

    public void setMiAccountBalance(MenuItem miAccountBalance){
        this.miAccountBalance = miAccountBalance;
    }

    @Override
    public NormalBettingtListAdapter.NormalBettingListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_normal_betting_row, parent, false);
        // Pass view to ViewHolder
        NormalBettingtListAdapter.NormalBettingListViewHolder normalBettingListViewHolder = new NormalBettingtListAdapter.NormalBettingListViewHolder(view);
        return normalBettingListViewHolder;
    }

    @Override
    public void onBindViewHolder(NormalBettingtListAdapter.NormalBettingListViewHolder holder, int position) {
        final int holderPosition = position;
        final BetPair currentBetPair = betPairArrayList.get(position);
        final Club homeTeam = currentBetPair.getHomeClub();
        final Club awayTeam = currentBetPair.getAwayClub();
        holder.homeTeamName.setText(homeTeam.getName());
        holder.awayTeamName.setText(awayTeam.getName());
        holder.stadium.setText(homeTeam.getStadium());

        holder.rgBetChoice.setSelected(false); // So that none is selected by default

        Resources res = context.getResources();
        // Setting home team icon
        String iconURL = homeTeam.getIconURL();
        int resID = res.getIdentifier("com.apptuned.fantacybetting:drawable/" + iconURL, null, null);
        holder.homeTeamLogo.setImageResource(resID);

        // setting away team icon
        iconURL = awayTeam.getIconURL();
        resID = res.getIdentifier("com.apptuned.fantacybetting:drawable/" + iconURL, null, null);
        holder.awayTeamLogo.setImageResource(resID);

        // TODO Set on checked for the radiogroup
        holder.rgBetChoice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if(i == R.id.rb_homeWin){
                    // TODO Update home win
                    currentBetPair.setUserSelectedClub(homeTeam);
                }
                else if(i == R.id.rb_awayWin){
                    // TODO Update away win
                    currentBetPair.setUserSelectedClub(awayTeam);
                }
                else if(i == R.id.rb_draw){
                    // TODO Update draw. Set to a new club with id 0
                    currentBetPair.setUserSelectedClub(new Club(0));

                }
                else {
                    // TODO Set selectedClub to null.
                    currentBetPair.setUserSelectedClub(null);
                }
            }
        });

        // Handle place bet action
        holder.btnPlaceNormalBet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity hostActivity = (Activity) view.getContext();
                spConfig = view.getContext().getSharedPreferences("com.apptuned.fantacybetting.Config", hostActivity.MODE_PRIVATE);
                MessageDialogActivity messageDialogActivity = new MessageDialogActivity(hostActivity);
                int netEffect = -10;


                if(currentBetPair.getUserSelectedClub() == null){
                    messageDialogActivity.setTitle("Single Bet");
                    messageDialogActivity.setMessage("No prediction selected");
                    messageDialogActivity.setStatus(-1);
                }
                else {
                    if(currentBetPair.getUserSelectedClub() == currentBetPair.getCorrectResultClub()){
                        netEffect += 100; // You win 100 Units
                        messageDialogActivity.setTitle("Congratulations!!!");
                        messageDialogActivity.setMessage("Correct prediction. You have won 100 Fantasy Betting Units.. ");
                        messageDialogActivity.setStatus(-1);
                    }
                    else {
                        messageDialogActivity.setTitle("Incorrect Prediction");
                        messageDialogActivity.setMessage("Your prediction was incorrect. Try again to win.");
                        messageDialogActivity.setStatus(-1);
                    }

                    int accountBalance = spConfig.getInt(ACCOUNT_BALANCE, 0);
                    spConfig.edit().putInt(ACCOUNT_BALANCE, accountBalance + netEffect).commit();

                    // Update menu item
                    if(miAccountBalance != null){
                        miAccountBalance.setTitle("Balance: " + accountBalance + netEffect + " Units");
                    }

                    betPairArrayList.remove(holderPosition);
                    notifyItemRemoved(holderPosition);
                    notifyItemRangeChanged(holderPosition, betPairArrayList.size());

                    // TODO Check if any more bets exist in the list and say no more bets accordingly
                }
                messageDialogActivity.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return betPairArrayList.size();
    }


    public class NormalBettingListViewHolder extends RecyclerView.ViewHolder{

        TextView homeTeamName, awayTeamName, gameDate, stadium;
        CircularImageView homeTeamLogo, awayTeamLogo;
        RadioGroup rgBetChoice;
        Button btnPlaceNormalBet;

        public NormalBettingListViewHolder(View itemView){

            super(itemView);

            homeTeamName = (TextView) itemView.findViewById(R.id.tv_homeTeamName);
            awayTeamName = (TextView) itemView.findViewById(R.id.tv_awayTeamName);
            stadium = (TextView) itemView.findViewById(R.id.tv_stadium);
            homeTeamLogo = (CircularImageView) itemView.findViewById(R.id.img_homeTeamLogo);
            awayTeamLogo = (CircularImageView) itemView.findViewById(R.id.img_awayTeamLogo);
            rgBetChoice = (RadioGroup) itemView.findViewById(R.id.rg_betChoice);
            btnPlaceNormalBet = (Button) itemView.findViewById(R.id.btn_placeNormalBet);
        }
    }
}
