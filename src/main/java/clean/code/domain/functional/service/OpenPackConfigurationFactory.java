package clean.code.domain.functional.service;

import clean.code.domain.functional.model.PackType;

interface OpenPackConfigurationFactory {

    static OpenPackConfiguration forType(PackType type) {
        return switch (type) {
            case SILVER -> new OpenPackConfiguration(3, 1);
            case DIAMOND -> new OpenPackConfiguration(5, 2);
        };
    }
}
