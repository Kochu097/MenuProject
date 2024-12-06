package Menu.MenuBackend.datalayer.entity;

import lombok.Data;
import Menu.MenuBackend.datalayer.enums.WeightUnit;
import jakarta.persistence.*;

@Entity
@Table(name = "PRODUCT")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Description")
    private String description;

    @Column(name = "ImageURL")
    private String imageUrl;

    @Column(name = "Weight")
    private Long weight;

    @Column(name = "WeightUnit")
    @Enumerated(EnumType.STRING)
    private WeightUnit weightUnit;

    @Column(name = "Calories")
    private Long calories;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID")
    private User user;

    @Column(name = "Shared" , nullable = false)
    private Boolean shared;
}
