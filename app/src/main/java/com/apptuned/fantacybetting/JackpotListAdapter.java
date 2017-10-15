package com.apptuned.fantacybetting;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

/**
 * Created by davies on 9/11/17.
 */

public class JackpotListAdapter extends RecyclerView.Adapter<JackpotListAdapter.JackpotListViewHolder> {

    private Context context;
    private ArrayList<BetPair> betPairArrayList;

    public JackpotListAdapter(Context context, ArrayList<BetPair> betPairArrayList){
        this.context = context;
        this.betPairArrayList = betPairArrayList;
    }

    public ArrayList<BetPair> getBetPairArrayList(){
        return this.betPairArrayList;
    }

    @Override
    public JackpotListAdapter.JackpotListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_jackpot_betting_row, parent, false);
        // Pass view to ViewHolder
        JackpotListAdapter.JackpotListViewHolder jackpotListViewHolder = new JackpotListAdapter.JackpotListViewHolder(view);
        return jackpotListViewHolder;
    }

    @Override
    public void onBindViewHolder(JackpotListAdapter.JackpotListViewHolder holder, int position) {
        final int holderPosition = position;
        final BetPair currentBetPair = betPairArrayList.get(position);
        final Club homeTeam = currentBetPair.getHomeClub();
        final Club awayTeam = currentBetPair.getAwayClub();
        holder.homeTeamName.setText(homeTeam.getName());
        holder.awayTeamName.setText(awayTeam.getName());
        holder.gameDate.setText("Today's date");
        holder.stadium.setText(homeTeam.getStadium());

        holder.rgBetChoice.setSelected(false); // So that none is pre selected

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
    }

    @Override
    public int getItemCount() {
        return betPairArrayList.size();
    }


    public class JackpotListViewHolder extends RecyclerView.ViewHolder{

        TextView homeTeamName, awayTeamName, gameDate, stadium;
        CircularImageView homeTeamLogo, awayTeamLogo;
        RadioGroup rgBetChoice;

        public JackpotListViewHolder(View itemView){

            super(itemView);

            homeTeamName = (TextView) itemView.findViewById(R.id.tv_homeTeamName);
            awayTeamName = (TextView) itemView.findViewById(R.id.tv_awayTeamName);
            gameDate = (TextView) itemView.findViewById(R.id.tv_gameDate);
            stadium = (TextView) itemView.findViewById(R.id.tv_stadium);
            homeTeamLogo = (CircularImageView) itemView.findViewById(R.id.img_homeTeamLogo);
            awayTeamLogo = (CircularImageView) itemView.findViewById(R.id.img_awayTeamLogo);
            rgBetChoice = (RadioGroup) itemView.findViewById(R.id.rg_betChoice);

        }
    }
}
