interface Recipe {
    id: string;
    name: string;
    description: string;
    imageUrl: string | null;
    ingredients: { id: string; name: string; amount: string }[];
    preparationTime: string;
    servings: number;
    difficulty: string;
}

export default Recipe;