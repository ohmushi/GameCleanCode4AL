package clean.code.domain.functional.service;

import clean.code.domain.ApplicationError;
import clean.code.domain.functional.model.FightResult;
import clean.code.domain.ports.client.HeroFighterApi;
import clean.code.domain.ports.server.HeroPersistenceSpi;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class HeroFighterService implements HeroFighterApi {

    private final HeroPersistenceSpi spi;

    public Either<ApplicationError, FightResult> fight(UUID attackerId, UUID defenderId) {

        return Either.right(FightResult.builder().build());
    }
}
