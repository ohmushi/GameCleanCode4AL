package clean.code.domain.ports.client;

import clean.code.domain.ApplicationError;
import clean.code.domain.functional.model.FightResult;
import io.vavr.control.Either;

import java.util.UUID;

public interface CardFighterApi {
    Either<ApplicationError, FightResult> fight(UUID attackerId, UUID defenderId);
}
