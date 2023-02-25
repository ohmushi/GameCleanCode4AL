package clean.code.domain.functional.service;

import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import clean.code.domain.ApplicationError;
import clean.code.domain.functional.model.Hero;
import clean.code.domain.functional.service.validation.HeroValidator;
import clean.code.domain.ports.client.HeroCreatorApi;
import clean.code.domain.ports.server.HeroPersistenceSpi;

@Slf4j
@RequiredArgsConstructor
public class HeroCreatorService implements HeroCreatorApi {

    private final HeroPersistenceSpi spi;

    private final HeroValidator validator;

    @Override
    public Either<ApplicationError, Hero> create(Hero hero) {
        return validator.validate(hero)
                .toEither()
                .flatMap(spi::save);
    }
}
