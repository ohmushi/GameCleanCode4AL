package clean.code.client.mapper;

import clean.code.client.dto.PackResponse;
import main.java.clean.code.domain.functional.model.Pack;

public interface PackDtoMapper {
    static PackResponse toDto(Pack pack) {
        return new PackResponse(
            pack.getHeroes().stream().map(HeroDtoMapper::toDto).toList()
        );
    }
}
