package Menu.MenuBackend.datalayer.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "MENU_ITEM")
public class MenuItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Menu_ID")
    private MenuEntity menu;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Meal_ID")
    private MealEntity meal;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Product_ID")
    private ProductEntity product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MenuEntity getMenu() {
        return menu;
    }

    public void setMenu(MenuEntity menu) {
        this.menu = menu;
    }

    public MealEntity getMeal() {
        return meal;
    }

    public void setMeal(MealEntity meal) {
        this.meal = meal;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }
}
