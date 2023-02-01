package clean.code.domain.ports.server;

import clean.code.domain.functional.model.Hero;

public interface HeroPersistenceSpi {
    Hero save(Hero hero);
}
