package Menu.MenuBackend.datalayer.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "USER")
@Data
@NamedQuery(name = User.FIND_BY_FIREBASE_USER_ID,
query = " SELECT u FROM User u WHERE u.firebaseUserId = :token")
public class User {

    public static final String FIND_BY_FIREBASE_USER_ID = "User.FIND_BY_FIREBASE_USER_ID";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "Firebase_User_ID", nullable = false)
    private String firebaseUserId;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Menu> menus;
}
