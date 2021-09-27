package coup;

import org.jetbrains.annotations.Nullable;

import java.util.Set;

public interface Agent {
  ActionType chooseAction(Set<ActionType> possibleActions, Set<PlayerInformation> otherPlayers);

  String chooseTheftTarget(Set<PlayerInformation> players);

  String chooseAssassinationTarget(Set<PlayerInformation> players);

  String chooseCoupTarget(Set<PlayerInformation> players);

  boolean shouldChallenge(ChallengeableInformation challengeable);

  Influence revealInfluence(ChallengeableInformation action, Set<Influence> heldInfluences);

  Influence chooseInfluenceToLose(Set<Influence> heldInfluences);

  void receivedCard(Influence influence);

  Influence chooseCardToReturn();

  @Nullable Influence blockAs(ChallengeableInformation declaration, Set<Influence> influences);
}
