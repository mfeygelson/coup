package coup.actions;

import coup.*;

import java.util.Set;

import static coup.ActionType.TAX;
import static coup.Influence.DUKE;

public class Tax implements Declaration {
  private final Player player;

  public Tax(Player player) {
    this.player = player;
  }

  @Override
  public Player declarer() {
    return player;
  }

  @Override
  public boolean worksWith(Card card) {
    return card.isA(DUKE);
  }

  @Override
  public void takeEffect() {
    player.gainIsk(3);
  }

  @Override
  public ActionInformation information() {
    return new ActionInformation(TAX, player);
  }
}
