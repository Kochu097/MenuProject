import { MaterialIcons } from "@expo/vector-icons";
import { useState } from "react";
import { View, TouchableOpacity, TextInput, ScrollView, Text, StyleSheet} from "react-native";
import AddRecipeDialog from "../AddRecipe/AddRecipeDialog";
import AddProductDialog from "../AddProduct/AddProductDialog";
import { Product, Recipe } from "../Interfaces/ICommon";

interface MenuItemSearchableProp {
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

const MenuItemSearchable: React.FC<MenuItemSearchableProp> = ({
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
              <AddProductDialog />
            )}
        </View>

        {/* Items List */}
        <ScrollView style={styles.itemsList}>
            {filteredItems.map((item, index) => (
            <TouchableOpacity
              key={index}
              style={[
              styles.itemCard,
              selectedItem === item && styles.itemCardSelected,
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
  });

  
export default MenuItemSearchable;