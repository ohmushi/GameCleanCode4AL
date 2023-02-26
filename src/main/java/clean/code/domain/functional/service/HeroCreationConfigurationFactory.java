package clean.code.domain.functional.service;

public interface HeroCreationConfigurationFactory {
    static HeroCreationConfiguration forSpeciality(String speciality) {
        return switch (speciality) {
            case "TANK" -> new HeroCreationConfiguration(1000, 100, 20);
            case "ASSASSIN" -> new HeroCreationConfiguration(800, 200, 5);
            case "WIZARD" -> new HeroCreationConfiguration(700, 150, 10);
            default -> throw new IllegalArgumentException("Unknown speciality");
        };
    }
}
