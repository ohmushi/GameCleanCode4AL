package clean.code.server.adapter;

import clean.code.domain.ApplicationError;
import clean.code.domain.functional.model.Player;
import clean.code.domain.ports.server.PlayerPersistenceSpi;
import io.vavr.API;
import io.vavr.control.Either;
import io.vavr.control.Option;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class InMemoryPlayerRepository implements PlayerPersistenceSpi {

    private final Map<UUID, Player> db = new HashMap<>();

    @Override
    public Either<ApplicationError, Player> save(Player player) {
        this.db.put(player.getId(), player);
        return null;
    }

    @Override
    public Option<Player> findById(UUID playerId) {
        return Option.ofOptional(Optional.ofNullable(this.db.get(playerId)));
    }

    @Override
    public Either<ApplicationError, List<Player>> findAll(String nickname) {
        return Either.right(this.db.values().stream()
                .filter(player -> player.getNickname().contains(nickname))
                .toList());
    }
}
