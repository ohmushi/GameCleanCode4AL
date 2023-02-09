package clean.code.domain.ports.server;

import clean.code.domain.ApplicationError;
import clean.code.domain.functional.model.Player;
import io.vavr.control.Either;

public interface PlayerPersistenceSpi {
    Either<ApplicationError, Player> save(Player player);
}
