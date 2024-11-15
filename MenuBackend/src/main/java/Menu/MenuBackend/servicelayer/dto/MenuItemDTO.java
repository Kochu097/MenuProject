package Menu.MenuBackend.servicelayer.dto;

public class MenuItemDTO {

    private Long id;
    private MenuDTO menu;
    private MealDTO meal;
    private ProductDTO product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MenuDTO getMenu() {
        return menu;
    }

    public void setMenu(MenuDTO menu) {
        this.menu = menu;
    }

    public MealDTO getMeal() {
        return meal;
    }

    public void setMeal(MealDTO meal) {
        this.meal = meal;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }
}
