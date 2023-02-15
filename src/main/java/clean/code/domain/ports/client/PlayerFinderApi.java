package clean.code.domain.ports.client;

import clean.code.domain.ApplicationError;
import clean.code.domain.functional.model.Player;
import io.vavr.control.Either;

import java.util.List;

public interface PlayerFinderApi {
    Either<ApplicationError, Player> findOne();

    Either<ApplicationError, List<Player>> findAll();
}
