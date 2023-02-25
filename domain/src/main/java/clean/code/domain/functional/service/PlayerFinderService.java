package main.java.clean.code.domain.functional.service;

import io.vavr.control.Either;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.java.clean.code.domain.ApplicationError;
import main.java.clean.code.domain.functional.model.Player;
import main.java.clean.code.domain.ports.client.PlayerFinderApi;
import main.java.clean.code.domain.ports.server.PlayerPersistenceSpi;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class PlayerFinderService implements PlayerFinderApi {

    private final PlayerPersistenceSpi spi;

    @Override
    public Option<Player> findById(UUID playerId) {
        return spi
                .findById(playerId)
                .onEmpty(() -> log.info("Player not found", playerId));
    }

    @Override
    public Either<ApplicationError, List<Player>> findAll(String nickname) {
        return spi.findAll(nickname)
                .peekLeft(error -> log.info(String.format("Error during finding players with nickname [%s]", nickname), error));
    }
}
