package clean.code.domain.functional.service;

import clean.code.domain.ApplicationError;
import clean.code.domain.functional.model.Pack;
import clean.code.domain.functional.model.PackType;
import clean.code.domain.ports.client.PackOpenerApi;
import clean.code.domain.ports.server.PlayerPersistenceSpi;
import io.vavr.collection.Array;
import io.vavr.collection.Stream;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@Slf4j
@RequiredArgsConstructor
public class PackOpenerService implements PackOpenerApi {

    private PlayerPersistenceSpi playerSpi;
    private HeroRandomPicker heroRandomPicker;

    @Override
    public Either<ApplicationError, Pack> open(UUID playerId, PackType type) {
        final var player = playerSpi.findById(playerId).get();
        final var configuration = OpenPackConfigurationFactory.forType(type);

        final var heroes = Stream.range(0,configuration.nbCards())
                .map(i -> heroRandomPicker.pick("COMMON").get())
                .asJava();
        final var pack = Pack.builder().heroes(heroes).build();

        playerSpi.save(player
                .withTokens(player.getTokens() - configuration.requiredNbTokens())
                .withDeck(player.getDeck().addHeroes(Array.ofAll(pack.getHeroes()))));

        return Either.right(pack);
    }
}
