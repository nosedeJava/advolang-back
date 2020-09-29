package advolang.app.models;

import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

/**
 * Recommendation
 */
public class Recommendation {
    @Id
    private String id;

    private String language;

    private User creator;

    private List<Category> categories;

    private String level;

    private String description;

    private String title;

    private Date creationDate;

    private String thumbnail;

    private String resource;

    private String resourceType;

    private List<Score> scores;

    private Double rating;
}