package clean.code.domain.functional.service.validation;

import clean.code.domain.ApplicationError;
import clean.code.domain.functional.model.Hero;
import io.vavr.control.Validation;

public interface HeroValidator {
    static Validation<ApplicationError, Hero> validate(Hero hero) {
        if (hero.getName().isEmpty()) {
            return Validation.invalid(new ApplicationError("The name should not be empty", hero, null));
        } else {
            return Validation.valid(hero);
        }
    }
}
