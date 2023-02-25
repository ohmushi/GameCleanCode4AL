package main.java.clean.code.domain.functional.service.validation;

import io.vavr.control.Validation;
import main.java.clean.code.domain.ApplicationError;
import main.java.clean.code.domain.functional.model.Hero;

import java.util.List;

public class HeroValidator {

    public Validation<ApplicationError, Hero> validate(Hero hero) {
        List<String> speciality = List.of("TANK", "ASSASSIN", "WIZARD");
        List<String> rarity = List.of("LEGENDARY", "RARE", "COMMON");

        if (hero.getName().isEmpty()) {
            return Validation.invalid(new ApplicationError("The name should not be empty", hero, null));
        } else if (!speciality.contains(hero.getSpeciality())) {
            return Validation.invalid(new ApplicationError("The speciality should be " + String.join(" or ", speciality), hero, null));
        } else if (!rarity.contains(hero.getRarity())) {
            return Validation.invalid(new ApplicationError("The rarity should be " + String.join(" or ", rarity), hero, null));
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
