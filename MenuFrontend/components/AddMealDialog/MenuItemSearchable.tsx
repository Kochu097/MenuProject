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
    setServings: (servings: number) => void,
    
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
    setIsVisible,
    setServings,
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
        <View style={styles.searchContainer} testID="menu-item-searchable">
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
                <View style={styles.itemDetails}>
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
                </View>
                <View style={styles.servingsContainer}>
                  <Text style={styles.servingsLabel}>Servings:</Text>
                  <TextInput
                    style={styles.servingsInput}
                    keyboardType="numeric"
                    defaultValue="1"
                    onChangeText={(text) => {
                      setServings(parseInt(text) || 1 );
                    }}
                  />
                </View>
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
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  itemCardSelected: {
    borderColor: '#8B4513',
    borderWidth: 2,
  },
  itemDetails: {
    flex: 1,
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
  servingsContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    marginLeft: 'auto',
  },
  servingsLabel: {
    fontSize: 14,
    color: '#8B4513',
    marginRight: 8,
  },
  servingsInput: {
    width: 40,
    height: 24,
    padding: 0,
    textAlign: 'center',
    fontSize: 14,
    color: '#8B4513',
    borderRadius: 4,
    borderWidth: 1,
    borderColor: '#D4B483',
  },
});

  
export default MenuItemSearchable;