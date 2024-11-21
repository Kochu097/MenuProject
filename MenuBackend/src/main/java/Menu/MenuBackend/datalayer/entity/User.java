package Menu.MenuBackend.datalayer.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "USERS")
@Data
@NamedQuery(name = User.FIND_BY_AUTH_TOKEN,
query = " SELECT u FROM User u WHERE u.authenticationToken = :token")
public class User {

    public static final String FIND_BY_AUTH_TOKEN = "User.FIND_BY_AUTH_TOKEN";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "Authentication_Token", nullable = false)
    private String authenticationToken;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Menu> menus;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Recipe> recipes;
}
