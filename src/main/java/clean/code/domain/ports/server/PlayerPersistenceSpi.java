package clean.code.domain.ports.server;

import clean.code.domain.ApplicationError;
import clean.code.domain.functional.model.Player;
import io.vavr.control.Either;
import io.vavr.control.Option;

import java.util.List;
import java.util.UUID;

public interface PlayerPersistenceSpi {
    Either<ApplicationError, Player> save(Player player);

    Option<Player> findById(UUID playerId);

    Either<ApplicationError, List<Player>> findAll(String nickname);
}
