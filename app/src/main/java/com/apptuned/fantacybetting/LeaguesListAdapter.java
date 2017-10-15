package com.apptuned.fantacybetting;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

/**
 * Created by davies on 9/9/17.
 */

public class LeaguesListAdapter  extends RecyclerView.Adapter<LeaguesListAdapter.LeaguesListViewHolder>{

    private ArrayList<League> leagueArrayList;
    private Context context;

    public LeaguesListAdapter(Context context, ArrayList<League> leaguesArray){
        this.context = context;
        this.leagueArrayList = leaguesArray;
    }

    @Override
    public LeaguesListAdapter.LeaguesListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_league_selection_row, parent, false);
        // Pass view to ViewHolder
        LeaguesListViewHolder leaguesListViewHolder = new LeaguesListViewHolder(view);
        return leaguesListViewHolder;
    }

    @Override
    public void onBindViewHolder(LeaguesListAdapter.LeaguesListViewHolder holder, final int position) {
        League currentLeague = leagueArrayList.get(position);
        Resources res = context.getResources();
        String iconURL = currentLeague.getIconURL();
        int resID = res.getIdentifier("com.apptuned.fantacybetting:drawable/" + iconURL, null, null);
        holder.leagueImage.setImageResource(resID);
        holder.leagueName.setText(leagueArrayList.get(position).getName());
        holder.leagueCountry.setText(leagueArrayList.get(position).getCountry());

        // holder.leagueImage -- This will be handled once we know how best to handle images

        // TODO Set onclicklistener for each holder
        holder.itemView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                League selectedLeague = leagueArrayList.get(position);
                if(selectedLeague.getId() == 1){
                    // Show jackpot betting view
                    Intent intent = new Intent(view.getContext(), JackpotBettingActivity.class);
                    intent.putExtra("leagueObj", selectedLeague);
                    view.getContext().startActivity(intent);
                }
                else{
                    // Show the normal betting window
                    Intent intent = new Intent(view.getContext(), NormalBettingActivity.class);
                    intent.putExtra("leagueObj", selectedLeague);
                    view.getContext().startActivity(intent);
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return leagueArrayList.size();
    }

    public class LeaguesListViewHolder extends RecyclerView.ViewHolder{

        TextView leagueName, leagueCountry;
        CircularImageView leagueImage;

        public LeaguesListViewHolder(View itemView){

            super(itemView);

            leagueName = (TextView) itemView.findViewById(R.id.leagueName);
            leagueCountry = (TextView) itemView.findViewById(R.id.leagueCountry);
            leagueImage = (CircularImageView) itemView.findViewById(R.id.leagueImage);
        }
    }
}
