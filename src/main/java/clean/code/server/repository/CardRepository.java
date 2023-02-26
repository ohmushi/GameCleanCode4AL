package clean.code.server.repository;

import clean.code.server.entity.CardEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CardRepository extends MongoRepository<CardEntity, String> {
}
