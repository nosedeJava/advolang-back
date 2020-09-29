package advolang.app.models;

import org.springframework.data.annotation.Id;

public class Score {
    @Id
    private String id;

    private String userId;

    private Double value;

    private String recommendationId;
}
