package com.example.user.myapplication;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import java.util.*;


public class Game {

    private ArrayList<Player> players;
    private Dealer dealer;



    public String getPlayerNameByPosition(int position){
        return this.players.get(position).getPlayerName();
    }

    public Player getPlayerByPosition(int position){
            return this.players.get(position);
    }


public Game(ArrayList<Player> players, Dealer dealer){
    this.players = players;
    this.dealer = dealer;

}

public int getActivePlayers(){
    int active = 0;
    for (int i = 0; i < getPlayerCount(); i++){
        Player player = getPlayerByPosition(i);
        if (!player.getPlayerStick() && !player.getPlayerBust()){
            active += 1;
        }
    }
    return active;
}

public ArrayList<Player> getPlayers(){
    return this.players;
}

    public int getScore(Player player){
    int sum = 0;
    boolean haveAce = false;
    ArrayList<Card> hand = player.getHand();
    if (player.getHandSize() > 0){
      for (Card card : hand) {
        int cardValue = card.getValue();
        if (cardValue > 10) {cardValue = 10;}
        if (cardValue == 1){haveAce = true;}
        sum += cardValue;
        //if we have aces we need to decide if we can make them
        //value 11.
        }
      if ((sum < 12) && (haveAce == true)){
        sum += 10;
        }

      }
    return sum;
  }


    public void deal(){
            for (int i = 0; i < players.size(); i++) {
                dealer.dealCard(getPlayerByPosition(i));
            }
    }

//  public void play(){
//    // for (Type variable: arraylist) {do something}
//  Boolean finished = false;
//  String stick;
//  int activePlayers = getPlayerCount();
//  int score = 0;
//  Player winner = null;
//  while (!finished) {
//    String output = "Active players "+activePlayers;
//    tempBox.setText(output);
//    for (Player player: players){
//
//      if ((!player.getPlayerStick()) && (!player.getPlayerBust())){
//        dealer.dealCard(player);
//
//        if (player.getHandSize() == 1) {
//          dealer.dealCard(player);
//        }
//      score = getScore(player);
//      //check score - if bust don't need to ask if wants to stick
//      if (score > 21){
//        player.setPlayerBust(true);
//        activePlayers--;
//        output = "Oh dear "+player.getPlayerName()+" is bust! ("+score+")";
//        tempBox.setText(output);
//        }
//      else {
//       output = player.getPlayerName()+" has "+player.getHandSize()+" cards, value "+score+". Stick? y/n ";
//       inputBox.setText(output);
//       stick = inputBox.getText().toString();
//
//       if (stick.equals("y")) {
//          player.setPlayerStick(true);
//          activePlayers--;
//          }
//        }
//      }
//    else
//      {
//       score = getScore(player);
//       if (player.getPlayerStick()){
//        output = player.getPlayerName()+" is sticking at "+score;
//        tempBox.setText(output);
//       }
//       if (player.getPlayerBust()){
//        output = player.getPlayerName()+" is bust ";
//        tempBox.setText(output);
//       }
//      }
//      if (activePlayers == 0) {finished = true;}
//
//    }
//  }
//  winner = getFinalScores();
//  if (winner != null) {
//  String result = winner.getPlayerName()+" is the winner with "+getScore(winner)+" - Yay!";
//  tempBox.setText(result);
//  }
//  else {
//      String result = "No winner in this round!";
//      tempBox.setText(result);
//  }
//}
//

    public int getPlayerCount(){
        return this.players.size();
    };



public Player getFinalScores(){
  int highScore = 0;
  Player winner = null;
  highScore = 0;
  for (Player player: players){
    if (!player.getPlayerBust()){
      int playerScore = getScore(player);
      if (playerScore > highScore) {
        highScore = playerScore;
        winner = player;
        }
      }
    }
  return winner;
  }



};

