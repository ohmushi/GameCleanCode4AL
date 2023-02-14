package clean.code.domain.functional.service;

import clean.code.domain.functional.model.Hero;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Builder
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class RandomHeroPicker {

    Double legendaryProbability;
    Double rareProbability;
    Double commonProbability;


    Hero pick() {
        return null;
    }
}
