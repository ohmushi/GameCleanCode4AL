package clean.code.client.controller;

import clean.code.client.dto.PlayerRegistrationRequest;
import clean.code.client.mapper.PlayerDtoMapper;
import clean.code.domain.ports.client.PlayerFinderApi;
import clean.code.domain.ports.client.PlayerRegisterApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/players")
public class PlayerController {

    private final PlayerRegisterApi playerRegisterApi;

    private final PlayerFinderApi playerFinderApi;
    @PostMapping
    public ResponseEntity<Object> registerPlayer(@RequestBody PlayerRegistrationRequest request) {

        return playerRegisterApi
                .register(PlayerDtoMapper.toDomain(request))
                .map(PlayerDtoMapper::toDefaultResponse)
                .fold(ResponseEntity.badRequest()::body, ResponseEntity::ok);
    }

    @GetMapping
    public ResponseEntity<Object> findAllPlayers(
            @RequestParam(required = false) String nickname
    ) {
        return playerFinderApi.findAll(nickname)
                .map(players -> players.stream().map(PlayerDtoMapper::toSearchResponse).toList())
                //.map(responses -> responses.stream().map(response -> response.withUrl()))
                .fold(ResponseEntity.internalServerError()::body, ResponseEntity::ok);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Object> findOneHero(@PathVariable("id") UUID id) {
        return playerFinderApi.findById(id)
                .map(PlayerDtoMapper::toDefaultResponse)
                .fold(()->ResponseEntity.notFound().build(), ResponseEntity::ok);
    }
}
