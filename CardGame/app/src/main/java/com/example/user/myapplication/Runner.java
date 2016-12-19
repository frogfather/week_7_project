package com.example.user.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class Runner extends AppCompatActivity{
    private Deck deck;
    private ArrayList<Player> players;
    private Dealer dealer;
    private Boolean dealerIsPlaying;
    private EditText inputBox;
    private Button enterButton;
    private Button newGameButton;
    private String dealerName;
    private CheckBox dealerPlaying;
    private ListView playerList;
    private PlayerAdaptor adapter;
    private TextView playerNameLabel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        inputBox = (EditText)findViewById(R.id.input_box);
        enterButton = (Button)findViewById(R.id.enterButton);
        newGameButton = (Button)findViewById(R.id.game_button);
        dealerPlaying = (CheckBox)findViewById(R.id.dealerPlay);
        playerList = (ListView)findViewById(R.id.playerlist);
        playerNameLabel = (TextView)findViewById(R.id.player_name);
        deck = new Deck();
        players = new ArrayList<Player>();
        adapter = new PlayerAdaptor(this,R.id.playerlist, players);
        playerList.setAdapter(adapter);
        enterButton.setEnabled(false);
        dealerPlaying.setVisibility(View.VISIBLE);
        dealerPlaying.setChecked(false);
        newGameButton.setEnabled(false);

        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                if (dealer == null){
                    setDealer(inputBox.getText().toString());
                    Log.d("Card game", "Dealer set");
                    playerNameLabel.setText(getResources().getString(R.string.player_name));
                    dealerPlaying.setVisibility(View.INVISIBLE);
                }
                else {
                    //set the players, min 2 max 4
                    if (getPlayerCount() < 4) {
                        Player player = new Player(inputBox.getText().toString());
                        addPlayer(player);
                        if (getPlayerCount() > 2) {
                            newGameButton.setEnabled(true);
                        } else {
                            newGameButton.setEnabled(true);
                        }
                    }
                else {
                    //toast to say max players reached?

                    }
                }
                inputBox.setText("");
                playerList.setAdapter(adapter);
            }

        });

        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //show a new view

                Log.d("Card game", "New game button clicked");

                Intent intent = new Intent(Runner.this, PlayGame.class);
                //now add the extras
                intent.putExtra("dealer_name",dealer.getPlayerName());
                intent.putExtra("dealer_playing",dealerIsPlaying);
                for (int i=0; i< players.size();i++){
                    String playerName = getPlayerByPosition(i);
                    if (playerName != dealer.getPlayerName() ){
                        intent.putExtra("player"+String.valueOf(i),playerName);
                    }
                }

                startActivity(intent);

            }
        });


        inputBox.addTextChangedListener(new TextWatcher(){
            String textinput;
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                textinput = s.toString().trim();
                if (textinput.isEmpty() || getPlayerCount()>3){
                    enterButton.setEnabled(false);
                }
                else
                {
                    enterButton.setEnabled(true);
                }
            }
        });



    };

    public void setDealer(String name){
        dealerName = name;
        dealer = new Dealer(dealerName,deck);
        Log.d("Card game","dealer set");
        dealerIsPlaying = dealerPlaying.isChecked();
        if (dealerPlaying.isChecked()){
            addPlayer(dealer);
        }
    }

    public Dealer getDealer(){
        return dealer;
    }

    public void addPlayer(Player player){
        this.players.add(player);
    }

    public int getPlayerCount(){
        return this.players.size();
    };

    public String getPlayerByPosition(int position){
        return this.players.get(position).getPlayerName();
    }
};




