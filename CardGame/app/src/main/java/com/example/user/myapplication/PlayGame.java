package com.example.user.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
    private boolean gameOver;

    TextView p1Cap;
    TextView p2Cap;
    TextView p3Cap;
    TextView p4Cap;

    TextView score_1;
    TextView score_2;
    TextView score_3;
    TextView score_4;

    RelativeLayout player_1;
    RelativeLayout player_2;
    RelativeLayout player_3;
    RelativeLayout player_4;
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


        //these score captions are temporary
        score_1 = (TextView)findViewById(R.id.score_1);
        score_2 = (TextView)findViewById(R.id.score_2);
        score_3 = (TextView)findViewById(R.id.score_3);
        score_4 = (TextView)findViewById(R.id.score_4);


        main_frame = (RelativeLayout)findViewById(R.id.main_layout);
        player_1 = (RelativeLayout)findViewById(R.id.player_1);
        player_2 = (RelativeLayout)findViewById(R.id.player_2);
        player_3 = (RelativeLayout)findViewById(R.id.player_3);
        player_4 = (RelativeLayout)findViewById(R.id.player_4);

        player_1.setOnClickListener(this);
        player_2.setOnClickListener(this);
        player_3.setOnClickListener(this);
        player_4.setOnClickListener(this);

        //get stuff passed from the first activity
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

        //start the game
        game = new Game(players,dealer);
        gameOver = false;
        setPlayerCaptions();
        game.deal();
        game.deal();
        //players get 2 cards initially
        for (int i = 0; i < game.getPlayerCount();i++){
            Log.d("Card Game",game.getPlayerNameByPosition(i)+" has a score of "+game.getScore(game.getPlayerByPosition(i)));
        }
        setScores();
        turn = 0; //this keeps track of whose turn it is
    }

    @Override
    public void onClick(View v) {
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

            case R.id.player_1:
                playerId = 4;
                break;
            case R.id.player_2:
                playerId = 5;
                break;
            case R.id.player_3:
                playerId = 6;
                break;
            case R.id.player_4:
                playerId = 7;
                break;
        };


        if (playerId < game.getPlayerCount()){
            //a caption has been clicked so we want to stick
            if (playerId == turn){
                Player player = game.getPlayerByPosition(playerId);
                player.setPlayerStick(true);
            }
        }
        else
        {
            playerId -= 4;
            if (playerId < game.getPlayerCount()){
                //an image has been clicked so we want to twist
                if (playerId == turn){
                    Player player = game.getPlayerByPosition(playerId);
                    dealer.dealCard(player);
                }
            }

        }
        setScores();
        getNextPlayer();
        if (gameOver) {
            Player winner = game.getFinalScores();
            if (winner != null){
            Log.d("Card Game", winner.getPlayerName()+" has won!");
            //toast to say who has won?
            }
            else
            {
            //toast to say no winner?
                Log.d("Card Game", "no winner");

            }
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
            if (player.getPlayerStick()){
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
        Player player;
        for (int i = 0; i < game.getPlayerCount(); i++){
            player = game.getPlayerByPosition(i);
            //we have a player and a player position so we can set the card images too
            setCardImages(player,i+1);
            int score = game.getScore(player);
            String captionToSet = String.valueOf(score);
            if (score > 21){
                player.setPlayerBust(true);
                Log.d("Card game", player.getPlayerName()+" is bust");
            }

            String controlToFind = "p"+String.valueOf(i+1)+"score";
            Object thisControl = getComponentByTag(controlToFind);
            if (thisControl != null && thisControl instanceof TextView){
                ((TextView) thisControl).setText(captionToSet);
                }

        }

    }

    public void setCardImages(Player player, int position){
        ArrayList<Card> hand;
        hand = player.getHand();
        if (player.getHandSize()>0){
            //we just show four cards - newest on top
            int imagenumber = player.getHandSize();
            if (imagenumber > 4) {imagenumber = 4;}
            for (Card card : hand){
            setImage(position,imagenumber,card.getSuit(),card.getValue());
                imagenumber -=1;
            }
        }
    }

    public void setImage(int player, int cardNumber, SuitType suit, int value){
        String tagToFind = "p"+String.valueOf(player)+"_"+String.valueOf(cardNumber);
        Object imageViewToSet = getComponentByTag(tagToFind);
        Log.d("Card Game", "Object to find "+tagToFind);
        if (imageViewToSet != null && imageViewToSet instanceof ImageView){
            Log.d("Card Game", "Object is "+imageViewToSet.toString());
            // order in suit type is heart, spade, diamond, club
        String imageToFind = String.valueOf(suit).toLowerCase()+"_"+String.valueOf(value);
        Log.d("Card Game", "String to find "+imageToFind);
            Context context = ((ImageView) imageViewToSet).getContext();
            int id = getResources().getIdentifier(imageToFind,"drawable",context.getPackageName());
            Log.d("Card Game", "Id returned "+id);

            ((ImageView) imageViewToSet).setImageResource(id);
        }
    }

    public void getNextPlayer(){
        Boolean done = false;
        Boolean playerFound= false;
        while (!done) {
            turn += 1;
            if (turn == game.getPlayerCount()) {
                turn = 0;
            }
            Player player = game.getPlayerByPosition(turn);
            Log.d("Card Game", "Checking if : " + player.getPlayerName() + " can play");

            if (!player.getPlayerStick() && !player.getPlayerBust()) {
                Log.d("Card Game", "Player found");
                playerFound = true;
            }
            int active = game.getActivePlayers();
            Log.d("Card Game", "Active Players: " + active);

            done = ((active == 0) || playerFound);
        }
        if (!playerFound){
                Log.d("Card Game", "Game is finished");
                setPlayerCaptions();
                gameOver = true;
        }
        else
            {
                Log.d("Card Game", "Active Player is "+ game.getPlayerNameByPosition(turn));
                setPlayerCaptions();
            }

    }



    public void addPlayer(Player player){
        this.players.add(player);
    }


}
