package clean.code.domain.ports.server;

import clean.code.domain.ApplicationError;
import clean.code.domain.functional.model.Hero;
import io.vavr.control.Either;

public interface HeroPersistenceSpi {
    Either<ApplicationError, Hero> save(Hero hero);
}
