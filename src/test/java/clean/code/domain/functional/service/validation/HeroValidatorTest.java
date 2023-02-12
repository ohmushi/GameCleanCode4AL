package clean.code.domain.functional.service.validation;

import clean.code.domain.ApplicationError;
import clean.code.domain.functional.model.Hero;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.vavr.api.VavrAssertions.assertThat;

@ExtendWith(MockitoExtension.class)
class HeroValidatorTest {
    @InjectMocks private HeroValidator validator;

    private Hero hero = Hero.builder().name("Test hero").power(10).armor(10).hp(10).speciality("TANK").rarity("COMMON").build();

    @Test
    void should_validate() {
        val actual = validator.validate(hero);
        assertThat(actual).containsValidSame(hero);
    }

    @Test
    void should_not_validate_if_invalid_Name() {
        val given = hero.withName("");
        val actual = validator.validate(given);
        assertThat(actual).containsInvalidInstanceOf(ApplicationError.class);
    }

    @Test
    void should_not_validate_if_invalid_Power() {
        val given = hero.withPower(-1);
        val actual = validator.validate(given);
        assertThat(actual).containsInvalidInstanceOf(ApplicationError.class);
    }

    @Test
    void should_not_validate_if_invalid_Armor() {
        val given = hero.withArmor(-1);
        val actual = validator.validate(given);
        assertThat(actual).containsInvalidInstanceOf(ApplicationError.class);
    }

    @Test
    void should_not_validate_if_invalid_Hp() {
        val given = hero.withHp(-1);
        val actual = validator.validate(given);
        assertThat(actual).containsInvalidInstanceOf(ApplicationError.class);
    }

    @Test
    void should_not_validate_if_invalid_Speciality() {
        val given = hero.withSpeciality("test");
        val actual = validator.validate(given);
        assertThat(actual).containsInvalidInstanceOf(ApplicationError.class);
    }

    @Test
    void should_not_validate_if_invalid_Rarity() {
        val given = hero.withRarity("test");
        val actual = validator.validate(given);
        assertThat(actual).containsInvalidInstanceOf(ApplicationError.class);
    }
}