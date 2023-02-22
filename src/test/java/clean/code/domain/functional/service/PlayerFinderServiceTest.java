package clean.code.domain.functional.service;

import clean.code.domain.ApplicationError;
import clean.code.domain.functional.model.Player;
import clean.code.domain.ports.server.PlayerPersistenceSpi;
import io.vavr.control.Either;
import io.vavr.control.Option;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.vavr.api.VavrAssertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerFinderServiceTest {

    @Mock
    private PlayerPersistenceSpi spi;
    @InjectMocks
    private PlayerFinderService service;

    @Test
    void should_find_player_by_id() {
        val given = UUID.randomUUID();
        val expected = Player.builder().id(given).nickname("NickName").build();

        when(spi.findById(given)).thenReturn(Option.of(expected));

        val actual = service.findById(given);

        assertThat(actual).contains(expected);
        verify(spi).findById(given);
        verifyNoMoreInteractions(spi);
    }

    @Test
    void should_get_empty_option_when_not_found_by_id() {
        val given = UUID.randomUUID();

        when(spi.findById(given)).thenReturn(Option.none());

        val actual = service.findById(given);

        assertThat(actual).isEmpty();
        verify(spi).findById(given);
        verifyNoMoreInteractions(spi);
    }

    @Test
    void should_find_all_players_by_nickname() {
        val given = "NickName";
        val b = Player.builder();
        val expected = List.of(
                  b.build().withNickname("NickName1")
                , b.build().withNickname("NickName2")
                , b.build().withNickname("NickName3")
        );

        when(spi.findAll(given)).thenReturn(Either.right(expected));

        val actual = service.findAll(given);

        assertThat(actual).containsRightSame(expected);
        verify(spi).findAll(given);
        verifyNoMoreInteractions(spi);
    }

    @Test
    void should_get_either_left() {
        val given = "nickname";
        val expected = new ApplicationError(null, null, null);
        when(spi.findAll(anyString()))
                .thenReturn(Either.left(expected));

        val actual = service.findAll(given);

        assertThat(actual).containsLeftSame(expected);
        verify(spi).findAll(given);
        verifyNoMoreInteractions(spi);
    }

}