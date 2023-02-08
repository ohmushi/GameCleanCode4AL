package clean.code.server.adapter;

import clean.code.domain.ApplicationError;
import clean.code.domain.functional.model.Hero;
import clean.code.domain.ports.server.HeroPersistenceSpi;
import clean.code.server.repository.HeroRepository;
import io.vavr.control.Either;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class HeroDatabaseAdapter implements HeroPersistenceSpi {

    private final HeroRepository repository;

    @Override
    public Either<ApplicationError, Hero> save(Hero hero) {
        return null;
    }

    @Override
    public List<Hero> findAll() {
        return Collections.emptyList();
    }

    @Override
    public Optional<Hero> findById(UUID id) {
        return Optional.empty();
    }
}
