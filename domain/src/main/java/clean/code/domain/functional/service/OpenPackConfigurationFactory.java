package main.java.clean.code.domain.functional.service;

import main.java.clean.code.domain.functional.model.PackType;

interface OpenPackConfigurationFactory {

    static OpenPackConfiguration forType(PackType type) {
        return switch (type) {
            case SILVER -> new OpenPackConfiguration(3, 1, 5, 20, 75);
            case DIAMOND -> new OpenPackConfiguration(5, 2, 15, 35, 50);
        };
    }
}
