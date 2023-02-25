package clean.code.domain.ports.client;

import io.vavr.control.Either;
import clean.code.domain.ApplicationError;
import clean.code.domain.functional.model.Hero;

public interface HeroCreatorApi {
    Either<ApplicationError, Hero> create(Hero hero);
}
