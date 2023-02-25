package clean.code.bootstrap.config.domain;

import main.java.clean.code.domain.functional.service.*;
import main.java.clean.code.domain.functional.service.validation.HeroValidator;
import main.java.clean.code.domain.ports.client.*;
import main.java.clean.code.domain.ports.server.HeroPersistenceSpi;
import main.java.clean.code.domain.ports.server.PlayerPersistenceSpi;
import clean.code.server.adapter.PlayerMongoDatabaseAdapter;
import clean.code.server.repository.PlayerRepository;
import main.java.clean.code.domain.ports.client.HeroCreatorApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class DomainConfiguration {

    @Bean
    public HeroCreatorApi heroCreatorService(HeroPersistenceSpi spi, HeroValidator validator) {
        return new HeroCreatorService(spi, validator);
    }

    @Bean
    public HeroValidator heroValidator() {
        return new HeroValidator();
    }

    @Bean
    public HeroFinderApi heroFinderService(HeroPersistenceSpi spi) {
        return new HeroFinderService(spi);
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
    public PackOpenerApi packOpenerApi(PlayerPersistenceSpi playerPersistenceSpi, HeroRandomPicker heroRandomPicker) {
        return new PackOpenerService(playerPersistenceSpi, heroRandomPicker);
    }

}
