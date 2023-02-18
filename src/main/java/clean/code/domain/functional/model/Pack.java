package clean.code.domain.functional.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class Pack {
    List<Hero> heroes;
}
