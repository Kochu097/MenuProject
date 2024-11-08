import React, { useState } from 'react';
import Select, { SingleValue, StylesConfig } from 'react-select';
import { StyleSheet, View } from 'react-native';
import { MaterialIcons } from '@expo/vector-icons';

interface Option {
  label: string;
  value: string;
}

interface IngredientAmountDropdownProps {
  onChange: (value: string) => void;
}

const IngredientAmountDropdown: React.FC<IngredientAmountDropdownProps> = ({ onChange }) => {
  const [selectedOption, setSelectedOption] = useState<Option | null>(null);

  const options: Option[] = [
    { label: 'teaspoon', value: 'teaspoon' },
    { label: 'tablespoon', value: 'tablespoon' },
    { label: 'cup', value: 'cup' },
    { label: 'gram', value: 'gram' },
    { label: 'ounce', value: 'ounce' },
    { label: 'pound', value: 'pound' },
    { label: 'piece', value: 'piece' },
    { label: 'whole', value: 'whole' },
  ];

  const handleOptionSelect = (option: SingleValue<Option>) => {
    setSelectedOption(option);
    if (option) {
      onChange(option.value);
    }
  };

  const customStyles: StylesConfig<Option, false> = {
    control: (provided) => ({
      ...provided,
      backgroundColor: '#F4F4F4',
      borderRadius: 4,
      padding: 12,
      flexDirection: 'row',
      alignItems: 'center',
      justifyContent: 'space-between',
      borderWidth: 1,
      borderColor: '#D4B483',
    }),
    singleValue: (provided) => ({
      ...provided,
      fontSize: 14,
      color: '#8B4513',
    }),
    menu: (provided) => ({
      ...provided,
      backgroundColor: '#FFF9E5',
      borderRadius: 8,
      elevation: 4,
      shadowColor: '#000',
      shadowOffset: { width: 0, height: 2 },
      shadowOpacity: 0.25,
      maxWidth: 300,
      zIndex: 10,
      padding: 8,
    }),
    option: (provided, { isSelected }) => ({
      ...provided,
      paddingVertical: 12,
      paddingHorizontal: 16,
      backgroundColor: isSelected ? '#D4B483' : '#FFF9E5',
      color: isSelected ? '#FFF' : '#8B4513',
    }),
  };

  return (
    <View style={styles.container}>
      <Select
        options={options}
        onChange={handleOptionSelect}
        value={selectedOption}
        placeholder="Select unit"
        styles={customStyles}
        components={{
          DropdownIndicator: () => (
            <MaterialIcons name="arrow-drop-down" size={24} color="#8B4513" />
          ),
        }}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    position: 'relative',
  },
});

export default IngredientAmountDropdown;