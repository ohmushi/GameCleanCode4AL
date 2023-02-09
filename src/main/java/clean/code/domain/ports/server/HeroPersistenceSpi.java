package clean.code.domain.ports.server;

import clean.code.domain.ApplicationError;
import clean.code.domain.functional.model.Hero;
import io.vavr.control.Either;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HeroPersistenceSpi {
    Either<ApplicationError, Hero> save(Hero hero);

    List<Hero> findAll();

    Optional<Hero> findById(UUID id);
}
