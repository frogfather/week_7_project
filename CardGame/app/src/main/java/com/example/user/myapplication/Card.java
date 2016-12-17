package com.example.user.myapplication;

import android.media.Image;

public class Card{

  private
  SuitType suit;
  CardType name;
  Image cardImage;
  int value;
  
  public Card(SuitType suit, CardType name, int value){
    this.suit = suit;
    this.name = name;
    this.value = value;

  }

  public SuitType getSuit(){
    return this.suit;
  }
  
  public int getValue(){
    return this.value;
  }

  public CardType getName(){
    return this.name;
  }

 }