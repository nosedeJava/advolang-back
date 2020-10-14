package advolang.app.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@ToString
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
