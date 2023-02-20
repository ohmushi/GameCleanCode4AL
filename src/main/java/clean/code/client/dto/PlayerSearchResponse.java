package clean.code.client.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.With;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PlayerSearchResponse {
    String id;
    String nickname;
    @With String url;
}
