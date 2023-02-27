package clean.code.domain.functional.service;

import clean.code.domain.ApplicationError;
import clean.code.domain.functional.model.Hero;
import clean.code.domain.functional.service.validation.HeroValidator;
import clean.code.domain.functional.service.validation.PlayerValidator;
import clean.code.domain.ports.server.HeroPersistenceSpi;
import io.vavr.control.Either;
import io.vavr.control.Validation;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.vavr.api.VavrAssertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HeroCreatorServiceTest {

    @InjectMocks private HeroCreatorService service;
    @Mock private HeroPersistenceSpi spi;

    @Test
    void should_create() {
        HeroCreationConfiguration configuration = HeroCreationConfigurationFactory.forSpeciality("TANK", "COMMON");
        val given = Hero.builder().name("Test hero")
                .power(configuration.powerAtLevel1())
                .armor(configuration.armorAtLevel1())
                .hp(configuration.hpAtLevel1())
                .speciality("TANK").rarity("COMMON").build();

        when(spi.save(given)).thenReturn(Either.right(given));

        try(MockedStatic<HeroValidator> validator = Mockito.mockStatic(HeroValidator.class)) {
            validator.when(() -> HeroValidator.validate(given)).thenReturn(Validation.valid(given));
            val actual = service.create(given);
            assertThat(actual).containsRightSame(given);
            verify(spi).save(given);
            verifyNoMoreInteractions(spi);
        }
    }

    @Test
    void should_not_create_if_invalid() {
        val given = Hero.builder().name("Test hero").power(10).armor(10).hp(10).speciality("TANK").rarity("COMMON").build();

        val error = new ApplicationError(null, null, null);

        try(MockedStatic<HeroValidator> validator = Mockito.mockStatic(HeroValidator.class)) {
            validator.when(() -> HeroValidator.validate(given)).thenReturn(Validation.invalid(error));
            val actual = service.create(given);
            assertThat(actual).containsLeftSame(error);
            verifyNoInteractions(spi);
        }
    }
}