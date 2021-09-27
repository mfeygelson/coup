package coup.actions;

import coup.*;

import java.util.Set;

import static coup.ActionType.INCOME;

public class Income implements Declaration {

  private final Player player;

  public Income(Player player) {
    this.player = player;
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
    player.gainIsk(1);
  }

  @Override
  public ActionInformation information() {
    return new ActionInformation(INCOME, player);
  }
}
