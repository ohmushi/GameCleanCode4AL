package clean.code.domain.functional.service;

import clean.code.domain.ApplicationError;
import clean.code.domain.functional.model.Card;
import clean.code.domain.functional.model.FightResult;
import clean.code.domain.functional.model.Player;
import clean.code.domain.functional.service.validation.CardOpponentValidator;
import clean.code.domain.ports.client.CardFighterApi;
import clean.code.domain.ports.server.CardPersistenceSpi;
import clean.code.domain.ports.server.PlayerPersistenceSpi;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class CardFighterService implements CardFighterApi {

    private final CardPersistenceSpi cardSpi;
    private final PlayerPersistenceSpi playerSpi;

    public Either<ApplicationError, FightResult> fight(UUID attackerId, UUID defenderId) {
        return cardSpi.findById(attackerId)
                .toEither(new ApplicationError("Attacker not found", attackerId, null))
                .flatMap(attacker -> cardSpi.findById(defenderId)
                        .toEither(new ApplicationError("Defender not found", attackerId, null))
                        .map(defender -> CardOpponentValidator.validate(defender, attacker.getLevel()))
                        .flatMap(validationResult -> validationResult.isInvalid()
                                ? Either.left(validationResult.getError())
                                : Either.right(validationResult.get()))
                        .map(attacker::fight)
                        .map(result -> saveCardAndGetFightResult(Tuple.of(attacker, result))))
                .flatMap(result -> updatePlayerTokens(attackerId, result));
    }

    private Either<ApplicationError,FightResult> updatePlayerTokens(UUID attackerId, FightResult result) {
        if (!result.isWon()) return Either.right(result);

        return this.playerSpi.findOwnerOfCard(attackerId)
                .toEither(new ApplicationError("Player not found by cardId", attackerId, null))
                .flatMap(this::addOneTokenToPlayerIfFifthWin)
                .flatMap(this.playerSpi::save)
                .map(player -> result);
    }

    private Either<ApplicationError, Player> addOneTokenToPlayerIfFifthWin(Player player) {
        final var numberOfWins = player.getDeck().getCards().stream()
                .map(Card::getHistory)
                .reduce(new ArrayList<FightResult>(), (a, b) -> {
                    a.addAll(b);
                    return a;
                })
                .stream()
                .filter(FightResult::isWon)
                .count();
        if(numberOfWins % 5 == 0) {
            return playerSpi.save(player.withTokens(player.getTokens() + 1));
        } else {
            return Either.right(player);
        }

    }

    private FightResult saveCardAndGetFightResult(Tuple2<Card, FightResult> cardAndFightResult) {
        cardSpi.save(resolveFight(cardAndFightResult._1, cardAndFightResult._2));
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
