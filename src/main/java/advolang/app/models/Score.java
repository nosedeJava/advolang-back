package advolang.app.models;

import org.springframework.data.annotation.Id;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Getter
@Setter
@ToString
@Document(collection = "scores")
public class Score {
    @Id
    private String id;

    private String userId;

    private Double value;

    private String recommendationId;
}
