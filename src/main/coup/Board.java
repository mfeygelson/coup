package coup;

import java.util.Set;

public interface Board {
  void swapOutCard(Player player, Card card);

  void revealCard(Player player, Card surrenderedCard);

  int numberOfCards(Player player);

  void addIsk(Player player, int amount);

  void removeIsk(Player player, int amount);

  Set<Player> activePlayers();

  int isk(Player player);

  Set<Card> revealedCards(Player player);

  Set<Card> heldCards(Player player);

  Card drawCard();

  void addCard(Player player, Card card);

  void removeCardFromPlayer(Player player, Card card);

  void returnCardToDeck(Card card);
}
