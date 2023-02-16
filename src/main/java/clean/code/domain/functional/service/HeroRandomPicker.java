package clean.code.domain.functional.service;

import clean.code.domain.ApplicationError;
import clean.code.domain.functional.model.Hero;
import clean.code.domain.ports.server.HeroPersistenceSpi;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HeroRandomPicker {

    HeroPersistenceSpi spi;

    Either<ApplicationError, Hero> pick(String rarity) {
        return null;
    }
}
