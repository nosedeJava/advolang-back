package advolang.app.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Category
 */
@Data
@Document(collection = "categories")
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    private String id;

    private String value;

    private int popularity;
    
}
