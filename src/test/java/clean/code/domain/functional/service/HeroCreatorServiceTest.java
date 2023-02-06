package clean.code.domain.functional.service;

import clean.code.domain.functional.model.Hero;
import clean.code.domain.ports.server.HeroPersistenceSpi;
import io.vavr.control.Either;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.vavr.api.VavrAssertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HeroCreatorServiceTest {

    @InjectMocks private HeroCreatorService service;
    @Mock private HeroPersistenceSpi spi;

    @Test
    void should_create_hero() {
        val given = Hero.builder().name("Test hero").build();
        when(spi.save(given)).thenReturn(Either.right(given));

        val actual = service.create(given);
        assertThat(actual).containsRightSame(given);
        verify(spi).save(given);
        verifyNoMoreInteractions(spi);
    }
}