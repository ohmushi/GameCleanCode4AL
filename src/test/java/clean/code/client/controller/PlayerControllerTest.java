package clean.code.client.controller;

import clean.code.client.dto.PlayerRegistrationRequest;
import clean.code.client.mapper.PlayerDtoMapper;
import clean.code.domain.ApplicationError;
import clean.code.domain.functional.model.Deck;
import clean.code.domain.functional.model.Player;
import clean.code.domain.ports.client.PlayerFinderApi;
import clean.code.domain.ports.client.PlayerRegisterApi;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Either;
import io.vavr.control.Option;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = PlayerController.class)
@AutoConfigureMockMvc
@EnableWebMvc
class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlayerRegisterApi playerRegisterApi;

    @MockBean
    private PlayerFinderApi playerFinderApi;

    ObjectMapper objectMapper = new ObjectMapper();

    private final String baseUrl = "/players";

    @Test
    void should_retrieve_all_players() throws Exception {
        val expected = List.of(Player.builder().build());
        when(playerFinderApi.findAll(null)).thenReturn(Either.right(expected));

        this.mockMvc.perform(get("/players"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(expected)));

        verify(playerFinderApi).findAll(null);
        verifyNoMoreInteractions(playerFinderApi);
    }

    @Test
    void should_not_retrieve_all_players() throws Exception {
        val expected = new ApplicationError(null, null, null);
        when(playerFinderApi.findAll("nickname")).thenReturn(Either.left(expected));

        this.mockMvc.perform(get(baseUrl))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(objectMapper.writeValueAsString(expected)));

        verify(playerFinderApi).findAll("nickname");
        verifyNoMoreInteractions(playerFinderApi);
    }

    @Test
    void should_retrieve_one_player_by_id() throws Exception {
        val id = UUID.randomUUID();
        val expected = Player.builder().id(id).build();

        when(playerFinderApi.findById(id)).thenReturn(Option.of(expected));

        this.mockMvc.perform(get(baseUrl + "/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(expected)));

        verify(playerFinderApi).findById(id);
        verifyNoMoreInteractions(playerFinderApi);
    }

    @Test
    void should_not_retrieve_player_by_id() throws Exception {
        val id = UUID.randomUUID();
        val expected = new ApplicationError(null, null, null);

        when(playerFinderApi.findById(id)).thenReturn(Option.none());

        this.mockMvc.perform(get(baseUrl + "/" + id))
                .andExpect(status().isNotFound())
                .andExpect(content().string(objectMapper.writeValueAsString(expected)));

        verify(playerFinderApi).findById(id);
        verifyNoMoreInteractions(playerFinderApi);
    }

    @Test
    void should_register_player() throws Exception {
        val givenRequest = new PlayerRegistrationRequest("nickname");
        val givenPlayer = PlayerDtoMapper.toDomain(givenRequest);
        val expectedPlayer = Player.builder().nickname("nickname").tokens(4).deck(Deck.empty()).build();

        when(playerRegisterApi.register(givenPlayer)).thenReturn(Either.right(expectedPlayer));

        this.mockMvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(givenRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(PlayerDtoMapper.toDefaultResponse(expectedPlayer))));

        verify(playerRegisterApi).register(givenPlayer);
        verifyNoMoreInteractions(playerRegisterApi);
    }

    @Test
    void should_not_register_player() throws Exception {
        val givenRequest = new PlayerRegistrationRequest("nickname");
        val givenPlayer = PlayerDtoMapper.toDomain(givenRequest);
        val expected = new ApplicationError(null, null, null);

        when(playerRegisterApi.register(givenPlayer)).thenReturn(Either.left(expected));

        this.mockMvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(givenRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(objectMapper.writeValueAsString(expected)));

        verify(playerRegisterApi).register(givenPlayer);
        verifyNoMoreInteractions(playerRegisterApi);
    }

}