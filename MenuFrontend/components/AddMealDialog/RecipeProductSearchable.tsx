import { MaterialIcons } from "@expo/vector-icons";
import { useState } from "react";
import { View, TouchableOpacity, TextInput, ScrollView, Text, StyleSheet} from "react-native";
import AddRecipeDialog from "../AddRecipeDialog";

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
  
interface Product {
    id: string;
    name: string;
    description: string;
    imageUrl: string;
    weight: string;
    calories: number;
  }

interface RecipeProductSearchableProp {
    selectedItemType: 'recipe' | 'product',
    setSelectedItemType: (type: 'recipe' | 'product') => void,
    searchQuery: string,
    setSearchQuery: (query: string) => void,
    selectedItem: Recipe | Product | null,
    setSelectedItem: (item: Recipe | Product | null) => void,
    filteredItems: (Recipe | Product)[],
    recipes: Recipe[],
    products: Product[],
    setIsVisible: (visible: boolean) => void,
    
}

const RecipeProductSearchable: React.FC<RecipeProductSearchableProp> = ({
    selectedItemType,
    setSelectedItemType,
    searchQuery,
    setSearchQuery,
    selectedItem,
    setSelectedItem,
    filteredItems,
    recipes,
    products,
    setIsVisible
}) => {

    const [items, setItems] = useState<(Recipe | Product)[]>(filteredItems);

    const handleAddRecipe = (newRecipe: Recipe | Product) => {
        setItems([...items, newRecipe]);
      };

    return (
        <>
        {/* Recipe/Product Toggle */}
        <View style={styles.toggleContainer}>
            <TouchableOpacity
            style={[
                styles.toggleButton,
                selectedItemType === 'recipe' && styles.toggleButtonActive,
            ]}
            onPress={() => setSelectedItemType('recipe')}
            >
            <Text style={[
                styles.toggleButtonText,
                selectedItemType === 'recipe' && styles.toggleButtonTextActive,
            ]}>
                Recipe
            </Text>
            </TouchableOpacity>
            <TouchableOpacity
            style={[
                styles.toggleButton,
                selectedItemType === 'product' && styles.toggleButtonActive,
            ]}
            onPress={() => setSelectedItemType('product')}
            >
            <Text style={[
                styles.toggleButtonText,
                selectedItemType === 'product' && styles.toggleButtonTextActive,
            ]}>
                Product
            </Text>
            </TouchableOpacity>
        </View>

        {/* Search Input */}
        <View style={styles.searchContainer}>
            <MaterialIcons name="search" size={20} color="#8B4513" />
            <TextInput
            style={styles.searchInput}
            placeholder={`Search ${selectedItemType}s...`}
            value={searchQuery}
            onChangeText={setSearchQuery}
            placeholderTextColor="#A67B5B"
            />
            {selectedItemType == 'recipe' ? (
                <AddRecipeDialog
                recipes={recipes}
                products={products}
                onAddRecipe={handleAddRecipe}
                onClose={() => setIsVisible(true)}
                />
            ) : (
                <TouchableOpacity
                style={[styles.addButton, { marginLeft: 8 }]}
                onPress={() => setIsVisible(false)}
                >
                <MaterialIcons name="add" size={16} color="#8B4513" />
                <Text style={styles.addButtonText}>Add Product</Text>
            </TouchableOpacity>
            )}
        </View>

        {/* Items List */}
        <ScrollView style={styles.itemsList}>
            {filteredItems.map((item) => (
            <TouchableOpacity
                key={item.id}
                style={[
                styles.itemCard,
                selectedItem?.id === item.id && styles.itemCardSelected,
                ]}
                onPress={() => setSelectedItem(item)}
            >
                <Text style={styles.itemName}>{item.name}</Text>
                <Text style={styles.itemDescription}>{item.description}</Text>
                {selectedItemType === 'recipe' && (
                <View style={styles.itemMeta}>
                    <Text style={styles.itemMetaText}>
                    {(item as Recipe).preparationTime} • {(item as Recipe).difficulty}
                    </Text>
                </View>
                )}
                {selectedItemType === 'product' && (
                <View style={styles.itemMeta}>
                    <Text style={styles.itemMetaText}>
                    {(item as Product).weight} • {(item as Product).calories} kcal
                    </Text>
                </View>
                )}
            </TouchableOpacity>
            ))}
        </ScrollView>
        </>
    );

}

