package coup.actions;

import coup.*;

import java.util.Set;

import static coup.ActionType.EXCHANGE;
import static coup.Influence.AMBASSADOR;

public class Exchange implements Declaration {
  private final Player player;

  public Exchange(Player player) {
    this.player = player;
  }

  @Override
  public Player declarer() {
    return player;
  }

  @Override
  public boolean worksWith(Card card) {
    return card.isA(AMBASSADOR);
  }

  @Override
  public void takeEffect() {
    player.drawCard();
    player.drawCard();
    player.returnCard();
    player.returnCard();
  }

  @Override
  public ActionInformation information() {
    return new ActionInformation(EXCHANGE, player);
  }
}
