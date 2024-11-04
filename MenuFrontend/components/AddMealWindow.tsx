import React, { useState } from 'react';
import { StyleSheet } from 'react-native';
import {
    Dialog,
    DialogContent,
    DialogDescription,
    DialogFooter,
    DialogHeader,
    DialogTitle,
    DialogTrigger,
  } from "@/components/ui/dialog"
import { Button } from '@/components/ui/button';
import { Calendar } from '@/components/ui/calendar';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { RadioGroup, RadioGroupItem } from '@/components/ui/radio-group';
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs';
import { Plus, Image } from 'lucide-react';

interface RecipeProp {
    type: string, 
    name: string
}

const AddMealDialog = () => {
    const [open, setOpen] = useState(false);
    const [selectedDate, setSelectedDate] = useState(new Date());
    const [mealType, setMealType] = useState('breakfast');
    const [showRecipeDialog, setShowRecipeDialog] = useState(false);
    const [showProductDialog, setShowProductDialog] = useState(false);

    const handleSetSelectDate = (date: Date | undefined) => {
        if(date != undefined) {
            setSelectedDate(date);
        }
    }
  
    const handleAddMeal = async (recipe: RecipeProp) => {
      console.log('Adding meal:', {
        date: selectedDate,
        type: mealType,
        recipe
      });
    };
  
    return (
      <>
        <Dialog open={open} onOpenChange={setOpen}>
          <DialogTrigger asChild>
            <Button variant="outline" className="w-full flex items-center gap-2">
              <Plus size={16} />
              Add Meal
            </Button>
          </DialogTrigger>
          <DialogContent className="sm:max-w-3xl bg-white">
            <DialogDescription>
                aaaaaaaaa!!!!!
            </DialogDescription>
            <DialogHeader>
              <DialogTitle>Add a Meal to Your Plan</DialogTitle>
            </DialogHeader>
            
            <div className="grid grid-cols-1 sm:grid-cols-2 gap-6">
              <div className="space-y-4">
                <Label>Select Date</Label>
                <Calendar
                  mode="single"
                  selected={selectedDate}
                  onSelect={handleSetSelectDate}
                  className="rounded-md border"
                />
              </div>
              
              <div className="space-y-4">
                <Label>Meal Type</Label>
                <RadioGroup value={mealType} onValueChange={setMealType}>
                  <div className="flex items-center space-x-2">
                    <RadioGroupItem value="breakfast" id="breakfast" />
                    <Label htmlFor="breakfast">Breakfast</Label>
                  </div>
                  <div className="flex items-center space-x-2">
                    <RadioGroupItem value="dinner" id="dinner" />
                    <Label htmlFor="dinner">Dinner</Label>
                  </div>
                  <div className="flex items-center space-x-2">
                    <RadioGroupItem value="supper" id="supper" />
                    <Label htmlFor="supper">Supper</Label>
                  </div>
                  <div className="flex items-center space-x-2">
                    <RadioGroupItem value="snacks" id="snacks" />
                    <Label htmlFor="snacks">Snacks</Label>
                  </div>
                </RadioGroup>
  
                <Tabs defaultValue="recipe" className="w-full">
                  <TabsList className="grid w-full grid-cols-2">
                    <TabsTrigger value="recipe">Recipe</TabsTrigger>
                    <TabsTrigger value="product">Product</TabsTrigger>
                  </TabsList>
                  
                  <TabsContent value="recipe" className="space-y-4">
                    <Input placeholder="Search recipes..." />
                    <Button 
                      variant="outline" 
                      className="w-full"
                      onClick={() => setShowRecipeDialog(true)}
                    >
                      Create New Recipe
                    </Button>
                  </TabsContent>
                  
                  <TabsContent value="product" className="space-y-4">
                    <Input placeholder="Search products..." />
                    <Button 
                      variant="outline" 
                      className="w-full"
                      onClick={() => setShowProductDialog(true)}
                    >
                      Add New Product
                    </Button>
                  </TabsContent>
                </Tabs>
              </div>
            </div>
  
            <DialogFooter>
              <Button onClick={() => setOpen(false)}>Add to Menu</Button>
            </DialogFooter>
          </DialogContent>
        </Dialog>
  
        <RecipeCreationDialog 
          open={showRecipeDialog} 
          onOpenChange={setShowRecipeDialog}
          onSave={handleAddMeal}
        />
        
        <ProductCreationDialog 
          open={showProductDialog} 
          onOpenChange={setShowProductDialog}
          onSave={handleAddMeal}
        />
      </>
    );
  };

