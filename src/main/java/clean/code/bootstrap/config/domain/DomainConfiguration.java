package clean.code.bootstrap.config.domain;

import clean.code.domain.functional.service.HeroCreatorService;
import clean.code.domain.functional.service.HeroFinderService;
import clean.code.domain.functional.service.PlayerFinderService;
import clean.code.domain.functional.service.PlayerRegisterService;
import clean.code.domain.functional.service.validation.HeroValidator;
import clean.code.domain.functional.service.validation.PlayerValidator;
import clean.code.domain.ports.client.HeroCreatorApi;
import clean.code.domain.ports.client.HeroFinderApi;
import clean.code.domain.ports.client.PlayerFinderApi;
import clean.code.domain.ports.client.PlayerRegisterApi;
import clean.code.domain.ports.server.HeroPersistenceSpi;
import clean.code.domain.ports.server.PlayerPersistenceSpi;
import clean.code.server.adapter.InMemoryPlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfiguration {

    @Autowired
    private PlayerPersistenceSpi playerPersistenceSpi;

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
    public PlayerRegisterApi playerRegisterApi() {
        return new PlayerRegisterService(playerPersistenceSpi);
    }

    @Bean
    public PlayerFinderApi playerFinderApi() {
        return new PlayerFinderService(playerPersistenceSpi);
    }


}
