package coup;

public record BlockInformation(

    PlayerInformation player, Influence claim) implements ChallengeableInformation {
}
