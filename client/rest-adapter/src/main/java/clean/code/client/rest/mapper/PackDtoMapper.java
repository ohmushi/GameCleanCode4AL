package clean.code.client.rest.mapper;

import clean.code.client.rest.dto.PackResponse;
import clean.code.domain.functional.model.Pack;

public interface PackDtoMapper {
    static PackResponse toDto(Pack pack) {
        return new PackResponse(
            pack.getHeroes().stream().map(HeroDtoMapper::toDto).toList()
        );
    }
}
