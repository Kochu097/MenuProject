import React, { useState } from 'react';
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
import RecipeProductSearchable from './RecipeProductSearchable';

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

interface AddMealDialogProps {
  recipes: Recipe[];
  products: Product[];
  onClose?: () => void;
  onAddMeal?: (mealData: {
    type: string;
    itemId: string;
    itemType: 'recipe' | 'product';
    date: Date;
  }) => void;
}

const AddMealDialog: React.FC<AddMealDialogProps> = ({
  recipes,
  products,
  onClose,
  onAddMeal,
}) => {
  const [isVisible, setIsVisible] = useState(false);
  const [selectedType, setSelectedType] = useState<string>('');
  const [searchQuery, setSearchQuery] = useState<string>('');
  const [selectedItemType, setSelectedItemType] = useState<'recipe' | 'product'>('recipe');
  const [selectedItem, setSelectedItem] = useState<Recipe | Product | null>(null);
  const [selectedDate, setSelectedDate] = useState(new Date());

  const filteredItems = React.useMemo(() => {
    const items = selectedItemType === 'recipe' ? recipes : products;
    return items.filter(item =>
      item.name.toLowerCase().includes(searchQuery.toLowerCase())
    );
  }, [selectedItemType, searchQuery, recipes, products]);

  const handleAddMeal = () => {
    if (selectedType && selectedItem && selectedDate) {
      // Placeholder API call
      console.log('Adding meal:', {
        type: selectedType,
        itemId: selectedItem.id,
        itemType: selectedItemType,
        date: selectedDate,
      });
      
      onAddMeal?.({
        type: selectedType,
        itemId: selectedItem.id,
        itemType: selectedItemType,
        date: selectedDate,
      });
      
      // Reset and close
      setSelectedType('');
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
            setIsVisible={setIsVisible} recipes={recipes} products={products}/>

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