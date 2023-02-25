package clean.code.server.adapter;

import main.java.clean.code.domain.ApplicationError;
import main.java.clean.code.domain.functional.model.Player;
import main.java.clean.code.domain.ports.server.PlayerPersistenceSpi;
import clean.code.server.entity.PlayerEntity;
import clean.code.server.mapper.PlayerEntityMapper;
import clean.code.server.repository.PlayerRepository;
import io.vavr.API;
import io.vavr.control.Either;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class PlayerMongoDatabaseAdapter implements PlayerPersistenceSpi {

    private final PlayerRepository repository;

    @Override
    public Either<ApplicationError, Player> save(Player player) {
        return API.Try(() -> repository.save(PlayerEntityMapper.toEntity(player)))
                .toEither()
                .mapLeft(e -> new ApplicationError("Unable to save Error in MongoDb", player, e))
                .map(PlayerEntityMapper::toDomain);
    }

    @Override
    public Option<Player> findById(UUID playerId) {
        return Option.ofOptional(repository.findById(playerId.toString())).map(PlayerEntityMapper::toDomain);
    }

    @Override
    public Either<ApplicationError, List<Player>> findAll(String nickname) {
        final var example = Example.of(PlayerEntity.builder().nickname(nickname).build());
        return API.Try(() -> repository.findAll(example))
                .toEither()
                .mapLeft(e -> new ApplicationError("Unable to find players by nickname", example, e))
                .map(players -> players.stream().map(PlayerEntityMapper::toDomain).toList());
    }
}
