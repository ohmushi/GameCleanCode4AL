package clean.code.domain.ports.client;

import clean.code.domain.ApplicationError;
import clean.code.domain.functional.model.Hero;
import io.vavr.control.Either;

public interface HeroFinderApi {
    Either<ApplicationError, Hero> find();
}
