package coup;

import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.stream.Collectors;

public interface Player {
    String name();

    int isk();

    int numberOfCards();

    Set<Card> revealedCards();

    Declaration speak();

    boolean challenges(Challengeable declaration);

    Card revealCardFor(Challengeable declaration);

    @Nullable Challengeable block(Declaration declaration);

    void swapOutCard(Card card);

    void loseInfluence();

    void loseCard(Card card);

    void gainIsk(int amount);

    void payIsk(int amount);

    void drawCard();

    void returnCard();

    boolean alive();

    default PlayerInformation information() {
        return new PlayerInformation(
            name(),
            isk(),
            numberOfCards(),
            revealedCards().stream().map(Card::influence).collect(Collectors.toSet()));
    }
}
