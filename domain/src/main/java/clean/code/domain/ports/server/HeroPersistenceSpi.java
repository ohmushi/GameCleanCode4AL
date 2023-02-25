package main.java.clean.code.domain.ports.server;

import io.vavr.control.Either;
import main.java.clean.code.domain.ApplicationError;
import main.java.clean.code.domain.functional.model.Hero;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HeroPersistenceSpi {
    Either<ApplicationError, Hero> save(Hero hero);

    Either<ApplicationError, List<Hero>> findAll();

    Either<ApplicationError, Optional<Hero>> findById(UUID id);

    Either<ApplicationError, List<Hero>> findByRarity(String expectedRarity);
}
