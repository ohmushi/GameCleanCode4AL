package clean.code.domain.functional.service.validation;

import clean.code.domain.ApplicationError;
import clean.code.domain.functional.model.Hero;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

class HeroValidatorTest {

    @Test
    void should_validate() {
        val validator = new HeroValidator();
        val given = Hero.builder().name("Test hero").power(10).armor(10).hp(10).speciality("TANK").rarity("COMMON").build();
        val actual = validator.validate(given);
        assertThat(actual).containsValidSame(given);
    }

    @Test
    void should_not_validate_if_invalid_Name() {
        val validator = new HeroValidator();
        val given = Hero.builder().name("").power(10).armor(10).hp(10).speciality("TANK").rarity("COMMON").build();
        val actual = validator.validate(given);
        assertThat(actual).containsInvalidInstanceOf(ApplicationError.class);
    }

    @Test
    void should_not_validate_if_invalid_Power() {
        val validator = new HeroValidator();
        val given = Hero.builder().name("Test hero").power(-1).armor(10).hp(10).speciality("TANK").rarity("COMMON").build();
        val actual = validator.validate(given);
        assertThat(actual).containsInvalidInstanceOf(ApplicationError.class);
    }

    @Test
    void should_not_validate_if_invalid_Armor() {
        val validator = new HeroValidator();
        val given = Hero.builder().name("Test hero").power(10).armor(-1).hp(10).speciality("TANK").rarity("COMMON").build();
        val actual = validator.validate(given);
        assertThat(actual).containsInvalidInstanceOf(ApplicationError.class);
    }

    @Test
    void should_not_validate_if_invalid_Hp() {
        val validator = new HeroValidator();
        val given = Hero.builder().name("Test hero").power(10).armor(10).hp(-1).speciality("TANK").rarity("COMMON").build();
        val actual = validator.validate(given);
        assertThat(actual).containsInvalidInstanceOf(ApplicationError.class);
    }

    @Test
    void should_not_validate_if_invalid_Speciality() {
        val validator = new HeroValidator();
        val given = Hero.builder().name("Test hero").power(10).armor(10).hp(10).speciality("test").rarity("COMMON").build();
        val actual = validator.validate(given);
        assertThat(actual).containsInvalidInstanceOf(ApplicationError.class);
    }

    @Test
    void should_not_validate_if_invalid_Rarity() {
        val validator = new HeroValidator();
        val given = Hero.builder().name("Test hero").power(10).armor(10).hp(10).speciality("TANK").rarity("test").build();
        val actual = validator.validate(given);
        assertThat(actual).containsInvalidInstanceOf(ApplicationError.class);
    }
}