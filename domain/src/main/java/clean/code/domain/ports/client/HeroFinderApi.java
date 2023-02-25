package main.java.clean.code.domain.ports.client;

import io.vavr.control.Either;
import main.java.clean.code.domain.ApplicationError;
import main.java.clean.code.domain.functional.model.Hero;

import java.util.List;
import java.util.UUID;

public interface HeroFinderApi {
    Either<ApplicationError, List<Hero>> findAll();

    Either<ApplicationError, Hero> findById(UUID id);
}
