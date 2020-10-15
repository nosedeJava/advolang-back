package advolang.app.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
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

    private String thumbnail;

    private List<Recommendation> savedRecommendations = new ArrayList<>();

    private List<String> subscriptions = new ArrayList<String>();
    @DBRef
    private Set<Role> roles = new HashSet<>();

    public User(String fullName, String email, String username, String password) {
        this.fullName = fullName;
        this.email = email;
        this.username = username;
        this.password = password;
    }
}
