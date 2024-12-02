import { View, TouchableOpacity, Text, StyleSheet } from "react-native";
import MealTypesEnum from "../Enums/MealTypesEnum";

interface MealTypesButtonProp {
    selectedType: string;
    setSelectedType: (type: MealTypesEnum) => void;
}

const MealTypesButton: React.FC<MealTypesButtonProp> = ({selectedType, setSelectedType}) => {
    const MEAL_TYPES = Object.values(MealTypesEnum);

    return (
        <View style={styles.section}>
              <Text style={styles.label}>Meal Type</Text>
              <View style={styles.mealTypeButtons}>
                {MEAL_TYPES.map((type) => (
                  <TouchableOpacity
                    key={type}
                    style={[
                      styles.mealTypeButton,
                      selectedType === type && styles.mealTypeButtonSelected,
                    ]}
                    onPress={() => setSelectedType(type)}
                  >
                    <Text style={[
                      styles.mealTypeButtonText,
                      selectedType === type && styles.mealTypeButtonTextSelected,
                    ]}>
                      {type}
                    </Text>
                  </TouchableOpacity>
                ))}
              </View>
            </View>
    );
}

const styles = StyleSheet.create({
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
  });

  export default MealTypesButton;