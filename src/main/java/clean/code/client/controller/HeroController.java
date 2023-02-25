package clean.code.client.controller;

import clean.code.client.dto.HeroCreationDto;
import clean.code.client.mapper.FightResultDtoMapper;
import clean.code.client.mapper.HeroDtoMapper;
import clean.code.domain.ports.client.CardFighterApi;
import clean.code.domain.ports.client.HeroCreatorApi;
import clean.code.domain.ports.client.HeroFinderApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/heroes")
public class HeroController {

    private final HeroCreatorApi heroCreatorApi;
    private final CardFighterApi cardFighterApi;
    private final HeroFinderApi heroFinderApi;

    @PostMapping
    public ResponseEntity<Object> createHero(@RequestBody HeroCreationDto dto) {

        return heroCreatorApi
                .create(HeroDtoMapper.heroCreationToDomain(dto))
                .map(HeroDtoMapper::toDto)
                .fold(ResponseEntity.badRequest()::body, ResponseEntity::ok);
    }

    @GetMapping
    public ResponseEntity<Object> findAllHeroes() {
        return heroFinderApi.findAll()
                .map(heroes -> heroes.stream().map(HeroDtoMapper::toDto).toList())
                .fold(ResponseEntity.internalServerError()::body, ResponseEntity::ok);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Object> findOneHero(@PathVariable("id") UUID id) {
        return heroFinderApi.findById(id)
                .map(HeroDtoMapper::toDto)
                .fold(ResponseEntity.notFound()::build, ResponseEntity::ok);
    }

    @GetMapping(path = "/{attacker}/fight/{defender}")
    public ResponseEntity<Object> fight(@PathVariable("attacker") UUID attacker, @PathVariable("defender") UUID defender) {
        return cardFighterApi.fight(attacker, defender)
                .map(FightResultDtoMapper::toDto)
                .fold(ResponseEntity.badRequest()::body, ResponseEntity::ok);
    }
}
