package clean.code.domain.functional.service.validation;

import clean.code.domain.ApplicationError;
import clean.code.domain.functional.model.Hero;
import clean.code.domain.ports.client.HeroFinderApi;
import io.vavr.control.Validation;
import lombok.AllArgsConstructor;

import java.util.Objects;

public interface HeroValidator {

    static Validation<ApplicationError, Hero> validate(Hero hero) {
        if (hero.getName().isEmpty()) {
            return Validation.invalid(new ApplicationError("The name should not be empty", hero, null));
        } else if (!Objects.equals(hero.getSpeciality(), "TANK") && !Objects.equals(hero.getSpeciality(), "ASSASSIN") && !Objects.equals(hero.getSpeciality(), "WIZARD")) {
            return Validation.invalid(new ApplicationError("The speciality should be TANK, ASSASSIN or WIZARD", hero, null));
        } else if (!Objects.equals(hero.getRarity(), "LEGENDARY") && !Objects.equals(hero.getRarity(), "RARE") && !Objects.equals(hero.getRarity(), "COMMON")) {
            return Validation.invalid(new ApplicationError("The rarity should be Legendary, Rare or Common", hero, null));
        } else if (hero.getPower() < 0) {
            return Validation.invalid(new ApplicationError("The power should be positive", hero, null));
        } else if (hero.getArmor() < 0) {
            return Validation.invalid(new ApplicationError("The armor should be positive", hero, null));
        } else if (hero.getHp() < 0) {
            return Validation.invalid(new ApplicationError("The hp should be positive", hero, null));
       } else {
            return Validation.valid(hero);
        }
    }
}
