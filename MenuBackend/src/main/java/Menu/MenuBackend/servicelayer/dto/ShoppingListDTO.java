package Menu.MenuBackend.servicelayer.dto;

import Menu.MenuBackend.datalayer.enums.WeightUnit;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Data
public class ShoppingListDTO {
    private LocalDate startDate;
    private LocalDate endDate;
    private List<ShoppingListEntry> products;

    public void addToShoppingList(MenuItemDTO menuItem) {
        ProductDTO product = menuItem.getProduct();
        RecipeDTO recipe = menuItem.getRecipe();
        if(product != null) {
            addProduct(product, menuItem.getServings());
        } else if (recipe != null) {
            addRecipe(recipe, menuItem.getServings());
        }
    }

    private void addProduct(ProductDTO product, Integer servings) {
        Optional<ShoppingListEntry> optionalEntry = products.stream().filter(p -> p.name.equals(product.getName())).findFirst();
        if(optionalEntry.isPresent()) {
            ShoppingListEntry entry = optionalEntry.get();
            entry.setWeight(entry.getWeight() + (product.getWeight() * servings));
            //TODO: handle convertion between weight units
        } else {
            ShoppingListEntry entry = new ShoppingListEntry();
            entry.setName(product.getName());
            entry.setWeight(product.getWeight());
            entry.setWeightUnit(product.getWeightUnit());
        }

    }

    private void addRecipe(RecipeDTO recipe, Integer servings) {
        recipe.getIngredients().forEach(ingredientDTO -> {
            if(ingredientDTO.getRecipe() != null) {
                addRecipe(ingredientDTO.getRecipe(), servings);
            } else if (ingredientDTO.getProduct() != null) {
                addProduct(ingredientDTO.getProduct(), servings);
            }
        });
    }

    @Data
    private static class ShoppingListEntry {
        private String name;
        private Double weight;
        private WeightUnit weightUnit;
    }
}

