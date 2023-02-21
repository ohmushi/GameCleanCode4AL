package clean.code.server.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;

@Builder
@Getter
public class CardEntity {
    @Id
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