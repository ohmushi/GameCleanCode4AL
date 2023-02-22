package clean.code.client.dto;

import java.util.List;

public record PackResponse(
        List<HeroDto> heroes
) {
}
