package clean.code.domain.functional.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Getter
@Builder
@EqualsAndHashCode
public class Pack {
    List<Hero> heroes;
}
