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
  date: Date;
  selectedMealType: MealTypesEnum
}

const AddMealDialog: React.FC<AddMealDialogProps> = ({
  onClose,
  onAddMenuItem,
  date,
  selectedMealType
}) => {
  const [isVisible, setIsVisible] = useState(false);

  const [selectedType, setSelectedType] = useState<MealTypesEnum>(selectedMealType);
  const [selectedItem, setSelectedItem] = useState<{
    selectedItemType: 'recipe' | 'product',
    item: Recipe | Product | null,
    servings: number
  }| null>(null);
  const [selectedDate, setSelectedDate] = useState(date);

  const { token, isAuthenticated } = useUser();

  const handleAddMeal = () => {
    if (selectedType && selectedItem && selectedDate && token && isAuthenticated) {

      const menuItem: MenuItem = {
        menuItemType: MealTypesEnum[selectedType as keyof typeof MealTypesEnum],
        recipe: selectedItem.selectedItemType === 'recipe' ? (selectedItem.item as Recipe) : undefined,
        product: selectedItem.selectedItemType === 'product' ? (selectedItem.item as Product) : undefined,
        servings: selectedItem.servings,
      };

      addMenuItem(token, menuItem, selectedDate);
      onAddMenuItem();
      // Reset and close
      setSelectedType(selectedMealType);
      setSelectedItem(null);
      setIsVisible(false);
    }
  };

  return (
    <>
      <View testID="add-meal-dialog"></View>
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
            <RecipeProductSearchable onSelectedItem={(item, servings, selectedItemType) => setSelectedItem({ item, servings, selectedItemType })} isVisible={isVisible}/>

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
    width: '100%',
  },
  dialogContainer: {
    width: '90%',
    maxWidth: 500,
    backgroundColor: '#FFF9E5',
    borderRadius: 12,
    overflow: 'hidden',
    elevation: 8,
    boxShadow: '0 2px 4px rgba(0, 0, 0, 0.25)',
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