import React, { useEffect, useState } from 'react';
import {
  StyleSheet,
  View,
  Text,
  TouchableOpacity,
} from 'react-native';
import Modal from 'react-native-modal';
import { MaterialIcons } from '@expo/vector-icons';
import { LinearGradient } from 'expo-linear-gradient';
import DatePickerButton from './AddMealDialogCalendarComponent';
import MealTypesButton from './MealTypesButton';
import RecipeProductSearchable from './MenuItemSearchable';
import { Product, Recipe, MenuItem } from '../Interfaces/ICommon';
import MealTypesEnum from '../Enums/MealTypesEnum';
import { addMenuItem, fetchProducts, fetchRecipes } from '@/hooks/useMealAPI';
import { useUser } from '@/context/UserContext';
import Menu from 'react-select/dist/declarations/src/components/Menu';

interface AddMealDialogProps {
  onClose?: () => void;
  onAddMenuItem: () => void;
}

const AddMealDialog: React.FC<AddMealDialogProps> = ({
  onClose,
  onAddMenuItem,
}) => {
  const [isVisible, setIsVisible] = useState(false);

  const [selectedType, setSelectedType] = useState<MealTypesEnum>(MealTypesEnum.BREAKFAST);
  const [searchQuery, setSearchQuery] = useState<string>('');
  const [selectedItemType, setSelectedItemType] = useState<'recipe' | 'product'>('recipe');
  const [selectedItem, setSelectedItem] = useState<Recipe | Product | null>(null);
  const [selectedDate, setSelectedDate] = useState(new Date());

  const [recipies, setRecipies] = useState<Recipe[]>([]);
  const [products, setProducts] = useState<Product[]>([]);
  const { token, isAuthenticated } = useUser();


  useEffect(() => {
    if (isVisible && isAuthenticated && token) {
      const fetchItems = async () => {
        const fetchedRecipes = await fetchRecipes(token);
        const fetchedProducts = await fetchProducts(token);
        setRecipies(fetchedRecipes);
        setProducts(fetchedProducts);
      };
      fetchItems();
    }
  }, [isVisible, isAuthenticated, token]);

  const filteredItems = React.useMemo(() => {
    const items = selectedItemType === 'recipe' ? recipies : products;
    return items.filter(item =>
      item.name.toLowerCase().includes(searchQuery.toLowerCase())
    );
  }, [selectedItemType, searchQuery, recipies, products]);

  const handleAddMeal = () => {
    if (selectedType && selectedItem && selectedDate && token && isAuthenticated) {

      const menuItem: MenuItem = {
        menuItemType: MealTypesEnum[selectedType as keyof typeof MealTypesEnum],
        recipe: selectedItemType === 'recipe' ? (selectedItem as Recipe) : undefined,
        product: selectedItemType === 'product' ? (selectedItem as Product) : undefined
      };

      addMenuItem(token, menuItem, selectedDate);
      onAddMenuItem();
      // Reset and close
      setSelectedType(MealTypesEnum.BREAKFAST);
      setSearchQuery('');
      setSelectedItem(null);
      setIsVisible(false);
    }
  };

  return (
    <>
      <TouchableOpacity
        style={styles.addButton}
        onPress={() => setIsVisible(true)}
      >
        <MaterialIcons name="add" size={16} color="#8B4513" />
        <Text style={styles.addButtonText}>Add meal</Text>
      </TouchableOpacity>

      <Modal
        isVisible={isVisible}
        onBackdropPress={() => setIsVisible(false)}
        backdropOpacity={0.5}
        deviceHeight={Number.POSITIVE_INFINITY}
        animationIn="fadeIn"
        animationOut="fadeOut"
        style={styles.modal}
      >
        <View style={styles.dialogContainer}>
          {/* Header */}
          <LinearGradient
            colors={['#8B4513', '#A0522D', '#8B4513']}
            style={styles.header}
          >
            <Text style={styles.title}>Add New Meal</Text>
            <TouchableOpacity
              onPress={() => setIsVisible(false)}
              style={styles.closeButton}
            >
              <MaterialIcons name="close" size={24} color="#FFF8F0" />
            </TouchableOpacity>
          </LinearGradient>

          {/* Content */}
          <View style={styles.content}>

            {/* Date Picker Section */}
            <DatePickerButton onSelectedDate={setSelectedDate} selectedDate={selectedDate} />

            {/* Meal Type Buttons */}
            <MealTypesButton selectedType={selectedType} setSelectedType={setSelectedType}/>

            {/* Recipe/Product Toggle */}
            <RecipeProductSearchable filteredItems={filteredItems} searchQuery={searchQuery}
            setSearchQuery={setSearchQuery} selectedItem={selectedItem} setSelectedItem={setSelectedItem}
            selectedItemType={selectedItemType} setSelectedItemType={setSelectedItemType}
            setIsVisible={setIsVisible} recipes={recipies} products={products}/>

            {/* Add Button */}
            <TouchableOpacity
              style={[
                styles.addMealButton,
                (!selectedType || !selectedItem) && styles.addMealButtonDisabled,
              ]}
              onPress={handleAddMeal}
              disabled={!selectedType || !selectedItem}
            >
              <Text style={styles.addMealButtonText}>Add to Meal Plan</Text>
            </TouchableOpacity>
          </View>
        </View>
      </Modal>
    </>
  );
};

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
  },
});

export default AddMealDialog;