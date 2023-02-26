package clean.code.domain.functional.service.validation;

import clean.code.domain.ApplicationError;
import clean.code.domain.functional.model.Card;
import io.vavr.control.Validation;

public interface CardOpponentValidator {
    static Validation<ApplicationError, Card> validate(Card card, int level) {
        return card.getLevel() < level
                ? Validation.invalid(new ApplicationError("Opponent card level is too low", card, null))
                : Validation.valid(card);
    }
}
