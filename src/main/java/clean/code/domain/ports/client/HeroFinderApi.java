package clean.code.domain.ports.client;

import clean.code.domain.ApplicationError;
import clean.code.domain.functional.model.Hero;
import io.vavr.control.Either;

import java.util.List;
import java.util.UUID;

public interface HeroFinderApi {
    Either<ApplicationError, List<Hero>> findAll();

    Either<ApplicationError, Hero> findById(UUID id);
}
