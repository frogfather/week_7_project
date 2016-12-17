import static org.junit.Assert.assertEquals;
import org.junit.*;
import blackjack.*;

public class DeckTest{

 Deck deck;

 @Before
 public void before(){
  deck = new Deck(); 
 }

 @Test
 public void hasFiftyTwo(){
  assertEquals(52,deck.getCardCount());
 }

 
}