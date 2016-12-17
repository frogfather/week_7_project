package com.example.user.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by user on 16/12/2016.
 */
public class PlayerAdaptor extends ArrayAdapter {
    public PlayerAdaptor(Context context,int resource, ArrayList<Player> players) {
        super(context, resource, players);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Player player = (Player)getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_player, parent, false);
        }
        // Lookup view for data population
        TextView playerName = (TextView) convertView.findViewById(R.id.player_name);
        // Populate the data into the template view using the data object
        playerName.setText(player.getPlayerName());
        // Return the completed view to render on screen
        return convertView;
    }



}
