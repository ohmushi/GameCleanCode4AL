package clean.code.server.adapter;

import clean.code.domain.ApplicationError;
import clean.code.domain.functional.model.Card;
import clean.code.domain.functional.model.Deck;
import clean.code.domain.functional.model.Hero;
import clean.code.domain.functional.model.Player;
import clean.code.server.entity.PlayerEntity;
import clean.code.server.mapper.PlayerEntityMapper;
import clean.code.server.repository.PlayerRepository;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.assertj.vavr.api.VavrAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerMongoDatabaseAdapterTest {

    @Mock private PlayerRepository repository;
    @InjectMocks private PlayerMongoDatabaseAdapter adapter;

    private final Player givenPlayer = Player.builder()
            .nickname("nickname")
            .deck(new Deck(List.of(Card.builder().name("hero").build())))
            .tokens(3)
            .build();

    @Test
    void should_save() {
        val entity = PlayerEntityMapper.toEntity(givenPlayer);
        when(repository.save(any(PlayerEntity.class))).thenReturn(entity);

        val actual = adapter.save(givenPlayer);
        VavrAssertions.assertThat(actual).containsRightInstanceOf(Player.class);
        Assertions.assertThat(actual.get()).usingRecursiveComparison().isEqualTo(givenPlayer);

        verify(repository).save(any(PlayerEntity.class));
        verifyNoMoreInteractions(repository);
    }

    @Test
    void should_not_save_if_repository_crash() {
        val exception = new RuntimeException();
        doThrow(exception).when(repository).save(any(PlayerEntity.class));

        val actual = adapter.save(givenPlayer);
        VavrAssertions.assertThat(actual).containsLeftInstanceOf(ApplicationError.class);

        verify(repository).save(any(PlayerEntity.class));
        verifyNoMoreInteractions(repository);
    }

    @Test
    void should_find_by_id() {
        val id = UUID.randomUUID();
        val entity = PlayerEntityMapper.toEntity(givenPlayer);
        when(repository.findById(id.toString())).thenReturn(Optional.of(entity));

        val actual = adapter.findById(id);

        VavrAssertions.assertThat(actual).contains(givenPlayer);

        verify(repository).findById(id.toString());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void should_not_find_by_id() {
        val id = UUID.randomUUID();
        when(repository.findById(id.toString())).thenReturn(Optional.empty());

        val actual = adapter.findById(id);
        VavrAssertions.assertThat(actual).isEmpty();

        verify(repository).findById(id.toString());
        verifyNoMoreInteractions(repository);
    }

    @ParameterizedTest
    @ValueSource(strings = {"nickname"})
    @NullAndEmptySource
    void should_find_all(String nickname) {
        val entity = PlayerEntityMapper.toEntity(givenPlayer);
        when(repository.findAll(any(Example.class))).thenReturn(List.of(entity));

        val actual = adapter.findAll(nickname);

        VavrAssertions.assertThat(actual).containsOnRight(List.of(givenPlayer));

        verify(repository).findAll(any(Example.class));
        verifyNoMoreInteractions(repository);
    }

    @ParameterizedTest
    @ValueSource(strings = {"nickname"})
    @NullAndEmptySource
    void should_not_find_all_if_repository_crash(String nickname) {
        val exception = new RuntimeException();
        doThrow(exception).when(repository).findAll(any(Example.class));

        val actual = adapter.findAll(nickname);
        VavrAssertions.assertThat(actual).containsLeftInstanceOf(ApplicationError.class);

        verify(repository).findAll(any(Example.class));
        verifyNoMoreInteractions(repository);
    }

}