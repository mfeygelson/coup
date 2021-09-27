package coup;

import java.util.Collection;

public record PlayerInformation(String name, int isk, int heldCards, Collection<Influence> revealedCards) {
}
