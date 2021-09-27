package coup.actions;

import coup.*;

import java.util.Set;

import static coup.ActionType.ASSASSINATE;
import static coup.Influence.ASSASSIN;
import static coup.Influence.CONTESSA;

public class Assassinate implements Declaration {
  private final Player player;
  private final Player target;

  public Assassinate(Player player, Player target) {
    this.player = player;
    this.target = target;
  }

  @Override
  public Player declarer() {
    return player;
  }

  @Override
  public boolean worksWith(Card card) {
    return card.isA(ASSASSIN);
  }

  @Override
  public Set<Player> whoCanBlock() {
    return Set.of(target);
  }

  @Override
  public Set<Influence> whatCanBlock() {
    return Set.of(CONTESSA);
  }

  @Override
  public void takeEffect() {
    player.payIsk(3);
    target.loseInfluence();
  }

  @Override
  public ActionInformation information() {
    return new ActionInformation(ASSASSINATE, player, target);
  }
}
