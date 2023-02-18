package clean.code.server.adapter;

import clean.code.domain.ApplicationError;
import clean.code.domain.functional.model.Hero;
import clean.code.domain.ports.server.HeroPersistenceSpi;
import clean.code.server.mapper.HeroEntityMapper;
import clean.code.server.repository.HeroRepository;
import io.vavr.API;
import io.vavr.control.Either;
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
        return API.Try(() -> repository.save(HeroEntityMapper.toEntity(hero)))
                .toEither()
                .mapLeft(throwable -> new ApplicationError("Unable to save hero", hero, throwable))
                .map(HeroEntityMapper::toDomain);
    }

    @Override
    public Either<ApplicationError, List<Hero>> findAll() {
        return API.Try(repository::findAll)
                .toEither()
                .mapLeft(throwable -> new ApplicationError("Unable to find all heroes", null, throwable))
                .map(heroes -> heroes.stream().map(HeroEntityMapper::toDomain).toList());
    }

    @Override
    public Either<ApplicationError, Optional<Hero>> findById(UUID id) {
        return API.Try(() -> repository.findById(id))
                .toEither()
                .mapLeft(throwable -> new ApplicationError("Unable to find hero by id", id, throwable))
                .map(optional -> optional.map(HeroEntityMapper::toDomain));
    }
}
