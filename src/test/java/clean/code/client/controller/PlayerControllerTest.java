package clean.code.client.controller;

import clean.code.client.dto.PlayerRegistrationRequest;
import clean.code.client.mapper.PackDtoMapper;
import clean.code.client.mapper.PlayerDtoMapper;
import clean.code.domain.ApplicationError;
import clean.code.domain.functional.model.*;
import clean.code.domain.ports.client.PackOpenerApi;
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

    @MockBean
    private PackOpenerApi packOpenerApi;

    ObjectMapper objectMapper = new ObjectMapper();

    private final String baseUrl = "/players";

    @Test
    void should_retrieve_all_players() throws Exception {
        val expected = List.of(Player.builder().build());
        when(playerFinderApi.findAll(null)).thenReturn(Either.right(expected));

        this.mockMvc.perform(get(baseUrl))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string(objectMapper.writeValueAsString(expected.stream()
                                .map(PlayerDtoMapper::toSearchResponse)
                                .toList())));

        verify(playerFinderApi).findAll(null);
        verifyNoMoreInteractions(playerFinderApi);
    }

    @Test
    void should_not_retrieve_all_players() throws Exception {
        val expected = new ApplicationError(null, null, null);
        when(playerFinderApi.findAll("nickname")).thenReturn(Either.left(expected));

        this.mockMvc.perform(get(baseUrl+ "?nickname=nickname"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(objectMapper.writeValueAsString(expected)));

        verify(playerFinderApi).findAll("nickname");
        verifyNoMoreInteractions(playerFinderApi);
    }

    @Test
    void should_retrieve_one_player_by_id() throws Exception {
        val id = UUID.randomUUID();
        val expected = Player.builder().id(id).deck(new Deck(List.of(Hero.builder().name("hero").build()))).build();

        when(playerFinderApi.findById(id)).thenReturn(Option.of(expected));

        this.mockMvc.perform(get(baseUrl + "/" + id.toString()))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(
                        PlayerDtoMapper.toDefaultResponse(expected)
                )));

        verify(playerFinderApi).findById(id);
        verifyNoMoreInteractions(playerFinderApi);
    }

    @Test
    void should_not_retrieve_player_by_id() throws Exception {
        val id = UUID.randomUUID();

        when(playerFinderApi.findById(id)).thenReturn(Option.none());

        this.mockMvc.perform(get(baseUrl + "/" + id))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));

        verify(playerFinderApi).findById(id);
        verifyNoMoreInteractions(playerFinderApi);
    }

    @Test
    void should_register_player() throws Exception {
        val givenRequest = new PlayerRegistrationRequest("nickname");
        val givenPlayer = PlayerDtoMapper.toDomain(givenRequest);
        val expectedPlayer = givenPlayer.withTokens(4).withDeck(Deck.empty());

        when(playerRegisterApi.register(any(Player.class))).thenReturn(Either.right(expectedPlayer));

        this.mockMvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(givenRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(PlayerDtoMapper.toDefaultResponse(expectedPlayer))));

        verify(playerRegisterApi).register(any(Player.class));
        verifyNoMoreInteractions(playerRegisterApi);
    }

    @Test
    void should_not_register_player() throws Exception {
        val givenRequest = new PlayerRegistrationRequest("nickname");
        val expected = new ApplicationError(null, null, null);

        when(playerRegisterApi.register(any(Player.class))).thenReturn(Either.left(expected));

        this.mockMvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(givenRequest)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(objectMapper.writeValueAsString(expected)));

        verify(playerRegisterApi).register(any(Player.class));
        verifyNoMoreInteractions(playerRegisterApi);
    }

    @Test
    void should_open_pack() throws Exception {
        val id = UUID.randomUUID();
        val expectedPack = new Pack(List.of(
                Hero.builder().name("hero1").rarity("COMMON").build(),
                Hero.builder().name("hero2").rarity("COMMON").build(),
                Hero.builder().name("hero3").rarity("RARE").build()
        ));

        when(packOpenerApi.open(id, PackType.SILVER)).thenReturn(Either.right(expectedPack));

        this.mockMvc.perform(get(baseUrl + "/" + id.toString() + "/pack/silver"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(PackDtoMapper.toDto(expectedPack))));

        verify(packOpenerApi).open(id, PackType.SILVER);
        verifyNoMoreInteractions(packOpenerApi);
    }

    @Test
    void should_not_open_pack() throws Exception {
        val id = UUID.randomUUID();
        val expectedError = new ApplicationError(null, null, null);

        when(packOpenerApi.open(id, PackType.SILVER)).thenReturn(Either.left(expectedError));

        this.mockMvc.perform(get(baseUrl + "/" + id.toString() + "/pack/silver"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(objectMapper.writeValueAsString(expectedError)));

        verify(packOpenerApi).open(id, PackType.SILVER);
        verifyNoMoreInteractions(packOpenerApi);
    }

}