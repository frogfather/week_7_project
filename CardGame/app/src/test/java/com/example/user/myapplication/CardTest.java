package com.example.user.myapplication;
import static org.junit.Assert.assertEquals;
import org.junit.*;

public class CardTest{

  Card card;

  @Before 
  public void before(){
    card = new Card(SuitType.HEART, CardType.TEN,10);
  }

  @Test 
  public void canGetSuit(){
    assertEquals(SuitType.HEART,card.getSuit());
  }

  @Test
  public void canGetValue(){
    assertEquals(10, card.getValue());
  }

  @Test
  public void canGetName(){
    assertEquals(CardType.TEN, card.getName());
  }

}