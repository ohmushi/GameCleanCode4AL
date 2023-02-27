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
                        .flatMap(result -> saveCardAndGetFightResult(Tuple.of(attacker, result))))
                .flatMap(cardAndResult -> updatePlayerTokens(cardAndResult));
    }

    private Either<ApplicationError,FightResult> updatePlayerTokens(Tuple2<Card, FightResult> cardFightResult) {
        if (!cardFightResult._2.isWon()) return Either.right(cardFightResult._2);

        final var playerId = UUID.fromString(cardFightResult._1.getPlayerId());
        return this.playerSpi.findById(playerId)
                .toEither(new ApplicationError("Player not found by cardId", playerId, null))
                .map(this::incrementWinCount)
                .map(this::addOneTokenToPlayerIfFifthWin)
                .flatMap(this.playerSpi::save)
                .map(player -> cardFightResult._2);
    }

    private Player incrementWinCount(Player player) {
        return player.withWinCount(player.getWinCount() + 1);
    }

    private Player addOneTokenToPlayerIfFifthWin(Player player) {
        if(player.getWinCount() >= 5) {
            return player
                    .withTokens(player.getTokens() + 1)
                    .withWinCount(0);
        }
        return player;
    }

    private Either<ApplicationError, Tuple2<Card, FightResult>> saveCardAndGetFightResult(Tuple2<Card, FightResult> cardAndFightResult) {
        return cardSpi.save(resolveFight(cardAndFightResult._1, cardAndFightResult._2))
                .map(card -> cardAndFightResult);
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
