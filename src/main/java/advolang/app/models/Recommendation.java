package advolang.app.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Recommendation
 */
@Data
@Getter
@Setter
@ToString
@Document(collection = "recommendations")
public class Recommendation {
	
    @Id
    private String id;

    private User creator;
    
    private String title;
    
    private String description;

    private String language;
    
    private String level;

    private Date creationDate;

    private String thumbnail;

    private String resource;

    private String resourceType;
    
    private List<Score> scores;
    
    private List<Category> categories;

    private Boolean reported;

    public Recommendation(User creator, String title, String description, String language,
    		String level, Date creationDate, String thumbnail, Boolean reported) {
    	
    	this.creator = creator;
    	this.title = title;
    	this.description = description;
    	this.language = language;
    	this.level = level;
    	this.creationDate = creationDate;
    	this.thumbnail = thumbnail;
    	this.scores = new ArrayList<Score>();
    	this.categories = new ArrayList<Category>();
    }
    
    
    
}