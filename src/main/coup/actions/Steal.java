package coup.actions;

import coup.*;

import java.util.Set;

import static coup.ActionType.STEAL;
import static coup.Influence.AMBASSADOR;
import static coup.Influence.CAPTAIN;

public class Steal implements Declaration {
  private final Player player;
  private final Player target;

  public Steal(Player player, Player target) {
    this.player = player;
    this.target = target;
  }

  @Override
  public Player declarer() {
    return player;
  }

  @Override
  public boolean worksWith(Card card) {
    return card.isA(CAPTAIN);
  }

  @Override
  public Set<Player> whoCanBlock() {
    return Set.of(target);
  }

  @Override public Set<Influence> whatCanBlock() {
    return Set.of(CAPTAIN, AMBASSADOR);
  }

  @Override
  public void takeEffect() {
    target.payIsk(2);
    player.gainIsk(2);
  }

  @Override
  public ActionInformation information() {
    return new ActionInformation(STEAL, player, target);
  }
}
