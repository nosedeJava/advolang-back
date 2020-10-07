package advolang.app.models;

import org.springframework.data.annotation.Id;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@ToString
public class Score {
    @Id
    private String id;

    private String userId;

    private Double value;

    private String recommendationId;
}
