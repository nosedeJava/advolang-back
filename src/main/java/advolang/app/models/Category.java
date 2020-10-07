package advolang.app.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Category
 */
@Data
@Getter
@Setter
@ToString
@Document(collection = "categories")
public class Category {

    @Id
    private String id;

    private String value;

    private int popularity;

    public Category(String id,String value,int popularity) {
        this.id = id;
        this.value = value;
        this.popularity = popularity;
    }
    
}
