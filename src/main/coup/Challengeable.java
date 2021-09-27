package coup;

public interface Challengeable {
  default boolean canBeChallenged() {
    return true;
  }

  Player declarer();

  boolean worksWith(Card card);

  ChallengeableInformation information();
}
