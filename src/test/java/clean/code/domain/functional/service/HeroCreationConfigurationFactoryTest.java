package clean.code.domain.functional.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class HeroCreationConfigurationFactoryTest {

    @ParameterizedTest
    @ValueSource(strings = {"TANK", "ASSASSIN", "WIZARD"})
    void should_contains_good_values_for_speciality(String speciality) {
        var conf = HeroCreationConfigurationFactory.forSpeciality(speciality, "COMMON");
        Assertions.assertThat(conf.hpAtLevel1()).isPositive();
        Assertions.assertThat(conf.armorAtLevel1()).isPositive();
        Assertions.assertThat(conf.powerAtLevel1()).isPositive();
    }

    @ParameterizedTest
    @ValueSource(strings = {"COMMON", "RARE", "LEGENDARY"})
    void should_contains_good_values_for_rarity(String rarity) {
        var conf = HeroCreationConfigurationFactory.forSpeciality("TANK", rarity);
        Assertions.assertThat(conf.hpAtLevel1()).isPositive();
        Assertions.assertThat(conf.armorAtLevel1()).isPositive();
        Assertions.assertThat(conf.powerAtLevel1()).isPositive();
    }

    @ParameterizedTest
    @ValueSource(strings = {"NEW", "HELLO", "MAGICIAN"})
    void should_throw_exception_for_unknown_speciality(String speciality) {
        assertThrows(IllegalArgumentException.class, () -> HeroCreationConfigurationFactory.forSpeciality(speciality, "COMMON"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"NEW", "HELLO", "MAGICIAN"})
    void should_throw_exception_for_unknown_rarity(String rarity) {
        assertThrows(IllegalArgumentException.class, () -> HeroCreationConfigurationFactory.forSpeciality("TANK", rarity));
    }

}