const styles = StyleSheet.create({
    addButton: {
      flexDirection: 'row',
      alignItems: 'center',
      gap: 4,
      padding: 8,
    },
    addButtonText: {
      fontSize: 12,
      color: '#8B4513',
      fontStyle: 'italic',
    },
    modal: {
      margin: 0,
      justifyContent: 'center',
      alignItems: 'center',
    },
    dialogContainer: {
      width: '90%',
      maxWidth: 500,
      backgroundColor: '#FFF9E5',
      borderRadius: 12,
      overflow: 'hidden',
      elevation: 8,
      shadowColor: '#000',
      shadowOffset: { width: 0, height: 2 },
      shadowOpacity: 0.25,
    },
    header: {
      flexDirection: 'row',
      justifyContent: 'space-between',
      alignItems: 'center',
      padding: 16,
    },
    title: {
      fontSize: 20,
      fontWeight: 'bold',
      color: '#FFF8F0',
    },
    closeButton: {
      padding: 4,
    },
    content: {
      padding: 16,
      gap: 16,
    },
    section: {
      gap: 8,
    },
    label: {
      fontSize: 14,
      fontWeight: '600',
      color: '#6B4423',
    },
    dropdown: {
      flexDirection: 'row',
      justifyContent: 'space-between',
      alignItems: 'center',
      padding: 12,
      backgroundColor: '#FFF',
      borderRadius: 8,
      borderWidth: 1,
      borderColor: '#D4B483',
    },
    dropdownText: {
      fontSize: 16,
      color: '#8B4513',
    },
    dropdownList: {
      position: 'absolute',
      top: '100%',
      left: 0,
      right: 0,
      backgroundColor: '#FFF',
      borderRadius: 8,
      borderWidth: 1,
      borderColor: '#D4B483',
      marginTop: 4,
      zIndex: 1000,
    },
    dropdownItem: {
      padding: 12,
      borderBottomWidth: 1,
      borderBottomColor: '#D4B483',
    },
    dropdownItemText: {
      fontSize: 16,
      color: '#8B4513',
    },
    toggleContainer: {
      flexDirection: 'row',
      backgroundColor: '#D4B483',
      borderRadius: 8,
      padding: 4,
    },
    toggleButton: {
      flex: 1,
      padding: 8,
      alignItems: 'center',
      borderRadius: 6,
    },
    toggleButtonActive: {
      backgroundColor: '#FFF',
    },
    toggleButtonText: {
      fontSize: 14,
      color: '#8B4513',
      opacity: 0.7,
    },
    toggleButtonTextActive: {
      opacity: 1,
      fontWeight: '600',
    },
    searchContainer: {
      flexDirection: 'row',
      alignItems: 'center',
      backgroundColor: '#FFF',
      borderRadius: 8,
      padding: 8,
      borderWidth: 1,
      borderColor: '#D4B483',
    },
    searchInput: {
      flex: 1,
      marginLeft: 8,
      fontSize: 16,
      color: '#8B4513',
    },
    itemsList: {
      maxHeight: 300,
    },
    itemCard: {
      backgroundColor: '#FFF',
      borderRadius: 8,
      padding: 12,
      marginBottom: 8,
      borderWidth: 1,
      borderColor: '#D4B483',
    },
    itemCardSelected: {
      borderColor: '#8B4513',
      borderWidth: 2,
    },
    itemName: {
      fontSize: 16,
      fontWeight: '600',
      color: '#8B4513',
    },
    itemDescription: {
      fontSize: 14,
      color: '#A67B5B',
      marginTop: 4,
    },
    itemMeta: {
      marginTop: 8,
      flexDirection: 'row',
      alignItems: 'center',
    },
    itemMetaText: {
      fontSize: 12,
      color: '#6B4423',
      fontStyle: 'italic',
    },
    addMealButton: {
      backgroundColor: '#8B4513',
      borderRadius: 8,
      padding: 16,
      alignItems: 'center',
    },
    addMealButtonDisabled: {
      backgroundColor: '#D4B483',
    },
    addMealButtonText: {
      fontSize: 16,
      fontWeight: '600',
      color: '#FFF',
    }, mealTypeButtons: {
      flexDirection: 'row',
      flexWrap: 'wrap',
      gap: 8,
      marginTop: 8,
    },
    mealTypeButton: {
      paddingVertical: 8,
      paddingHorizontal: 16,
      borderRadius: 20,
      backgroundColor: '#FFF',
      borderWidth: 1,
      borderColor: '#D4B483',
      minWidth: 100,
      alignItems: 'center',
    },
    mealTypeButtonSelected: {
      backgroundColor: '#8B4513',
      borderColor: '#8B4513',
    },
    mealTypeButtonText: {
      fontSize: 14,
      color: '#8B4513',
      fontWeight: '500',
    },
    mealTypeButtonTextSelected: {
      color: '#FFF',
    },
    datePickerButton: {
      flexDirection: 'row',
      alignItems: 'center',
      padding: 12,
      backgroundColor: '#FFF',
      borderRadius: 8,
      borderWidth: 1,
      borderColor: '#D4B483',
      gap: 8,
    },
    datePickerButtonText: {
      flex: 1,
      fontSize: 16,
      color: '#8B4513',
    },
    calendarContainer: {
      backgroundColor: '#FFF',
      borderRadius: 8,
      padding: 12,
      marginTop: 8,
      borderWidth: 1,
      borderColor: '#D4B483',
    },
    weekDaysHeader: {
      flexDirection: 'row',
      justifyContent: 'space-around',
      marginBottom: 8,
      borderBottomWidth: 1,
      borderBottomColor: '#D4B483',
      paddingBottom: 8,
    },
    weekDayText: {
      fontSize: 12,
      color: '#6B4423',
      fontWeight: '600',
      width: 36,
      textAlign: 'center',
    },
    weekRow: {
      flexDirection: 'row',
      justifyContent: 'space-around',
      marginBottom: 8,
    },
    dateCell: {
      width: 36,
      height: 36,
      justifyContent: 'center',
      alignItems: 'center',
      borderRadius: 18,
    },
    dateCellText: {
      fontSize: 14,
      color: '#8B4513',
    },
    selectedDate: {
      backgroundColor: '#8B4513',
    },
    selectedDateText: {
      color: '#FFF',
      fontWeight: '600',
    },
    todayDate: {
      backgroundColor: '#D4B483',
    },
    todayDateText: {
      fontWeight: '600',
    },
  });

  
export default RecipeProductSearchable;