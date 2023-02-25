package main.java.clean.code.domain.ports.client;

import io.vavr.control.Either;
import main.java.clean.code.domain.ApplicationError;
import main.java.clean.code.domain.functional.model.Pack;
import main.java.clean.code.domain.functional.model.PackType;

import java.util.UUID;

public interface PackOpenerApi {
    Either<ApplicationError, Pack> open(UUID playerId, PackType type);
}
