package clean.code.domain.ports.service;

import clean.code.domain.ApplicationError;
import clean.code.domain.functional.model.Deck;
import clean.code.domain.functional.model.Player;
import clean.code.domain.ports.client.PlayerRegisterApi;
import clean.code.domain.ports.server.PlayerPersistenceSpi;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PlayerRegisterService implements PlayerRegisterApi {

    private final PlayerPersistenceSpi spi;

    @Override
    public Either<ApplicationError, Player> register(Player player) {
        //TODO validator
        return spi.save(player.withTokens(4).withDeck(Deck.empty()));
    }
}
