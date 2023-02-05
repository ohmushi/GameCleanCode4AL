package clean.code.domain.ports.client;

import clean.code.domain.ApplicationError;
import clean.code.domain.functional.model.Player;
import io.vavr.control.Either;

public interface PlayerRegisterApi {
    Either<ApplicationError, Player> register();
}
