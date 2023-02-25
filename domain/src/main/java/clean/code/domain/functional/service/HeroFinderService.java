package main.java.clean.code.domain.functional.service;

import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import main.java.clean.code.domain.ApplicationError;
import main.java.clean.code.domain.functional.model.Hero;
import main.java.clean.code.domain.ports.client.HeroFinderApi;
import main.java.clean.code.domain.ports.server.HeroPersistenceSpi;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class HeroFinderService implements HeroFinderApi {

    private final HeroPersistenceSpi spi;
    @Override
    public Either<ApplicationError, List<Hero>> findAll() {
        return spi.findAll();
    }

    @Override
    public Either<ApplicationError, Hero> findById(UUID id) {
        return spi.findById(id).flatMap(hero -> {
            if (hero.isEmpty()) {
                return Either.left(new ApplicationError("Hero not found", id, null));
            } else {
                return Either.right(hero.get());
            }
        });
    }
}
