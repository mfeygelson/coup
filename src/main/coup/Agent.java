package coup;

import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.concurrent.Future;

public interface Agent {
  Future<ActionType> chooseAction(Set<ActionType> possibleActions, Set<PlayerInformation> otherPlayers);

  Future<String> chooseTheftTarget(Set<PlayerInformation> players);

  Future<String> chooseAssassinationTarget(Set<PlayerInformation> players);

  Future<String> chooseCoupTarget(Set<PlayerInformation> players);

  Future<Boolean> shouldChallenge(ChallengeableInformation challengeable);

  Future<Influence> revealInfluence(ChallengeableInformation action, Set<Influence> heldInfluences);

  Future<Influence> chooseInfluenceToLose(Set<Influence> heldInfluences);

  void receivedCard(Influence influence);

  Future<Influence> chooseCardToReturn();

  Future<@Nullable Influence> blockAs(ChallengeableInformation declaration, Set<Influence> influences);
}
