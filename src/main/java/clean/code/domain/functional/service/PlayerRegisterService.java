package clean.code.domain.functional.service;

import clean.code.domain.ApplicationError;
import clean.code.domain.functional.model.Deck;
import clean.code.domain.functional.model.Player;
import clean.code.domain.functional.service.validation.PlayerValidator;
import clean.code.domain.ports.client.PlayerRegisterApi;
import clean.code.domain.ports.server.PlayerPersistenceSpi;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class PlayerRegisterService implements PlayerRegisterApi {

    private final PlayerPersistenceSpi spi;

    @Override
    public Either<ApplicationError, Player> register(Player player) {
        return PlayerValidator
                .validate(player.withTokens(4).withDeck(Deck.empty()))
                .toEither()
                .peekLeft(error -> log.error("Player not valid for registration", error))
                .flatMap(spi::save);
    }
}
