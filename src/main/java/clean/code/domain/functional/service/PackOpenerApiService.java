package clean.code.domain.functional.service;

import clean.code.domain.ApplicationError;
import clean.code.domain.functional.model.Deck;
import clean.code.domain.functional.model.Hero;
import clean.code.domain.functional.model.Pack;
import clean.code.domain.functional.model.PackType;
import clean.code.domain.ports.client.PackOpenerApi;
import clean.code.domain.ports.server.PlayerPersistenceSpi;
import io.vavr.collection.Array;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static io.vavr.API.Seq;

@Slf4j
@RequiredArgsConstructor
public class PackOpenerApiService implements PackOpenerApi {

    private PlayerPersistenceSpi playerSpi;
    private HeroRandomPicker heroRandomPicker;

    @Override
    public Either<ApplicationError, Pack> open(UUID playerId, PackType type) {
        final var player = playerSpi.findById(playerId).get();

        var pack = Pack.builder().heroes(
                List.of( heroRandomPicker.pick("COMMON").get()
                        ,heroRandomPicker.pick("COMMON").get()
                        ,heroRandomPicker.pick("COMMON").get())
        ).build();

        playerSpi.save(player
                .withTokens(Math.min(player.getTokens()-1, 0))
                .withDeck(player.getDeck().addHeroes(Array.ofAll(pack.getHeroes()))));
        return Either.right(pack);
    }
}
