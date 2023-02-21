package clean.code.server.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "players")
@Builder
@Getter
public class PlayerEntity {
    @Id
    private String id;

    private String nickname;
    private Integer tokens;
    //TODO Deck
}
