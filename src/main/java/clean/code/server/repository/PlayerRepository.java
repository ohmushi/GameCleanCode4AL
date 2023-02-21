package clean.code.server.repository;

import clean.code.server.entity.PlayerEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface PlayerRepository extends MongoRepository<PlayerEntity, UUID> {
}
