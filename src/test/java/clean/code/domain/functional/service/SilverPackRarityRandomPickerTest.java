package clean.code.domain.functional.service;

import clean.code.domain.functional.model.PackType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SilverPackRarityRandomPickerTest {

    @Mock private Random random;
    @InjectMocks private RarityRandomPicker picker;

    @ParameterizedTest
    @CsvSource({"0,COMMON", "74,COMMON",
                "75,RARE", "94,RARE",
                "95,LEGENDARY", "99,LEGENDARY"
    })
    void should_pick(Integer randomValue, String expectedRarity) {
        try(MockedStatic<OpenPackConfigurationFactory> factory = mockStatic(OpenPackConfigurationFactory.class)) {
            factory.when(() -> OpenPackConfigurationFactory.forType(PackType.SILVER))
                    .thenReturn(new OpenPackConfiguration(0, 0,
                            5, 20, 75));

            when(random.nextInt(anyInt())).thenReturn(randomValue);

            assertThat(picker.pick(PackType.SILVER)).isEqualTo(expectedRarity);

            factory.verify(() -> OpenPackConfigurationFactory.forType(PackType.SILVER));
        }
    }

}