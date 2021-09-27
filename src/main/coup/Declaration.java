package coup;

import java.util.Set;

public interface Declaration extends Challengeable {
    default Set<Player> whoCanBlock() { return Set.of(); }

    default Set<Influence> whatCanBlock() { return Set.of(); }

    void takeEffect();
}
