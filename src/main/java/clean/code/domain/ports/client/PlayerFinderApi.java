package clean.code.domain.ports.client;

import clean.code.domain.ApplicationError;
import clean.code.domain.functional.model.Player;
import io.vavr.control.Either;

import java.util.List;
import java.util.UUID;

public interface PlayerFinderApi {
    Either<ApplicationError, Player> findById(UUID playerId);

    Either<ApplicationError, List<Player>> findAll(String nickname);
}
