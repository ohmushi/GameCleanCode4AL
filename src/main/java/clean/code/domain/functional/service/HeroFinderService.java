package clean.code.domain.functional.service;

import clean.code.domain.functional.model.Hero;
import clean.code.domain.ports.client.HeroFinderApi;
import clean.code.domain.ports.server.HeroPersistenceSpi;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class HeroFinderService implements HeroFinderApi {

    private final HeroPersistenceSpi spi;
    @Override
    public List<Hero> findAll() {
        return spi.findAll();
    }

    @Override
    public Optional<Hero> findById(UUID id) {
        return spi.findById(id);
    }
}
