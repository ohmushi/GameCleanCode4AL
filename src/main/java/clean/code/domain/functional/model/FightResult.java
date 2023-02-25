package clean.code.domain.functional.model;

import lombok.Builder;
import lombok.Value;
import lombok.With;

@With
@Value
@Builder
public class FightResult {
    Card opponent;
    boolean won;
}
