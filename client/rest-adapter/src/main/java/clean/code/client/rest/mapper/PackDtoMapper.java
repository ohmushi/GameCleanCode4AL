package main.java.clean.code.client.rest.mapper;

import main.java.clean.code.client.rest.dto.PackResponse;
import main.java.clean.code.domain.functional.model.Pack;

public interface PackDtoMapper {
    static PackResponse toDto(Pack pack) {
        return new PackResponse(
            pack.getHeroes().stream().map(HeroDtoMapper::toDto).toList()
        );
    }
}
