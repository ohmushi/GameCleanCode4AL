package clean.code.server.adapter;

import clean.code.domain.ApplicationError;
import clean.code.domain.functional.model.Hero;
import clean.code.server.entity.HeroEntity;
import clean.code.server.mapper.HeroEntityMapper;
import clean.code.server.repository.HeroRepository;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.assertj.vavr.api.VavrAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HeroDatabaseAdapterTest {
    @InjectMocks HeroDatabaseAdapter adapter;

    @Mock HeroRepository repository;

    @Test
    void should_save() {
        val given = Hero.builder().build();
        val entity = HeroEntityMapper.toHeroEntity(given);
        when(repository.save(any(HeroEntity.class))).thenReturn(entity);

        val actual = adapter.save(given);
        VavrAssertions.assertThat(actual).containsRightInstanceOf(Hero.class);
        Assertions.assertThat(actual.get()).usingRecursiveComparison().isEqualTo(given);

        verify(repository).save(any(HeroEntity.class));
        verifyNoMoreInteractions(repository);
    }

    @Test
    void should_not_save_if_repository_crash() {
        val given = Hero.builder().build();
        val throwable = new RuntimeException();
        doThrow(throwable).when(repository).save(any(HeroEntity.class));

        val actual = adapter.save(given);
        VavrAssertions.assertThat(actual).containsLeftInstanceOf(ApplicationError.class);

        verify(repository).save(any(HeroEntity.class));
        verifyNoMoreInteractions(repository);
    }

    @Test
    void should_not_find_by_id() {
        val id = UUID.randomUUID();
        when(repository.findById(id.toString())).thenReturn(Optional.empty());

        val actual = adapter.findById(id);

        Assertions.assertThat(actual).isEmpty();

        verify(repository).findById(id.toString());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void should_find_by_id() {
        val id = UUID.randomUUID();
        val given = Hero.builder().id(id).build();
        val entity = HeroEntityMapper.toHeroEntity(given);
        when(repository.findById(id.toString())).thenReturn(Optional.of(entity));

        val actual = adapter.findById(id);

        VavrAssertions.assertThat(actual).contains(given);

        verify(repository).findById(id.toString());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void should_find_all() {
        val given = Hero.builder().build();
        val entity = HeroEntityMapper.toHeroEntity(given);
        when(repository.findAll()).thenReturn(List.of(entity));

        val actual = adapter.findAll();

        VavrAssertions.assertThat(actual).containsRightInstanceOf(List.class);
        Assertions.assertThat(actual.get().size()).isEqualTo(1);
        Assertions.assertThat(actual.get()).usingRecursiveComparison().isEqualTo(List.of(given));

        verify(repository).findAll();
        verifyNoMoreInteractions(repository);
    }

    @Test
    void should_not_find_all_if_repository_crash() {
        val throwable = new RuntimeException();
        doThrow(throwable).when(repository).findAll();

        val actual = adapter.findAll();
        VavrAssertions.assertThat(actual).containsLeftInstanceOf(ApplicationError.class);

        verify(repository).findAll();
        verifyNoMoreInteractions(repository);
    }

    @Test
    void should_find_by_rarity() {
        val given = Hero.builder().build();
        val entity = HeroEntityMapper.toHeroEntity(given);
        val rarity = "COMMON";

        when(repository.findByRarity(rarity)).thenReturn(List.of(entity));

        val actual = adapter.findByRarity(rarity);

        VavrAssertions.assertThat(actual).containsOnRight(List.of(given));

        verify(repository).findByRarity(rarity);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void should_return_error_if_repository_crash_when_find_by_rarity() {
        val rarity = "COMMON";
        val exeption = new RuntimeException();
        val expectedError = new ApplicationError("Unable to find heroes by rarity", rarity, exeption);
        doThrow(exeption).when(repository).findByRarity(rarity);

        val actual = adapter.findByRarity(rarity);

        VavrAssertions.assertThat(actual).containsOnLeft(expectedError);

        verify(repository).findByRarity(rarity);
        verifyNoMoreInteractions(repository);
    }
}