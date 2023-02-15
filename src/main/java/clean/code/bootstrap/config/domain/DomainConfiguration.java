package clean.code.bootstrap.config.domain;

import clean.code.domain.functional.service.HeroCreatorService;
import clean.code.domain.functional.service.HeroFinderService;
import clean.code.domain.functional.service.validation.HeroValidator;
import clean.code.domain.ports.client.HeroCreatorApi;
import clean.code.domain.ports.client.HeroFinderApi;
import clean.code.domain.ports.server.HeroPersistenceSpi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}
