package clean.code.bootstrap.config.domain;

import clean.code.domain.functional.service.*;
import clean.code.domain.functional.service.validation.HeroValidator;
import clean.code.domain.ports.client.*;
import clean.code.domain.ports.server.CardPersistenceSpi;
import clean.code.domain.ports.server.HeroPersistenceSpi;
import clean.code.domain.ports.server.PlayerPersistenceSpi;
import clean.code.server.adapter.PlayerMongoDatabaseAdapter;
import clean.code.server.repository.PlayerRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class DomainConfiguration {

    @Bean
    public HeroCreatorApi heroCreatorService(HeroPersistenceSpi spi) {
        return new HeroCreatorService(spi);
    }

    @Bean
    public HeroFinderApi heroFinderService(HeroPersistenceSpi spi) {
        return new HeroFinderService(spi);
    }

    @Bean CardFighterApi cardFighterService(CardPersistenceSpi spi) {
        return new CardFighterService(spi);
    }

    @Bean
    public PlayerPersistenceSpi playerPersistenceSpi(PlayerRepository repository) {
        return new PlayerMongoDatabaseAdapter(repository);
    }

    @Bean
    public PlayerRegisterApi playerRegisterApi(PlayerPersistenceSpi playerPersistenceSpi) {
        return new PlayerRegisterService(playerPersistenceSpi);
    }

    @Bean
    public PlayerFinderApi playerFinderApi(PlayerPersistenceSpi playerPersistenceSpi) {
        return new PlayerFinderService(playerPersistenceSpi);
    }

    @Bean
    public Random random() {
        return new Random();
    }

    @Bean
    public RarityRandomPicker rarityRandomPicker(Random random) {
        return new RarityRandomPicker(random);
    }

    @Bean
    public HeroRandomPicker heroRandomPicker(HeroPersistenceSpi spi, Random randomIndexGenerator, RarityRandomPicker rarityRandomPicker) {
        return new HeroRandomPicker(spi, randomIndexGenerator, rarityRandomPicker);
    }

    @Bean
    public PackOpenerApi packOpenerApi(PlayerPersistenceSpi playerPersistenceSpi, CardPersistenceSpi cardPersistenceSpi, HeroRandomPicker heroRandomPicker) {
        return new PackOpenerService(playerPersistenceSpi, cardPersistenceSpi, heroRandomPicker);
    }

}
