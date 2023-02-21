package clean.code.server.adapter;

import clean.code.domain.functional.model.Hero;
import clean.code.domain.functional.model.Player;
import clean.code.server.entity.HeroEntity;
import clean.code.server.entity.PlayerEntity;
import clean.code.server.mapper.HeroEntityMapper;
import clean.code.server.mapper.PlayerEntityMapper;
import clean.code.server.repository.PlayerRepository;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.assertj.vavr.api.VavrAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerMongoDatabaseAdapterTest {

    @Mock private PlayerRepository repository;

    @InjectMocks private PlayerMongoDatabaseAdapter adapter;

    @Test
    void should_save() {
        val given = Player.builder().nickname("nickname").tokens(3).build();
        val entity = PlayerEntityMapper.toEntity(given);
        when(repository.save(any(PlayerEntity.class))).thenReturn(entity);

        val actual = adapter.save(given);
        VavrAssertions.assertThat(actual).containsRightInstanceOf(Player.class);
        Assertions.assertThat(actual.get()).usingRecursiveComparison().isEqualTo(given);

        verify(repository).save(any(PlayerEntity.class));
        verifyNoMoreInteractions(repository);
    }

}