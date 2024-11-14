package Menu.MenuBackend.datalayer.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "INGREDIENT")
public class IngredientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Menu_ID")
    private MenuEntity menu;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Recipe_ID")
    private RecipeEntity recipe;

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

    public RecipeEntity getRecipe() {
        return recipe;
    }

    public void setRecipe(RecipeEntity recipe) {
        this.recipe = recipe;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }
}
