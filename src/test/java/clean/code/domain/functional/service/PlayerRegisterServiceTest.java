package clean.code.domain.functional.service;

import clean.code.domain.functional.model.Deck;
import clean.code.domain.functional.model.Player;
import clean.code.domain.ports.server.PlayerPersistenceSpi;
import clean.code.domain.ports.service.PlayerRegisterService;
import io.vavr.control.Either;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.vavr.api.VavrAssertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerRegisterServiceTest {

    @InjectMocks
    private PlayerRegisterService service;
    @Mock
    private PlayerPersistenceSpi spi;
    @Captor
    private ArgumentCaptor<Player> playerCaptor;


    @Test
    void should_register_player() {
        val given = Player.builder().nickname("NickName").build();
        val expected = given.withTokens(4).withDeck(Deck.empty());

        when(spi.save(expected)).thenReturn(Either.right(expected));


        val actual = service.register(given);
        assertThat(actual).containsRightSame(expected);

        verify(spi, times(1)).save(playerCaptor.capture());
        Assertions.assertThat(playerCaptor.getValue()).isEqualTo(expected);
        verifyNoMoreInteractions(spi);
    }

}