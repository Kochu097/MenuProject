package menu.menubackend.datalayer.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "USERS")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "Authentication_Token", nullable = false)
    private String authenticationToken;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Menu> menus;
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private List<Product> products;
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private List<Recipe> recipes;
}
