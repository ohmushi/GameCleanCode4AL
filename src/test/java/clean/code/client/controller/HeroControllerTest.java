package clean.code.client.controller;

import clean.code.client.dto.HeroCreationDto;
import clean.code.client.mapper.HeroDtoMapper;
import clean.code.domain.ApplicationError;
import clean.code.domain.functional.model.Hero;
import clean.code.domain.ports.client.HeroCreatorApi;
import clean.code.domain.ports.client.HeroFinderApi;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Either;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = HeroController.class)
@AutoConfigureMockMvc
@EnableWebMvc
class HeroControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HeroCreatorApi heroCreatorApi;

    @MockBean
    private HeroFinderApi heroFinderApi;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void should_retrieve_all_heroes() throws Exception {
        val expected = List.of(Hero.builder().build());
        when(heroFinderApi.findAll()).thenReturn(Either.right(expected));

        this.mockMvc.perform(get("/heroes"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(expected)));

        verify(heroFinderApi).findAll();
        verifyNoMoreInteractions(heroFinderApi);
    }

    @Test
    void should_not_retrieve_all_heroes() throws Exception {
        val expected = new ApplicationError(null, null, null);
        when(heroFinderApi.findAll()).thenReturn(Either.left(expected));

        this.mockMvc.perform(get("/heroes"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(objectMapper.writeValueAsString(expected)));

        verify(heroFinderApi).findAll();
        verifyNoMoreInteractions(heroFinderApi);
    }

    @Test
    void should_retrieve_hero() throws Exception {
        val id = UUID.randomUUID();
        val expected = Hero.builder().id(id).build();

        when(heroFinderApi.findById(id)).thenReturn(Either.right(expected));

        this.mockMvc.perform(get("/heroes/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(expected)));

        verify(heroFinderApi).findById(id);
        verifyNoMoreInteractions(heroFinderApi);
    }

    @Test
    void should_not_retrieve_hero() throws Exception {
        val id = UUID.randomUUID();
        val expected = new ApplicationError(null, null, null);

        when(heroFinderApi.findById(id)).thenReturn(Either.left(expected));

        this.mockMvc.perform(get("/heroes/" + id))
                .andExpect(status().isNotFound())
                .andExpect(content().string(objectMapper.writeValueAsString(expected)));

        verify(heroFinderApi).findById(id);
        verifyNoMoreInteractions(heroFinderApi);
    }

    @Test
    void should_create_hero() throws Exception {
        val dto = new HeroCreationDto("test", 1, 2, 3, "TANK", "COMMON");
        val expected = HeroDtoMapper.heroCreationToDomain(dto);

        when(heroCreatorApi.create(any(Hero.class))).thenReturn(Either.right(expected));

        this.mockMvc.perform(post("/heroes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(expected)));

        verify(heroCreatorApi).create(any(Hero.class));
        verifyNoMoreInteractions(heroCreatorApi);
    }

    @Test
    void should_not_create_hero() throws Exception {
        val dto = new HeroCreationDto("", 1, 2, 3, "TANK", "COMMON");
        val expected = new ApplicationError(null, null, null);

        when(heroCreatorApi.create(any(Hero.class))).thenReturn(Either.left(expected));

        this.mockMvc.perform(post("/heroes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(objectMapper.writeValueAsString(expected)));

        verify(heroCreatorApi).create(any(Hero.class));
        verifyNoMoreInteractions(heroCreatorApi);
    }
}