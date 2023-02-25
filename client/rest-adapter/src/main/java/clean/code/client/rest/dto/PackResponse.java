package clean.code.client.rest.dto;

import java.util.List;

public record PackResponse(
        List<HeroDto> heroes
) {
}
