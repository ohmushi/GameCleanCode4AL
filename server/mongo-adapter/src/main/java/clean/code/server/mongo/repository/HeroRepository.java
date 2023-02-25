package clean.code.server.mongo.repository;

import clean.code.server.mongo.entity.HeroEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HeroRepository extends MongoRepository<HeroEntity, UUID> {

    List<HeroEntity> findByRarity(String rarity);
}
