package Menu.MenuBackend.datalayer.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "USERS")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "Authentication_Token", nullable = false)
    private String authenticationToken;
}
