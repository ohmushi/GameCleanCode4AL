package clean.code.domain.functional.service;

import clean.code.domain.functional.model.Player;
import clean.code.domain.ports.server.PlayerPersistenceSpi;
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
        verify(spi, times(1)).findById(given);
        verifyNoMoreInteractions(spi);
    }

    @Test
    void should_get_empty_option_when_not_found_by_id() {
        val given = UUID.randomUUID();

        when(spi.findById(given)).thenReturn(Option.none());

        val actual = service.findById(given);

        assertThat(actual).isEmpty();
        verify(spi, times(1)).findById(given);
        verifyNoMoreInteractions(spi);
    }

    @Test
    void should_find_all_players_by_nickname() {
        val given = UUID.randomUUID();
        val p = Player.builder().id(given).build();
        val expected = List.of(
                p.withNickname("NickName1"), p.withNickname("NickName2"), p.withNickname("NickName1")
        );
    }

}