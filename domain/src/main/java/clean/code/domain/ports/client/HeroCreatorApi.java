package main.java.clean.code.domain.ports.client;

import io.vavr.control.Either;
import main.java.clean.code.domain.ApplicationError;
import main.java.clean.code.domain.functional.model.Hero;

public interface HeroCreatorApi {
    Either<ApplicationError, Hero> create(Hero hero);
}
