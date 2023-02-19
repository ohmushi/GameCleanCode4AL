package clean.code.domain.functional.service;

import clean.code.domain.ApplicationError;
import clean.code.domain.functional.model.Hero;
import clean.code.domain.functional.model.Pack;
import clean.code.domain.functional.model.PackType;
import clean.code.domain.functional.model.Player;
import clean.code.domain.ports.client.PackOpenerApi;
import clean.code.domain.ports.server.PlayerPersistenceSpi;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.collection.Stream;
import io.vavr.control.Either;
import io.vavr.control.Validation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class PackOpenerService implements PackOpenerApi {

    private final PlayerPersistenceSpi playerSpi;
    private final HeroRandomPicker heroRandomPicker;

    @Override
    public Either<ApplicationError, Pack> open(UUID playerId, PackType type) {
        final var configuration = OpenPackConfigurationFactory.forType(type);

        return playerSpi.findById(playerId)
                .toEither(new ApplicationError("Player not found", playerId, null))
                .flatMap(foundPlayer -> checkPlayerHasEnoughTokens(foundPlayer, configuration.requiredNbTokens()).toEither())
                .flatMap(p -> generateRandomPackAndAddHeroesInPlayersDeck(p, type, configuration.nbCards()))
                .map(packAndPlayer -> Tuple.of(packAndPlayer._1, removePlayerTokens(packAndPlayer._2, configuration.requiredNbTokens())))
                .flatMap(this::savePlayerAndGetPack);
    }

    private Validation<ApplicationError, Player> checkPlayerHasEnoughTokens(Player player, Integer requiredNbTokens) {
        return player.getTokens() < requiredNbTokens
                ? Validation.invalid(new ApplicationError("Player does not have enough tokens for this pack", player, null))
                : Validation.valid(player);
    }

    private Either<ApplicationError,Tuple2<Pack, Player>> generateRandomPackAndAddHeroesInPlayersDeck(Player player, PackType type, Integer nbCards) {
        return getRandomPackOfType(type, nbCards)
                .map(pack -> Tuple.of(pack, player.addHeroesInDeck(pack.getHeroes())));
    }

    private Either<ApplicationError, Pack> getRandomPackOfType(PackType type, Integer nbCards) {
        return getRandomHeroesByType(type, nbCards).map(heroes -> new Pack(heroes.toJavaList()));
    }

    private Either<ApplicationError, List<Hero>> getRandomHeroesByType(PackType type, Integer nbCards) {
        final var heroes = Stream.range(0, nbCards)
                .map(i -> heroRandomPicker.pick(type));
        final var errors = heroes.filter(Either::isLeft).map(Either::getLeft);

        return errors.isEmpty()
                ? Either.right(heroes.map(Either::get).toList())
                : Either.left(errors.get(0));
    }

    private Player removePlayerTokens(Player player, Integer requiredNbTokens) {
        return player.withTokens(player.getTokens() - requiredNbTokens);
    }

    private Either<ApplicationError, Pack> savePlayerAndGetPack(Tuple2<Pack, Player> packAndPlayer) {
        return playerSpi.save(packAndPlayer._2)
                .map(p -> packAndPlayer._1);
    }
}
