package coup.actions;

import coup.*;

import java.util.Set;

import static coup.ActionType.FOREIGN_AID;
import static coup.Influence.DUKE;

public class ForeignAid implements Declaration {
  private final Player player;
  private final Set<Player> otherPlayers;

  public ForeignAid(Player player, Set<Player> otherPlayers) {
    this.player = player;
    this.otherPlayers = otherPlayers;
  }

  @Override
  public boolean canBeChallenged() {
    return false;
  }

  @Override
  public Player declarer() {
    return player;
  }

  @Override
  public boolean worksWith(Card card) {
    return true;
  }

  @Override
  public Set<Player> whoCanBlock() {
    return otherPlayers;
  }

  @Override
  public Set<Influence> whatCanBlock() {
    return Set.of(DUKE);
  }

  @Override
  public void takeEffect() {
    player.gainIsk(2);
  }

  @Override
  public ActionInformation information() {
    return new ActionInformation(FOREIGN_AID, player);
  }
}
