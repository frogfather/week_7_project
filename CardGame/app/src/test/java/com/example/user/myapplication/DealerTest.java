import static org.junit.Assert.assertEquals;
import org.junit.*;
import blackjack.*;

public class DealerTest{

  Player player;
  Dealer dealer;
  Card card;
  Deck deck;

  @Before
  public void before(){
    player = new Player("John");
    card = new Card(SuitType.HEART,CardType.EIGHT,8);
    deck = new Deck();
    dealer = new Dealer("Tom",deck);
  }

  @Test
  public void canDealCard(){
    dealer.dealCard(player);
    assertEquals(1,player.getHandSize());
    assertEquals(8, player.getHandValue());
  }

  @Test
  public void dealerCanPlay(){
    dealer.dealCard(dealer);
    assertEquals(1,dealer.getHandSize());
  }
}