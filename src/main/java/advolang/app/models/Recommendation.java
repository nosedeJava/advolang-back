package advolang.app.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Recommendation
 */
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "recommendations")
public class Recommendation {
	
    @Id
    private String id="";

    private String creator="";
    
    private String title="";
    
    private String description="";

    private String language="";
    
    private String level="";

    private Date creationDate=new Date();

    private String thumbnail="";

    private String resource="";

    private String resourceType="";
    
    private List<Category> categories = new ArrayList<>();

    private Boolean reported=false;
    
    private Boolean promo;

    public Recommendation(String creator, String title, String description, String language,
    		String level, Date creationDate, String thumbnail, Boolean reported) {

    	this.creator = creator;
    	this.title = title;
    	this.description = description;
    	this.language = language;
    	this.level = level;
    	this.creationDate = creationDate;
    	this.thumbnail = thumbnail;
    	this.categories = new ArrayList<Category>();
    	this.promo = false;
    }
}