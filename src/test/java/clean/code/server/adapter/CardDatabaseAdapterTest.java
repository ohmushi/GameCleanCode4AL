package clean.code.server.adapter;

import clean.code.domain.ApplicationError;
import clean.code.domain.functional.model.Card;
import clean.code.server.entity.CardEntity;
import clean.code.server.mapper.CardEntityMapper;
import clean.code.server.repository.CardRepository;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.assertj.vavr.api.VavrAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardDatabaseAdapterTest {
    @InjectMocks CardDatabaseAdapter adapter;

    @Mock
    CardRepository repository;

    @Test
    void should_find_by_id() {
        val id = UUID.randomUUID();

        val given = Card.builder().id(id).build();
        val entity = CardEntityMapper.toCardEntity(given);
        when(repository.findById(id.toString())).thenReturn(Optional.of(entity));

        val actual = adapter.findById(id);

        VavrAssertions.assertThat(actual).contains(given);

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

    @Test
    void should_save() {
        val given = Card.builder().build();
        val entity = CardEntityMapper.toCardEntity(given);
        when(repository.save(any(CardEntity.class))).thenReturn(entity);

        val actual = adapter.save(given);

        VavrAssertions.assertThat(actual).containsRightInstanceOf(Card.class);
        Assertions.assertThat(actual.get()).usingRecursiveComparison().isEqualTo(given);

        verify(repository).save(any(CardEntity.class));
        verifyNoMoreInteractions(repository);
    }

    @Test
    void should_not_save_if_repository_crash() {
        val given = Card.builder().build();
        val throwable = new RuntimeException();
        doThrow(throwable).when(repository).save(any(CardEntity.class));

        val actual = adapter.save(given);

        VavrAssertions.assertThat(actual).containsLeftInstanceOf(ApplicationError.class);

        verify(repository).save(any(CardEntity.class));
        verifyNoMoreInteractions(repository);
    }
}