package clean.code.domain.functional.service;

import clean.code.domain.ApplicationError;
import clean.code.domain.functional.model.Card;
import clean.code.domain.functional.model.FightResult;
import clean.code.domain.ports.client.CardFighterApi;
import clean.code.domain.ports.server.CardPersistenceSpi;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class CardFighterService implements CardFighterApi {

    private final CardPersistenceSpi spi;

    public Either<ApplicationError, FightResult> fight(UUID attackerId, UUID defenderId) {
        return spi.findById(attackerId)
                .toEither(new ApplicationError("Attacker not found", attackerId, null))
                .flatMap(attacker -> spi.findById(defenderId)
                        .toEither(new ApplicationError("Defender not found", attackerId, null))
                        .map(attacker::fight)
                        .map(result -> saveCardAndGetFightResult(Tuple.of(attacker, result))));
    }

    private FightResult saveCardAndGetFightResult(Tuple2<Card, FightResult> cardAndFightResult) {
        spi.save(resolveFight(cardAndFightResult._1, cardAndFightResult._2));
        return cardAndFightResult._2;
    }

    private Card resolveFight(Card attacker, FightResult fightResult) {
        List<FightResult> history = new ArrayList<>(attacker.getHistory());
        history.add(fightResult);

        if (fightResult.isWon()) {
            if (attacker.getXp() + 1 == 5) {
                return attacker.withLevel(attacker.getLevel() + 1)
                        .withXp(0)
                        .withHp(attacker.getHp() + (int) (attacker.getHp() * 0.1))
                        .withPower(attacker.getPower() + (int) (attacker.getPower() * 0.1))
                        .withArmor(attacker.getArmor() + (int) (attacker.getArmor() * 0.1))
                        .withHistory(history);
            } else {
                return attacker.withXp(attacker.getXp() + 1)
                        .withHistory(history);
            }
        } else {
            return attacker.withHistory(history);
        }
    }
}
