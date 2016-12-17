import static org.junit.Assert.assertEquals;
import org.junit.*;
import blackjack.*;

public class PlayerTest{

  Player player;
  Card card;

  @Before 
  public void before(){
    player = new Player("John");
    card = new Card(SuitType.HEART,CardType.EIGHT,8);
  }

  @Test
  public void canTakeCard(){
    this.player.takeCard(card);
    assertEquals(1,this.player.getHandSize());
  }

  @Test
  public void canGetHandValue(){
    this.player.takeCard(card);
    assertEquals(8, this.player.getHandValue());
  }

}