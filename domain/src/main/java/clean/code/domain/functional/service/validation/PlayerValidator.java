package main.java.clean.code.domain.functional.service.validation;

import io.vavr.control.Validation;
import main.java.clean.code.domain.ApplicationError;
import main.java.clean.code.domain.functional.model.Player;

import static io.vavr.API.Invalid;
import static io.vavr.API.Valid;

public interface PlayerValidator {
    static Validation<ApplicationError, Player> validate(Player player) {
        return player.getNickname().isBlank() || player.getTokens() < 0 || player.getDeck() == null
                ? Invalid(new ApplicationError("Invalid Player", player, null))
                : Valid(player);
    }
}
