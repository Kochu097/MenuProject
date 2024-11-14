import Product from "./IProduct";

interface Recipe {
    name: string;
    description: string;
    imageUrl: string | null;
    ingredients: {product: Product, amount: string, unit: string}[];
    preparationTime: string;
    servings: number;
    difficulty: string;
}

export default Recipe;