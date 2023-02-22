package clean.code.domain.functional.service;

import clean.code.domain.ApplicationError;
import clean.code.domain.functional.model.Hero;
import clean.code.domain.functional.model.PackType;
import clean.code.domain.ports.server.HeroPersistenceSpi;
import io.vavr.API;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Random;

import static io.vavr.API.Left;
import static io.vavr.API.Right;

@RequiredArgsConstructor
public class HeroRandomPicker {

    private final HeroPersistenceSpi spi;
    private final Random randomIndexGenerator;
    private final RarityRandomPicker rarityRandomPicker;

    Either<ApplicationError, Hero> pick(PackType type) {
        final var rarity = rarityRandomPicker.pick(type);
        return spi.findByRarity(rarity)
                .flatMap(this::checkHeroListIsNotEmpty)
                .flatMap(this::pickRandom);
    }

    private Either<ApplicationError, List<Hero>> checkHeroListIsNotEmpty(List<Hero> heroes) {
        return heroes.isEmpty() ? Left(new ApplicationError("No hero found for rarity", null, null)) : Right(heroes);
    }

    private Either<ApplicationError, Hero> pickRandom(List<Hero> heroesByRarity) {
        return API.Try(() -> randomIndexGenerator.nextInt(heroesByRarity.size())).toEither()
                .mapLeft(e -> new ApplicationError("Error while picking random hero", null, e))
                .map(randomIndex -> heroesByRarity.get(randomIndex));
    }
}
