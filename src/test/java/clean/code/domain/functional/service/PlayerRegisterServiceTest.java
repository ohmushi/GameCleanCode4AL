package clean.code.domain.functional.service;

import clean.code.domain.ApplicationError;
import clean.code.domain.functional.model.Deck;
import clean.code.domain.functional.model.Player;
import clean.code.domain.functional.service.validation.PlayerValidator;
import clean.code.domain.ports.server.PlayerPersistenceSpi;
import io.vavr.control.Either;
import io.vavr.control.Validation;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static io.vavr.API.Invalid;
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

    @Test
    void should_not_register_when_player_not_valid() {
        val given = Player.builder().nickname("NickName").build();

        try(MockedStatic<PlayerValidator> validator = Mockito.mockStatic(PlayerValidator.class)) {
            validator.when(()-> PlayerValidator.validate(any(Player.class)))
                    .thenReturn(Invalid(new ApplicationError("Invalid Player", null, null)));

            val actual = service.register(given);
            assertThat(actual).containsLeftInstanceOf(ApplicationError.class);

            verifyNoInteractions(spi);
        }
    }

}