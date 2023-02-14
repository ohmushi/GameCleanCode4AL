package clean.code.domain.functional.service;

import clean.code.domain.ApplicationError;
import clean.code.domain.functional.model.Pack;
import clean.code.domain.functional.model.PackType;
import clean.code.domain.ports.client.PackOpenerApi;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class PackOpenerApiService implements PackOpenerApi {
    @Override
    public Either<ApplicationError, Pack> open(UUID playerId, PackType type) {
        return null;
    }
}