interface CreationDialogProp {
     open: boolean | undefined,
     onOpenChange: (changed: boolean) => void,
     onSave: (recipe: RecipeProp) => void
}

const RecipeCreationDialog: React.FC<CreationDialogProp> = ({ open, onOpenChange, onSave }) => {
  const [ingredients, setIngredients] = useState([{ name: '', amount: '', unit: '' }]);
  const [recipeName, setRecipeName] = useState('');
  const [instructions, setInstructions] = useState('');

  const handleAddIngredient = () => {
    setIngredients([...ingredients, { name: '', amount: '', unit: '' }]);
  };

  const handleSave = async () => {
    // Placeholder API call
    console.log('Saving recipe:', {
      name: recipeName,
      ingredients,
      instructions
    });
    // await createRecipe({ name: recipeName, ingredients, instructions });
    onSave({ type: 'recipe', name: recipeName });
    onOpenChange(false);
  };

  return (
    <Dialog open={open} onOpenChange={onOpenChange}>
      <DialogContent className="max-w-3xl">
        <DialogHeader>
          <DialogTitle>Create New Recipe</DialogTitle>
        </DialogHeader>

        <div className="space-y-4">
          <div>
            <Label>Recipe Name</Label>
            <Input 
              value={recipeName}
              onChange={(e) => setRecipeName(e.target.value)}
              placeholder="Enter recipe name"
            />
          </div>

          <div>
            <Label>Ingredients</Label>
            {ingredients.map((ing, idx) => (
              <div key={idx} className="grid grid-cols-3 gap-2 mt-2">
                <Input
                  placeholder="Ingredient"
                  value={ing.name}
                  onChange={(e) => {
                    const newIng = [...ingredients];
                    newIng[idx].name = e.target.value;
                    setIngredients(newIng);
                  }}
                />
                <Input
                  placeholder="Amount"
                  value={ing.amount}
                  onChange={(e) => {
                    const newIng = [...ingredients];
                    newIng[idx].amount = e.target.value;
                    setIngredients(newIng);
                  }}
                />
                <Input
                  placeholder="Unit"
                  value={ing.unit}
                  onChange={(e) => {
                    const newIng = [...ingredients];
                    newIng[idx].unit = e.target.value;
                    setIngredients(newIng);
                  }}
                />
              </div>
            ))}
            <Button 
              variant="outline" 
              className="mt-2"
              onClick={handleAddIngredient}
            >
              <Plus size={16} className="mr-2" />
              Add Ingredient
            </Button>
          </div>

          <div>
            <Label>Instructions</Label>
            <textarea
              className="w-full min-h-32 p-2 border rounded-md"
              value={instructions}
              onChange={(e) => setInstructions(e.target.value)}
              placeholder="Enter cooking instructions..."
            />
          </div>

          <div>
            <Label>Recipe Image</Label>
            <div className="border-2 border-dashed rounded-md p-4 text-center">
              <Image className="mx-auto h-12 w-12 text-gray-400" />
              <div className="mt-2">
                <Button variant="outline">Upload Image</Button>
              </div>
            </div>
          </div>
        </div>

        <DialogFooter>
          <Button onClick={handleSave}>Save Recipe</Button>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  );
};

const ProductCreationDialog: React.FC<CreationDialogProp> = ({ open, onOpenChange, onSave }) => {
  const [productName, setProductName] = useState('');

  const handleSave = async () => {
    // Placeholder API call
    console.log('Saving product:', { name: productName });
    // await createProduct({ name: productName });
    onSave({ type: 'product', name: productName });
    onOpenChange(false);
  };

  return (
    <Dialog open={open} onOpenChange={onOpenChange}>
      <DialogContent>
        <DialogHeader>
          <DialogTitle>Add New Product</DialogTitle>
        </DialogHeader>

        <div className="space-y-4">
          <div>
            <Label>Product Name</Label>
            <Input 
              value={productName}
              onChange={(e) => setProductName(e.target.value)}
              placeholder="Enter product name"
            />
          </div>
        </div>

        <DialogFooter>
          <Button onClick={handleSave}>Add Product</Button>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  );
};

export default AddMealDialog;