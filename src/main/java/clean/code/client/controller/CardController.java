package clean.code.client.controller;

import clean.code.client.mapper.FightResultDtoMapper;
import clean.code.domain.ports.client.CardFighterApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/cards")
public class CardController {
    private final CardFighterApi cardFighterApi;

    @GetMapping(path = "/{attacker}/fight/{defender}")
    public ResponseEntity<Object> fight(@PathVariable("attacker") UUID attacker, @PathVariable("defender") UUID defender) {
        return cardFighterApi.fight(attacker, defender)
                .map(FightResultDtoMapper::toDto)
                .fold(ResponseEntity.badRequest()::body, ResponseEntity::ok);
    }
}
