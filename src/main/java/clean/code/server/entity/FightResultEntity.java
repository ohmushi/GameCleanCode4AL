package clean.code.server.entity;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FightResultEntity {
    private CardEntity opponent;
    private boolean won;
}
