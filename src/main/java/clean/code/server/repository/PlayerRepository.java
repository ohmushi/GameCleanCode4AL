package clean.code.server.repository;

import clean.code.server.entity.PlayerEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlayerRepository extends MongoRepository<PlayerEntity, String> {
}
