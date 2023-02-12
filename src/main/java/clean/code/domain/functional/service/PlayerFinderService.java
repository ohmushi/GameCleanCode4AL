package clean.code.domain.functional.service;

import clean.code.domain.ApplicationError;
import clean.code.domain.functional.model.Player;
import clean.code.domain.ports.client.PlayerFinderApi;
import clean.code.domain.ports.server.PlayerPersistenceSpi;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class PlayerFinderService implements PlayerFinderApi {

    private final PlayerPersistenceSpi spi;

    @Override
    public Either<ApplicationError, Player> findById(UUID playerId) {
        return null;
    }

    @Override
    public Either<ApplicationError, List<Player>> findAll(String nickname) {
        return null;
    }
}
