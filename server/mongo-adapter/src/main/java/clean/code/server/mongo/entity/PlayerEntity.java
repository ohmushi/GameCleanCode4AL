package clean.code.server.mongo.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;


@Document(collection = "players")
@Builder
@Getter
public class PlayerEntity {
    @Id
    private String id;

    private String nickname;
    private Integer tokens;
    private List<CardEntity> cards;
}
