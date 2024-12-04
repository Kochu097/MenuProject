package Menu.MenuBackend.datalayer.entity;

import Menu.MenuBackend.datalayer.enums.AmountUnit;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "INGREDIENT")
@Data
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Recipe_ID", referencedColumnName = "ID")
    private Recipe recipe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Product_ID", referencedColumnName = "ID")
    private Product product;

    @Column(name = "Amount")
    private Long amount;

    @Column(name = "Amount_Unit")
    private AmountUnit amountUnit;
}
