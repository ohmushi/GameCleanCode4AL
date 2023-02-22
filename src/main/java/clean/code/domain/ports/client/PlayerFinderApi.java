package clean.code.domain.ports.client;

import clean.code.domain.ApplicationError;
import clean.code.domain.functional.model.Player;
import io.vavr.control.Either;
import io.vavr.control.Option;

import java.util.List;
import java.util.UUID;

public interface PlayerFinderApi {
    Option<Player> findById(UUID playerId);

    Either<ApplicationError, List<Player>> findAll(String nickname);
}
