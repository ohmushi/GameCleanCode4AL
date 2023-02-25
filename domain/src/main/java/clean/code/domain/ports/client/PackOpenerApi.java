package clean.code.domain.ports.client;

import io.vavr.control.Either;
import clean.code.domain.ApplicationError;
import clean.code.domain.functional.model.Pack;
import clean.code.domain.functional.model.PackType;

import java.util.UUID;

public interface PackOpenerApi {
    Either<ApplicationError, Pack> open(UUID playerId, PackType type);
}
