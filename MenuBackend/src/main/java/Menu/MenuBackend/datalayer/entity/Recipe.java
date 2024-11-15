package Menu.MenuBackend.datalayer.entity;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_ID", nullable = false)
    private User user;

    @Column(name = "Description", columnDefinition = "BLOB")
    private String description;

    @Column(name = "ImageURL")
    private String imageUrl;

    @Column(name = "PreperationTime")
    private LocalTime preparationTime;

    @Column(name = "Servings")
    private BigDecimal servings;

    @Column(name = "Difficulty")
    private String difficulty;

    @ManyToMany
    @JoinTable(name = "RECIPE_PRODUCT",
            joinColumns = @JoinColumn(name = "Recipe_ID"),
            inverseJoinColumns = @JoinColumn(name = "Product_ID"))
    private List<Product> products = new ArrayList<>();
}
