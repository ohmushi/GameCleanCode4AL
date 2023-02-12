package clean.code.server.adapter;

import clean.code.domain.functional.model.Hero;
import clean.code.server.entity.HeroEntity;
import clean.code.server.mapper.HeroEntityMapper;
import clean.code.server.repository.HeroRepository;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HeroDatabaseAdapterTest {
    @InjectMocks HeroDatabaseAdapter adapter;

    @Mock HeroRepository repository;

    @Test
    void should_save() {
        val given = Hero.builder().build();
        val entity = HeroEntityMapper.toEntity(given);
        when(repository.save(any(HeroEntity.class))).thenReturn(entity);

        val actual = adapter.save(given);
        Assertions.assertThat(actual.get()).usingRecursiveComparison().isEqualTo(given);

        verify(repository).save(any(HeroEntity.class));
    }

    @Test
    void should_not_find_by_id() {
        val id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());

        val actual = adapter.findById(id);
        Assertions.assertThat(actual).isEmpty();
    }

    @Test
    void should_find_by_id() {
        val id = UUID.randomUUID();
        val given = Hero.builder().id(id).build();
        val entity = HeroEntityMapper.toEntity(given);
        when(repository.findById(id)).thenReturn(Optional.of(entity));

        val actual = adapter.findById(id);
        Assertions.assertThat(actual).isNotEmpty();
        Assertions.assertThat(actual.get()).usingRecursiveComparison().isEqualTo(given);
    }

    @Test
    void should_find_all() {
        val given = Hero.builder().build();
        val entity = HeroEntityMapper.toEntity(given);
        when(repository.findAll()).thenReturn(List.of(entity));

        val actual = adapter.findAll();
        Assertions.assertThat(actual.size()).isEqualTo(1);
        Assertions.assertThat(actual.get(0)).usingRecursiveComparison().isEqualTo(given);
    }
}