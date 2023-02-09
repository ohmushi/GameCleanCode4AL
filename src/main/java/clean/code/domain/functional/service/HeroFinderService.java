package clean.code.domain.functional.service;

import clean.code.domain.ApplicationError;
import clean.code.domain.functional.model.Hero;
import clean.code.domain.ports.client.HeroFinderApi;
import clean.code.domain.ports.server.HeroPersistenceSpi;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class HeroFinderService implements HeroFinderApi {

    private final HeroPersistenceSpi spi;
    @Override
    public Either<ApplicationError, List<Hero>> findAll() {
        return Either.right(spi.findAll());
    }

    @Override
    public Either<ApplicationError, Hero> findById(UUID id) {
        var hero = spi.findById(id);

        if (hero.isEmpty()) {
            return Either.left(new ApplicationError("Hero not found", null, id, null));
        } else {
            return Either.right(hero.get());
        }
    }
}
