package com.example.user.myapplication;
import java.util.*;

public class Player {
  private String name;
  private boolean stick;
  private boolean bust;
  private ArrayList<Card> hand;

public Player(String name){
  this.name = name;
  this.hand = new ArrayList<Card>();
  this.stick = false;
  this.bust = false;
  }  

public void takeCard(Card card) {
  this.hand.add(card);
}

public int getHandSize(){
  return this.hand.size();
}

public ArrayList<Card> getHand(){
  return this.hand;
}

public Card playCard(){
  if (getHandSize() > 0){
    return this.hand.get(0);
  } 
return null;  
}


public int getHandValue(){
  int sum = 0;
  if (getHandSize() > 0){
    for (Card card : hand) {
      sum += card.getValue();
      }
    }
  return sum;  
  }

public String getPlayerName(){
  return this.name;
}

public boolean getPlayerStick(){
  return this.stick;
}

public boolean getPlayerBust(){
  return this.bust;
}

public void setPlayerStick(Boolean value){
  this.stick = value;
}

public void setPlayerBust(Boolean value){
  this.bust = value;
}
}