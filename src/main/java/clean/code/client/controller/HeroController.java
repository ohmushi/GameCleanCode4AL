package clean.code.client.controller;

import clean.code.client.dto.HeroCreationDto;
import clean.code.client.mapper.HeroDtoMapper;
import clean.code.domain.ports.client.HeroCreatorApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/heroes")
public class HeroController {

    private final HeroCreatorApi heroCreatorApi;

    @PostMapping
    public ResponseEntity<Object> createHero(@RequestBody HeroCreationDto dto) {

        return heroCreatorApi
                .create(HeroDtoMapper.heroCreationToDomain(dto)
                .map(HeroDtoMapper::toDto)
                .fold(ResponseEntity.badRequest()::body, ResponseEntity::ok);
    }
}
