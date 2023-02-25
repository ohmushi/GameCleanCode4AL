package main.java.clean.code.domain.ports.client;

import io.vavr.control.Either;
import main.java.clean.code.domain.ApplicationError;
import main.java.clean.code.domain.functional.model.Player;

public interface PlayerRegisterApi {
    Either<ApplicationError, Player> register(Player player);
}
