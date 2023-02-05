package clean.code.domain.ports.client;

import clean.code.domain.ApplicationError;
import clean.code.domain.functional.model.Pack;
import clean.code.domain.functional.model.PackType;
import clean.code.domain.functional.model.Player;
import io.vavr.control.Either;

public interface PackOpenerApi {
    Either<ApplicationError, Pack> open(Player player, PackType type);
}
