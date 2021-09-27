package coup;

public class Block implements Challengeable {
  private final Player player;
  private final Influence claim;

  public Block(Player player, Influence claim) {
    this.player = player;
    this.claim = claim;
  }

  @Override
  public Player declarer() {
    return player;
  }

  @Override
  public boolean worksWith(Card card) {
    return card.isA(claim);
  }

  @Override
  public BlockInformation information() {
    return new BlockInformation(player.information(), claim);
  }
}
