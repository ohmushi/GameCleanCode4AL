package clean.code.domain.ports.client;

import io.vavr.control.Either;
import clean.code.domain.ApplicationError;
import clean.code.domain.functional.model.Player;

public interface PlayerRegisterApi {
    Either<ApplicationError, Player> register(Player player);
}
