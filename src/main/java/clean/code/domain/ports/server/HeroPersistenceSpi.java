package clean.code.domain.ports.server;

import clean.code.domain.ApplicationError;
import clean.code.domain.functional.model.Hero;
import io.vavr.control.Either;
import io.vavr.control.Option;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HeroPersistenceSpi {
    Either<ApplicationError, Hero> save(Hero hero);

    Either<ApplicationError, List<Hero>> findAll();

    Option<Hero> findById(UUID id);

    Either<ApplicationError, List<Hero>> findByRarity(String expectedRarity);
}
