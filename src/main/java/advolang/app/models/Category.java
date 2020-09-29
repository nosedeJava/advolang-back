package advolang.app.models;

import org.springframework.data.annotation.Id;

/**
 * Category
 */
public class Category {

    @Id
    private String id;

    private String value;

    private int popularity;
}
