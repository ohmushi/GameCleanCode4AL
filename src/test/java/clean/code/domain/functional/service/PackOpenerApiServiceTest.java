package clean.code.domain.functional.service;

import clean.code.domain.functional.model.PackType;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.vavr.api.VavrAssertions.assertThat;

@ExtendWith(MockitoExtension.class)
class PackOpenerApiServiceTest {

    @Mock
    private RandomHeroPicker randomHeroPicker;

    @InjectMocks
    private PackOpenerApiService service;

    @Test
    void should_open_silver_pack() {
        val given_player_id = UUID.randomUUID();

        val actual = service.open(given_player_id, PackType.SILVER);

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(
                        Pac
                )
    }

}