package clean.code.domain.functional.service;

public interface HeroCreationConfigurationFactory {
    static HeroCreationConfiguration forSpeciality(String speciality, String rarity) {
        double rarityBonus = forRarity(rarity);
        return switch (speciality) {
            case "TANK" -> new HeroCreationConfiguration(1000 + (int) (1000 * rarityBonus), 100 + (int) (100 * rarityBonus), 20 + (int) (20 * rarityBonus));
            case "ASSASSIN" -> new HeroCreationConfiguration(800 + (int) (800 * rarityBonus), 200 + (int) (200 * rarityBonus), 5 + (int) (5 * rarityBonus));
            case "WIZARD" -> new HeroCreationConfiguration(700 + (int) (700 * rarityBonus), 150 + (int) (150 * rarityBonus), 10 + (int) (10 * rarityBonus));
            default -> throw new IllegalArgumentException("Unknown speciality");
        };
    }

    private static double forRarity(String rarity) {
        return switch (rarity) {
            case "LEGENDARY" -> 0.2;
            case "RARE" -> 0.1;
            case "COMMON" -> 0;
            default -> throw new IllegalArgumentException("Unknown rarity");
        };
    }
}
