package advolang.app.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {
	
    @Id
    private String id;

    private String fullName;

    private String username;

    private String email;

    private String password;
    
    private String profileImage;

    private List<Recommendation> savedRecommendations;

    @DBRef
    private Set<Role> roles = new HashSet<>();

    public User(String fullName, String email, String username, String password, String profileImage) {
        this.fullName = fullName;
        this.email = email;
        this.username = username;
        this.profileImage = profileImage;
    }

    public List<Recommendation> getSavedRecommendations() {
        return this.savedRecommendations;
    }

    public void setSavedRecommendations(List<Recommendation> savedRecommendations) { this.savedRecommendations = savedRecommendations; }
}
