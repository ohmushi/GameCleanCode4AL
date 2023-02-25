package clean.code.server.mongo.repository;

import clean.code.server.mongo.entity.PlayerEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlayerRepository extends MongoRepository<PlayerEntity, String> {
}
