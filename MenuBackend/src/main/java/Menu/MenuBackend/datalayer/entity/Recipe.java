package Menu.MenuBackend.datalayer.entity;

import Menu.MenuBackend.datalayer.enums.Difficulty;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "RECIPE")
@Data
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Description", columnDefinition = "BLOB")
    private String description;

    @Column(name = "ImageURL")
    private String imageUrl;

    @Column(name = "PreperationTime")
    private LocalTime preparationTime;

    @Column(name = "Servings")
    private BigDecimal servings;

    @Column(name = "Difficulty")
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ingredient> ingredients = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID")
    private User user;

    @Column(name = "Shared" , nullable = false)
    private Boolean shared;
}
