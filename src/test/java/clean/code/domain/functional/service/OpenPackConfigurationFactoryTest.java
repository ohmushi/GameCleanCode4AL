package clean.code.domain.functional.service;

import clean.code.domain.functional.model.PackType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class OpenPackConfigurationFactoryTest {

    @ParameterizedTest
    @EnumSource(PackType.class)
    void should_contains_good_values(PackType type) {
        var conf = OpenPackConfigurationFactory.forType(type);
        Assertions.assertThat(conf.nbCards()).isPositive();
        Assertions.assertThat(conf.requiredNbTokens()).isNotNegative();
    }

}