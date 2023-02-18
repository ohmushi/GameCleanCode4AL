package clean.code.domain.functional.service;

import clean.code.domain.ApplicationError;
import clean.code.domain.functional.model.Hero;
import clean.code.domain.ports.server.HeroPersistenceSpi;
import io.vavr.control.Either;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.vavr.api.VavrAssertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HeroFinderServiceTest {

    @InjectMocks HeroFinderService service;

    @Mock HeroPersistenceSpi spi;

    @Test
    void should_find_all() {
        val given = Hero.builder().name("Test hero").power(10).armor(10).hp(10).speciality("TANK").rarity("COMMON").build();
        val expected = List.of(given);
        when(spi.findAll()).thenReturn(Either.right(expected));

        val actual = service.findAll();
        assertThat(actual).containsRightSame(expected);

        verify(spi).findAll();
        verifyNoMoreInteractions(spi);
    }

    @Test
    void should_not_find_all() {
        when(spi.findAll()).thenReturn(Either.left(new ApplicationError(null, null, null)));

        val actual = service.findAll();
        assertThat(actual).containsLeftInstanceOf(ApplicationError.class);

        verify(spi).findAll();
        verifyNoMoreInteractions(spi);
    }

    @Test
    void should_find_by_id() {
        val id = UUID.randomUUID();
        val given = Hero.builder().id(id).name("Test hero").power(10).armor(10).hp(10).speciality("TANK").rarity("COMMON").build();
        when(spi.findById(id)).thenReturn(Either.right(Optional.of(given)));

        val actual = service.findById(id);
        assertThat(actual).containsRightSame(given);

        verify(spi).findById(id);
        verifyNoMoreInteractions(spi);
    }

    @Test
    void should_not_find_by_id() {
        val id = UUID.randomUUID();
        when(spi.findById(id)).thenReturn(Either.right(Optional.empty()));

        val actual = service.findById(id);
        assertThat(actual).containsLeftInstanceOf(ApplicationError.class);

        verify(spi).findById(id);
        verifyNoMoreInteractions(spi);
    }
}