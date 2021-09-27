package coup;

import java.util.Queue;

public class Game {
  Queue<Player> activePlayers;
  Player currentPlayer;

  void takeTurn() {
    var declaration = currentPlayer.speak();
    if (survivesChallenge(declaration) && !isBlocked(declaration)) {
      declaration.takeEffect();
    }
    nextTurn();
  }

  private void nextTurn() {
    var nextPlayer = activePlayers.remove();
    activePlayers.add(nextPlayer);
    currentPlayer = nextPlayer;
  }

  private boolean isBlocked(Declaration declaration) {
    for (var blocker : declaration.whoCanBlock()) {
      var counter = blocker.block(declaration);
      if (counter != null) {
        return survivesChallenge(counter);
      }
    }
    return false;
  }

  private boolean survivesChallenge(Challengeable challengeable) {
    if (!challengeable.canBeChallenged()) {
      return true;
    }
    var declaringPlayer = challengeable.declarer();
    for (var challenger : activePlayers) {
      if (challenger == declaringPlayer || !challenger.challenges(challengeable)) {
        continue;
      }
      var revealedCard = declaringPlayer.revealCardFor(challengeable);
      if (challengeable.worksWith(revealedCard)) {
        declaringPlayer.swapOutCard(revealedCard);
        challenger.loseInfluence();
        checkIfAlive(challenger);
        return true;
      } else {
        declaringPlayer.loseCard(revealedCard);
        checkIfAlive(declaringPlayer);
        return false;
      }
    }
    return true;
  }

  private void checkIfAlive(Player player) {
    if (!player.alive()) {
      activePlayers.remove(player);
    }
  }
}
