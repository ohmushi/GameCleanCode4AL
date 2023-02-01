package clean.code.domain.functional.service;

import clean.code.domain.ApplicationError;
import clean.code.domain.functional.model.Hero;
import clean.code.domain.ports.client.HeroCreatorApi;
import io.vavr.control.Either;

public class HeroCreatorService implements HeroCreatorApi {

    @Override
    public Either<ApplicationError, Hero> create(Hero hero) {
        return Either.right(hero);
    }
}
