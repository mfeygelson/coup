package coup.actions;

import coup.*;

import static coup.ActionType.COUP;

public class Coup implements Declaration {
  private final Player player;
  private final Player target;

  public Coup(Player player, Player target) {
    this.player = player;
    this.target = target;
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
  public void takeEffect() {
    player.payIsk(7);
    target.loseInfluence();
  }

  @Override
  public ActionInformation information() {
    return new ActionInformation(COUP, player, target);
  }
}
