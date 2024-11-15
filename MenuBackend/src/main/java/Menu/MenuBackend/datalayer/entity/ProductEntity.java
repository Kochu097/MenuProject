package menu.menubackend.datalayer.entity;

import menu.menubackend.datalayer.enums.WeightUnit;
import jakarta.persistence.*;

@Entity
@Table(name = "PRODUCT")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "UserID", nullable = false)
    private int userID;

    @Column(name = "Description")
    private String description;

    @Column(name = "ImageURL")
    private String imageURL;

    @Column(name = "Weight")
    private Double weight;

    @Column(name = "WeightUnit")
    private WeightUnit weightUnit;

    @Column(name = "Calories", nullable = false)
    private Double calories;
}
