package clean.code.domain;


// TODO Refactor
final class Hero {

    private final String name;
    private final int hp;
    private final int xp;
    private final int power;
    private final Armor armor;
    private final Speciality speciality;
    private final Rarity rarity;
    private final int level;

    private Hero(
            String name,
            int hp,
            int xp,
            int power,
            Armor armor,
            Speciality speciality,
            Rarity rarity,
            int level) {
        this.name = name;
        this.hp = hp;
        this.xp = xp;
        this.power = power;
        this.armor = armor;
        this.speciality = speciality;
        this.rarity = rarity;
        this.level = level;
    }


    //<editor-fold desc="getters">
    public String name() {
        return name;
    }

    public int hp() {
        return hp;
    }

    public int xp() {
        return xp;
    }

    public int power() {
        return power;
    }

    public Armor armor() {
        return armor;
    }

    public Speciality speciality() {
        return speciality;
    }

    public Rarity rarity() {
        return rarity;
    }

    public int level() {
        return level;
    }
    //</editor-fold>

}
