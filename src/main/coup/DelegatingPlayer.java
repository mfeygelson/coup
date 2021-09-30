package coup;

import coup.actions.*;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

import static coup.ActionType.COUP;
import static java.util.stream.Collectors.toSet;

public class DelegatingPlayer implements Player {
  private final String name;
  private final Board board;
  private final Agent agent;

  public DelegatingPlayer(String name, Board board, Agent agent) {
    this.name = name;
    this.board = board;
    this.agent = agent;
  }

  @Override
  public Declaration speak() {
    try {
      return switch (chooseAction()) {
        case INCOME -> new Income(this);
        case FOREIGN_AID -> new ForeignAid(this, otherPlayers());
        case TAX -> new Tax(thisPlayer());
        case STEAL -> new Steal(thisPlayer(), findPlayer(agent.chooseTheftTarget(otherPlayersInformation()).get()));
        case ASSASSINATE -> new Assassinate(thisPlayer(), findPlayer(agent.chooseAssassinationTarget(otherPlayersInformation()).get()));
        case EXCHANGE -> new Exchange(thisPlayer());
        case COUP -> new Coup(thisPlayer(), findPlayer(agent.chooseCoupTarget(otherPlayersInformation()).get()));
      };
    } catch (ExecutionException | InterruptedException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  private Set<PlayerInformation> otherPlayersInformation() {
    return otherPlayers().stream().map(Player::information).collect(toSet());
  }

  private Player findPlayer(String playerName) {
    return board.activePlayers()
        .stream()
        .filter(player -> player.name().equals(playerName))
        .findAny()
        .orElseThrow();
  }

  private ActionType chooseAction() {
    if (isk() >= 10) {
      return COUP;
    }
    try {
      return agent.chooseAction(possibleActions(), otherPlayersInformation()).get();
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  @Override
  public String name() {
    return name;
  }

  public int isk() {
    return board.isk(thisPlayer());
  }

  @Override
  public int numberOfCards() {
    return board.numberOfCards(thisPlayer());
  }

  @Override
  public Set<Card> revealedCards() {
    return board.revealedCards(thisPlayer());
  }

  private Set<Player> otherPlayers() {
    return board.activePlayers().stream().filter(player -> player != thisPlayer()).collect(toSet());
  }

  private Player thisPlayer() {
    return this;
  }

  private Set<ActionType> possibleActions() {
    return Stream.of(ActionType.values()).filter(action -> action.iskRequired() <= isk()).collect(toSet());
  }

  @Override
  public boolean challenges(Challengeable challengeable) {
    try {
      return agent.shouldChallenge(challengeable.information()).get();
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  @Override
  public Card revealCardFor(Challengeable challengeable) {
    var influences = influences().stream().toList();
    if (influences.size() == 1) {
      return cardWithInfluence(influences.get(0)).orElseThrow();
    }
    Influence influenceToReveal = null;
    try {
      influenceToReveal = agent.revealInfluence(challengeable.information(), influences()).get();
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
    return cardWithInfluence(influenceToReveal).orElseThrow();
  }

  private Set<Card> cards() {
    return board.heldCards(thisPlayer());
  }

  private Set<Influence> influences() {
    return cards().stream().map(Card::influence).collect(toSet());
  }

  private Optional<Card> cardWithInfluence(Influence influence) {
    return cards().stream().filter(card -> card.influence() == influence).findAny();
  }

  @Override
  public @Nullable Challengeable block(Declaration declaration) {
    Influence blockAs = null;
    try {
      blockAs = agent.blockAs(declaration.information(), declaration.whatCanBlock()).get();
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
    if (blockAs != null) {
      return new Block(thisPlayer(), blockAs);
    }
    return null;
  }

  @Override
  public void swapOutCard(Card card) {
    board.swapOutCard(thisPlayer(), card);
  }

  @Override
  public void loseInfluence() {
    Influence influence = null;
    try {
      influence = agent.chooseInfluenceToLose(influences()).get();
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
    loseCard(cardWithInfluence(influence).orElseThrow());
  }

  @Override
  public void loseCard(Card card) {
    board.revealCard(thisPlayer(), card);
  }

  @Override
  public void gainIsk(int amount) {
    board.addIsk(thisPlayer(), amount);
  }

  @Override
  public void payIsk(int amount) {
    board.removeIsk(thisPlayer(), amount);
  }

  @Override
  public void drawCard() {
    Card card = board.drawCard();
    board.addCard(thisPlayer(), card);
    agent.receivedCard(card.influence());
  }

  @Override
  public void returnCard() {
    Influence influence = null;
    try {
      influence = agent.chooseCardToReturn().get();
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
    Card card = cardWithInfluence(influence).orElseThrow();
    board.removeCardFromPlayer(thisPlayer(), card);
    board.returnCardToDeck(card);
  }

  @Override
  public boolean alive() {
    return cards().size() > 0;
  }
}
