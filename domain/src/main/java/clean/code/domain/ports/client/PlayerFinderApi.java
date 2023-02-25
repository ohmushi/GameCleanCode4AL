package main.java.clean.code.domain.ports.client;

import io.vavr.control.Either;
import io.vavr.control.Option;
import main.java.clean.code.domain.ApplicationError;
import main.java.clean.code.domain.functional.model.Player;

import java.util.List;
import java.util.UUID;

public interface PlayerFinderApi {
    Option<Player> findById(UUID playerId);

    Either<ApplicationError, List<Player>> findAll(String nickname);
}
