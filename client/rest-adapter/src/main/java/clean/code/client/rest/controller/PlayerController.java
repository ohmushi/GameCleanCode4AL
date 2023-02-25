package main.java.clean.code.client.rest.controller;

import lombok.RequiredArgsConstructor;
import main.java.clean.code.client.rest.dto.PlayerRegistrationRequest;
import main.java.clean.code.client.rest.mapper.PackDtoMapper;
import main.java.clean.code.client.rest.mapper.PlayerDtoMapper;
import main.java.clean.code.domain.functional.model.PackType;
import main.java.clean.code.domain.ports.client.PackOpenerApi;
import main.java.clean.code.domain.ports.client.PlayerFinderApi;
import main.java.clean.code.domain.ports.client.PlayerRegisterApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/players")
public class PlayerController {

    private final PlayerRegisterApi playerRegisterApi;
    private final PlayerFinderApi playerFinderApi;
    private final PackOpenerApi packOpenerApi;

    @PostMapping
    public ResponseEntity<Object> registerPlayer(@RequestBody PlayerRegistrationRequest request) {

        return playerRegisterApi
                .register(PlayerDtoMapper.toDomain(request))
                .map(PlayerDtoMapper::toDefaultResponse)
                .fold(ResponseEntity.internalServerError()::body, ResponseEntity::ok);
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
    public ResponseEntity<Object> findOnePlayerById(@PathVariable String id) {
        return playerFinderApi.findById(UUID.fromString(id))
                .map(PlayerDtoMapper::toDefaultResponse)
                .fold(()->ResponseEntity.notFound().build(), ResponseEntity::ok);
    }

    @GetMapping("/{id}/pack/{type}")
    public ResponseEntity<Object> openPack(@PathVariable("id") String id, @PathVariable("type") String type) {
        return packOpenerApi.open(UUID.fromString(id), PackType.valueOf(type.toUpperCase()))
                .map(PackDtoMapper::toDto)
                .fold(ResponseEntity.internalServerError()::body, ResponseEntity::ok);
    }
}
