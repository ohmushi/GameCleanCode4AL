package clean.code.client.controller;

import clean.code.domain.ApplicationError;
import clean.code.domain.functional.model.FightResult;
import clean.code.domain.ports.client.CardFighterApi;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Either;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = CardController.class)
@AutoConfigureMockMvc
@EnableWebMvc
class CardControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CardFighterApi cardFighterApi;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void should_fight() throws Exception {
        val expected = FightResult.builder().build();
        val attackerId = UUID.randomUUID();
        val defenderId = UUID.randomUUID();

        when(cardFighterApi.fight(any(UUID.class), any(UUID.class))).thenReturn(Either.right(expected));

        this.mockMvc.perform(get("/" + attackerId + "/fight/" + defenderId))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(expected)));

        verify(cardFighterApi).fight(attackerId, defenderId);
        verifyNoMoreInteractions(cardFighterApi);
    }

    @Test
    void should_return_error_when_fight() throws Exception {
        val expected = new ApplicationError(null, null, null);
        val attackerId = UUID.randomUUID();
        val defenderId = UUID.randomUUID();

        when(cardFighterApi.fight(any(UUID.class), any(UUID.class))).thenReturn(Either.left(expected));

        this.mockMvc.perform(get("/" +  attackerId + "/fight/" + defenderId))
                .andExpect(status().isBadRequest());

        verify(cardFighterApi).fight(attackerId, defenderId);
        verifyNoMoreInteractions(cardFighterApi);
    }
}