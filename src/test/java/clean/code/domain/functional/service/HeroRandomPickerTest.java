package clean.code.domain.functional.service;

import clean.code.domain.functional.model.Hero;
import clean.code.domain.functional.model.PackType;
import clean.code.domain.ports.server.HeroPersistenceSpi;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.assertj.vavr.api.VavrAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

class HeroRandomPickerTest {

    @Mock
    private HeroPersistenceSpi spi;

    private HeroRandomPicker heroRandomPicker;

    @Test
    void should_get_random_hero_silver_pack() {
        val conf = OpenPackConfigurationFactory.forType(PackType.SILVER);
        val expectedRarity = "COMMON";
        val expected = Hero.builder().name("hero").rarity(expectedRarity).build();

        val actual = heroRandomPicker.pick(conf);

        assertThat(actual).containsRightSame(expected);
    }

}