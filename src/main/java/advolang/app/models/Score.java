package advolang.app.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "scores")
public class Score {
	
    @Id
    private String id;

    private String userId;

    private String recommendationId;

    private Double value;
    
    public Score(String userId, String recommendationId, Double value) {
        this.userId = userId;
        this.recommendationId = recommendationId;
        this.value = value;
    }
    
}
