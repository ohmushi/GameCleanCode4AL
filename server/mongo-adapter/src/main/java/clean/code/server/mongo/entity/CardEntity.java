package clean.code.server.mongo.entity;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CardEntity {
    private String id;

    private String name;

    private int hp;

    private int xp;

    private int power;

    private int armor;

    private String speciality;

    private String rarity;

    private int level;
}
