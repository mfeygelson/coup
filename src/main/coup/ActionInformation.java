package coup;

import org.jetbrains.annotations.Nullable;

public record ActionInformation(
    ActionType action,
    PlayerInformation player,
    @Nullable PlayerInformation target
) implements ChallengeableInformation {
  public ActionInformation(ActionType action, Player player) {
    this(action, player.information(), null);
  }

  public ActionInformation(ActionType action, Player player, Player target) {
    this(action, player.information(), target.information());
  }
}
