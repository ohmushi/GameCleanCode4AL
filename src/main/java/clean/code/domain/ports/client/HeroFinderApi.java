package clean.code.domain.ports.client;

import clean.code.domain.functional.model.Hero;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HeroFinderApi {
    List<Hero> findAll();

    Optional<Hero> findById(UUID id);
}
