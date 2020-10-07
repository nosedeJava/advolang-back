package advolang.app.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Recommendation
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    public Recommendation(User creator, String title, String description, String language,
    		String level, Date creationDate, String thumbnail) {
    	
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