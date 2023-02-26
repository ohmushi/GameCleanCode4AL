package clean.code.domain.functional.service;

import clean.code.domain.ApplicationError;
import clean.code.domain.functional.model.Hero;
import clean.code.domain.functional.service.validation.HeroValidator;
import clean.code.domain.ports.client.HeroCreatorApi;
import clean.code.domain.ports.server.HeroPersistenceSpi;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class HeroCreatorService implements HeroCreatorApi {

    private final HeroPersistenceSpi spi;

    @Override
    public Either<ApplicationError, Hero> create(Hero hero) {
        return HeroValidator.validate(hero)
                .map(this::applyConfiguration)
                .toEither()
                .flatMap(spi::save);
    }

    private Hero applyConfiguration(Hero hero) {
        HeroCreationConfiguration configuration = HeroCreationConfigurationFactory.forSpeciality(hero.getSpeciality());

        return hero.withHp(configuration.hpAtLevel1())
                .withPower(configuration.powerAtLevel1())
                .withArmor(configuration.armorAtLevel1());
    }
}
