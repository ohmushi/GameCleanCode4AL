package clean.code.domain.functional.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Builder
public class Pack {
    List<Hero> heroes;
}
