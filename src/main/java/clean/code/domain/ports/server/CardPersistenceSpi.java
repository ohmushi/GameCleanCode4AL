package clean.code.domain.ports.server;

import clean.code.domain.ApplicationError;
import clean.code.domain.functional.model.Card;
import io.vavr.control.Either;
import io.vavr.control.Option;

import java.util.UUID;

public interface CardPersistenceSpi {

    Option<Card> findById(UUID id);

    Either<ApplicationError, Card> save(Card card);
}
