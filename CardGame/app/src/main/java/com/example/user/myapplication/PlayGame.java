package com.example.user.myapplication;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by user on 18/12/2016.
 */
public class PlayGame extends AppCompatActivity implements View.OnClickListener {
// this class keeps track of whose turn it is, if the game is finished and handles final scores

    private ArrayList<Player> players;
    private Dealer dealer;
    private Deck deck;
    private Game game;
    private int turn;

    TextView p1Cap;
    TextView p2Cap;
    TextView p3Cap;
    TextView p4Cap;

    TextView score_1;
    TextView score_2;
    TextView score_3;
    TextView score_4;

    RelativeLayout main_frame;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);
        p1Cap = (TextView) findViewById(R.id.cap_player_1);
        p2Cap = (TextView) findViewById(R.id.cap_player_2);
        p3Cap = (TextView) findViewById(R.id.cap_player_3);
        p4Cap = (TextView) findViewById(R.id.cap_player_4);

        p1Cap.setOnClickListener(this);
        p2Cap.setOnClickListener(this);
        p3Cap.setOnClickListener(this);
        p4Cap.setOnClickListener(this);



        score_1 = (TextView)findViewById(R.id.score_1);
        score_2 = (TextView)findViewById(R.id.score_2);
        score_3 = (TextView)findViewById(R.id.score_3);
        score_4 = (TextView)findViewById(R.id.score_4);

        main_frame = (RelativeLayout)findViewById(R.id.main_layout);

        //this is temporary!
        for (int i=0; i< main_frame.getChildCount();i++){
            View v = main_frame.getChildAt(i);
            if (v instanceof RelativeLayout){
              //go through its children
               RelativeLayout rl = ((RelativeLayout) v);
                for (int p = 0; p < rl.getChildCount();p++){
                    View w = ((RelativeLayout) v).getChildAt(p);
                    Log.d("Card Game","Child of "+v.toString()+" = "+w.getTag());
                }
            }
        }
        // end of temporary code

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String dealerName = extras.getString("dealer_name");
        deck = new Deck();
        dealer = new Dealer(dealerName,deck);
        players = new ArrayList<Player>();
        if (extras.containsKey("dealer_playing")){
            Boolean dealerPlay = extras.getBoolean("dealer_playing");
            if (dealerPlay == true) {
                addPlayer(dealer);
            }
        }

        String playerName;
        for (int i = 0; i < 4; i++){
           if (extras.containsKey("player"+String.valueOf(i))){
               playerName = extras.getString("player"+String.valueOf(i));
               Player player = new Player(playerName);
               addPlayer(player);
           }
        }


        game = new Game(players,dealer);
        setPlayerCaptions();
        game.deal();
        for (int i = 0; i < game.getPlayerCount();i++){
            Log.d("Card Game",game.getPlayerNameByPosition(i)+" has a score of "+game.getScore(game.getPlayerByPosition(i)));
        }
        setScores();
        turn = 0; //this keeps track of whose turn it is
    }

    @Override
    public void onClick(View v) {
        // default method for handling onClick Events..
        //find which TextView has clicked and set that player to stick
        int playerId = -1;
        switch (v.getId()) {
            case R.id.cap_player_1:
                playerId = 0;
            break;

            case R.id.cap_player_2:
                playerId = 1;

            break;

            case R.id.cap_player_3:
                playerId = 2;
            break;

            case R.id.cap_player_4:
                playerId = 3;
            break;

        };
        if ((playerId == turn) && (playerId < game.getPlayerCount())) {
            Player player = game.getPlayerByPosition(playerId);
            player.setPlayerStick(true);
            nextTurn();
        }


    }

    public void setPlayerCaptions(){
        Log.d("Card Game","player count is"+String.valueOf(game.getPlayerCount()));
        Log.d("Card Game", String.valueOf(R.id.cap_player_1));
        Player player;
        String openBracket;
        String closeBracket;
        String captionToSet;
        for (int i = 0; i < game.getPlayerCount(); i++ ){
            openBracket = "";
            closeBracket = "";
            player = game.getPlayerByPosition(i);
            if (player.getPlayerStick() == true){
                openBracket = "(";
                closeBracket = ")";
            }
            captionToSet = openBracket+player.getPlayerName()+closeBracket;
            String controlToFind = "p"+String.valueOf(i+1)+"cap";
            Object thisControl = getComponentByTag(controlToFind);
            if (thisControl != null && thisControl instanceof TextView){
                if (i == turn){((TextView) thisControl).setTypeface(null, Typeface.BOLD);}
                else {((TextView) thisControl).setTypeface(null, Typeface.NORMAL);}
                ((TextView) thisControl).setText(captionToSet);

            }
        }
    }

    public Object getComponentByTag(String tag){
        Object control = null;
        for (int i=0; i< main_frame.getChildCount();i++){
            View v = main_frame.getChildAt(i);
            if (v instanceof RelativeLayout){
                //go through its children
                RelativeLayout rl = ((RelativeLayout) v);
                for (int p = 0; p < rl.getChildCount();p++){
                    View w = ((RelativeLayout) v).getChildAt(p);
                    String tagName = (String) w.getTag();
                    if (tag.equals(tagName)){
                        control = w;
                        return control;
                    }
                }
            }
        }
        return control;
    }


    public void setScores(){
            score_1.setText(String.valueOf(game.getScore(game.getPlayerByPosition(0))));
            score_2.setText(String.valueOf(game.getScore(game.getPlayerByPosition(1))));
            if (game.getPlayerCount() > 2){
                score_3.setText(String.valueOf(game.getScore(game.getPlayerByPosition(2))));
            }
        if (game.getPlayerCount() > 3){
            score_4.setText(String.valueOf(game.getScore(game.getPlayerByPosition(3))));
        }

    }

    public void nextTurn(){
        Boolean done = false;
        Boolean playerFound= false;
        while (!done){
            turn +=1;
            if (turn == game.getPlayerCount()){ turn = 0;}
            Player player = game.getPlayerByPosition(turn);
            if (!player.getPlayerStick() && !player.getPlayerBust()){
                playerFound = true;
            }
            done = (game.getActivePlayers() == 0 || playerFound);
        }
     Log.d("Card Game", "Active Player is "+ game.getPlayerNameByPosition(turn));
    }



    public void addPlayer(Player player){
        this.players.add(player);
    }


}
