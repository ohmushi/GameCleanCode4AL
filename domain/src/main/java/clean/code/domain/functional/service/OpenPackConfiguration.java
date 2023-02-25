package main.java.clean.code.domain.functional.service;

public record OpenPackConfiguration(
          Integer nbCards
        , Integer requiredNbTokens
        , Integer legendaryProbability
        , Integer rareProbability
        , Integer commonProbability

) {
}
