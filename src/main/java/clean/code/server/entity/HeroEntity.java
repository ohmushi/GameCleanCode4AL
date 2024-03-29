package clean.code.server.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Getter
@Document(collection = "heroes")
public class HeroEntity {
    @Id
    private String id;

    private String name;

    private int hp;

    private int power;

    private int armor;

    private String speciality;

    private String rarity;
}
