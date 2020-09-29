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

    private List<Recommendation> saved;

    private String description;

    private String userImage;

    private List<String> subscriptions;

    @DBRef
    private Set<Role> roles = new HashSet<>();

    public User(String fullName, String email, String username, String password) {
        this.fullName = fullName;
        this.email = email;
        this.username = username;
        this.password = password;
    }
}
