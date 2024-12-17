import ProductWeightUnit from "../Enums/ProductWeghtUnit";
import difficulty from "../Enums/DifficultyEnum";
import IngridientAmountUnit from "../Enums/IngredientAmountUnit";
import MealTypesEnum from "../Enums/MealTypesEnum";

interface Product {
    name: string;
    description: string;
    imageUrl: string | undefined;
    weight: number;
    weightUnit: ProductWeightUnit;
    calories: number;
    shared: boolean;
}

  export type { Product };


interface Recipe {
  name: string;
  description: string;
  imageUrl: string | undefined;
  ingredients: {product: Product, amount: string, unit: string}[];
  preparationTime: string;
  servings: number;
  difficulty: difficulty;
  shared: boolean;
}

  export type { Recipe };


interface Ingredient {
  recipe: Recipe;
  product: Product;
  amount: string;
  unit: IngridientAmountUnit;
}

  export type { Ingredient };


interface Menu {
  day: Date;
  menuItems: MenuItem[];
}

  export type { Menu };

interface MenuItem {
  menuItemType: MealTypesEnum;
  recipe: Recipe| undefined;
  product: Product | undefined;
  servings: number;
}
  export type { MenuItem };