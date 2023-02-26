package clean.code.domain.functional.model;


import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.With;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@With
@Value
@Builder
@EqualsAndHashCode
public class Card {
    @Builder.Default
    UUID id = UUID.randomUUID();

    String name;

    int hp;

    @Builder.Default
    int xp = 0;

    int power;

    int armor;

    String speciality;

    String rarity;

    @Builder.Default
    int level = 1;

    @Builder.Default
    List<FightResult> history = Collections.emptyList();

    public static Card fromHero(Hero hero) {
        return Card.builder()
            .name(hero.getName())
            .hp(hero.getHp())
            .power(hero.getPower())
            .armor(hero.getArmor())
            .speciality(hero.getSpeciality())
            .rarity(hero.getRarity())
            .build();
    }

    public FightResult fight(Card opponent) {
        Card tmpAttacker = this;
        Card tmpDefender = opponent;

        while (tmpAttacker.getHp() > 0 && tmpDefender.getHp() > 0) {

            tmpDefender = tmpDefender.withHp(tmpDefender.getHp() - calculateDamage(tmpAttacker, tmpDefender));

            if (tmpDefender.getHp() > 0) {
                tmpAttacker = tmpAttacker.withHp(tmpAttacker.getHp() - calculateDamage(tmpDefender, tmpAttacker));
            } else {
                return FightResult.builder()
                    .opponent(opponent)
                    .won(true)
                    .build();
            }

            if (tmpAttacker.getHp() <= 0) {
                return FightResult.builder()
                    .opponent(opponent)
                    .won(false)
                    .build();
            }
        }

        throw new IllegalStateException("Should not happen");
    }

    private int calculateDamage(Card attacker, Card defender) {
        return Math.max(0, attacker.getPower() + getPowerBonus(attacker.speciality, defender.speciality) - defender.getArmor());
    }

    private int getPowerBonus(String attackerSpeciality, String defenderSpeciality) {
        if (attackerSpeciality.equals("TANK") && defenderSpeciality.equals("WIZARD")) {
            return 20;
        } else if (attackerSpeciality.equals("ASSASSIN") && defenderSpeciality.equals("TANK")) {
            return 30;
        } else if (attackerSpeciality.equals("WIZARD") && defenderSpeciality.equals("ASSASSIN")) {
            return 25;
        } else {
            return 0;
        }
    }

}
