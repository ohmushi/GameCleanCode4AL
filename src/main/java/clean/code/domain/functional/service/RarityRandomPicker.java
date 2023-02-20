package clean.code.domain.functional.service;

import clean.code.domain.functional.model.PackType;
import lombok.RequiredArgsConstructor;

import java.util.Random;

@RequiredArgsConstructor
public final class RarityRandomPicker {

    private final Random randomPercentageGenerator;

    public String pick(PackType packType) {
        final var configuration = OpenPackConfigurationFactory.forType(packType);

        final var rareThreshold = configuration.commonProbability();
        final var legendaryThreshold = rareThreshold + configuration.rareProbability();

        final var randomPercentage = getRandomPercentageGenerator();

        if(randomPercentage < rareThreshold) return "COMMON";
        if(randomPercentage < legendaryThreshold) return "RARE";
        return "LEGENDARY";
    }

    private Integer getRandomPercentageGenerator() {
        return randomPercentageGenerator.nextInt(100);
    }
}

