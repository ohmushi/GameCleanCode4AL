package clean.code.server.adapter;

import clean.code.domain.ApplicationError;
import clean.code.domain.functional.model.Card;
import clean.code.domain.ports.server.CardPersistenceSpi;
import clean.code.server.mapper.CardEntityMapper;
import clean.code.server.repository.CardRepository;
import io.vavr.API;
import io.vavr.control.Either;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CardDatabaseAdapter implements CardPersistenceSpi {

    private final CardRepository repository;

    @Override
    public Option<Card> findById(UUID id) {
        return Option.ofOptional(repository.findById(id.toString())).map(CardEntityMapper::toDomain);
    }

    @Override
    public Either<ApplicationError, Card> save(Card card) {
        return API.Try(() -> repository.save(CardEntityMapper.toCardEntity(card)))
                .toEither()
                .mapLeft(throwable -> new ApplicationError("Unable to save card", card, throwable))
                .map(CardEntityMapper::toDomain);
    }
}
