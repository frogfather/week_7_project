package com.example.user.myapplication;
import java.util.*;

public class Deck implements Dealable{

private ArrayList<Card> deck;
private Card card;

public Deck(){
  this.deck = new ArrayList<Card>();
  populate();
  }

public void populate(){
  //generates A-K of each suit
  for (SuitType suit : SuitType.values()) {
    int cardValue = 1;
    for (CardType cardName : CardType.values()){
      Card card = new Card(suit,cardName,cardValue);
      this.deck.add(card);
      cardValue++;
    }

  } 
}

public int getCardCount(){
  return this.deck.size();
}

public Card getCard(int position){
  return deck.get(position);
  }
}