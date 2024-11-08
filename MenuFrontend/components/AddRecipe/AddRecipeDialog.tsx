import React, { useState, useRef } from 'react';
import {
  StyleSheet,
  View,
  Text,
  TouchableOpacity,
  ScrollView,
  Image,
  TouchableWithoutFeedback,
  Keyboard,
  TextInput,
} from 'react-native';
import Modal from 'react-native-modal';
import { MaterialIcons } from '@expo/vector-icons';
import IngredientAmountDropdown from './IngredientAmountDropdown';

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

interface AddRecipeDialogProps {
  recipes: Recipe[];
  products: Product[];
  onAddRecipe: (newRecipe: Recipe) => void;
  onClose?: () => void;
}

const AddRecipeDialog: React.FC<AddRecipeDialogProps> = ({
  recipes,
  products,
  onAddRecipe,
  onClose,
}) => {
  const [isVisible, setIsVisible] = useState(false);
  const [isIngredientsModalVisible, setIsIngredientsModalVisible] = useState(false);
  const [newRecipeName, setNewRecipeName] = useState('');
  const [newRecipeDescription, setNewRecipeDescription] = useState('');
  const [selectedProducts, setSelectedProducts] = useState<
    { id: string; name: string; amount: string; unit: string }[]
  >([]);
  const [newRecipePreparationTime, setNewRecipePreparationTime] = useState('');
  const [newRecipeServings, setNewRecipeServings] = useState('');
  const [newRecipeDifficulty, setNewRecipeDifficulty] = useState('');
  const [newRecipeImageUrl, setNewRecipeImageUrl] = useState('');
  const [selectedImage, setSelectedImage] = useState<string | null>(null);
  const fileInputRef = useRef<HTMLInputElement>(null);

  const handleAddRecipe = () => {
    const newRecipe: Recipe = {
      id: `recipe-${recipes.length + 1}`,
      name: newRecipeName,
      description: newRecipeDescription,
      imageUrl: selectedImage || newRecipeImageUrl,
      ingredients: selectedProducts,
      preparationTime: newRecipePreparationTime,
      servings: parseInt(newRecipeServings),
      difficulty: newRecipeDifficulty,
    };

    onAddRecipe(newRecipe);
    setIsVisible(false);
    clearForm();
  };

  const clearForm = () => {
    setNewRecipeName('');
    setNewRecipeDescription('');
    setSelectedProducts([]);
    setNewRecipePreparationTime('');
    setNewRecipeServings('');
    setNewRecipeDifficulty('');
    setNewRecipeImageUrl('');
    setSelectedImage(null);
  };

  const handleOnClose = () => {
    setIsVisible(false);
    clearForm();
    if(onClose != undefined) onClose();
  }

  const handleRemoveIngredient = (index: number) => {
    const updatedProducts = [...selectedProducts];
    updatedProducts.splice(index, 1);
    setSelectedProducts(updatedProducts);
  };

  const handleChangeIngredientAmount = (index: number, amount: string) => {
    const updatedProducts = [...selectedProducts];
    updatedProducts[index].amount = amount;
    setSelectedProducts(updatedProducts);
  };

  const handleChangeUnit = (index: number, unit: string) => {
    const updatedProducts = [...selectedProducts];
    updatedProducts[index].unit = unit;
    setSelectedProducts(updatedProducts);
  };

  const handleImageUpload = (event: React.ChangeEvent<HTMLInputElement>) => {
    if (event.target.files && event.target.files.length > 0) {
      const file = event.target.files[0];
      const imageUrl = URL.createObjectURL(file);
      setSelectedImage(imageUrl);
      setNewRecipeImageUrl('');
    }
  };

  const handleOpenImagePicker = () => {
    if (fileInputRef.current) {
      fileInputRef.current.click();
    }
  };

  const handleOpenIngredientsModal = () => {
    setIsIngredientsModalVisible(true);
  };

  const handleCloseIngredientsModal = () => {
    setIsIngredientsModalVisible(false);
  };

  const handleAddSelectedIngredient = (product: Product, unit: string) => {
    setSelectedProducts([
      ...selectedProducts,
      { id: product.id, name: product.name, amount: '', unit: unit },
    ]);
    setIsIngredientsModalVisible(false);
  };

  return (
    <>
      <TouchableOpacity
        style={styles.addButton}
        onPress={() => setIsVisible(true)}
      >
        <MaterialIcons name="add" size={16} color="#8B4513" />
        <Text style={styles.addButtonText}>Add Recipe</Text>
      </TouchableOpacity>

      <Modal
        isVisible={isVisible}
        onBackdropPress={() => setIsVisible(false)}
        onBackButtonPress={() => setIsVisible(false)}
        backdropOpacity={0.5}
        animationIn="fadeIn"
        animationOut="fadeOut"
        style={styles.modal}
        onModalHide={handleOnClose}
      >
        <View style={styles.dialogContainer}>
          <View style={styles.header}>
            <Text style={styles.title}>Add New Recipe</Text>
            <TouchableOpacity
              onPress={() => setIsVisible(false)}
              style={styles.closeButton}
            >
              <MaterialIcons name="close" size={24} color="#8B4513" />
            </TouchableOpacity>
          </View>
          <View style={styles.bookLayout}>
            {/* Left Page */}
            <View style={styles.leftPage}>
              <View style={styles.imageContainer}>
                {selectedImage ? (
                  <Image
                    source={{ uri: selectedImage }}
                    style={styles.selectedImage}
                  />
                ) : (
                  <View style={styles.imagePlaceholder}>
                    <Text style={styles.imagePlaceholderText}>
                      {newRecipeImageUrl
                        ? 'Loading...'
                        : 'Drag and drop or select an image'}
                    </Text>
                  </View>
                )}
                <TouchableOpacity
                  style={styles.imagePickerButton}
                  onPress={handleOpenImagePicker}
                >
                  <MaterialIcons name="photo-library" size={20} color="#8B4513" />
                  <Text style={styles.imagePickerButtonText}>Select Image</Text>
                </TouchableOpacity>
                <input
                  type="file"
                  accept="image/*"
                  ref={fileInputRef}
                  style={{ display: 'none' }}
                  onChange={handleImageUpload}
                />
                {newRecipeImageUrl && (
                  <TextInput
                    style={[styles.input, styles.imageInput]}
                    placeholder="Image URL"
                    value={newRecipeImageUrl}
                    onChangeText={setNewRecipeImageUrl}
                    placeholderTextColor="#A67B5B"
                  />
                )}
              </View>
              <TextInput
                style={[styles.input, styles.recipeNameInput]}
                placeholder="Recipe Name"
                value={newRecipeName}
                onChangeText={setNewRecipeName}
                placeholderTextColor="#A67B5B"
                key="recipeName"
              />
              <View style={styles.selectedIngredientsContent}>
                <View style={styles.selectedIngredients}>
                  {selectedProducts.map((ingredient, index) => (
                    <View key={index} style={styles.ingredientRow}>
                      <TouchableOpacity
                        onPress={() => handleRemoveIngredient(index)}
                      >
                        <MaterialIcons name="close" size={20} color="#8B4513" />
                      </TouchableOpacity>
                      <View style={styles.ingredientInfo}>
                        <Text style={styles.ingredientName}>
                          {ingredient.name}
                        </Text>
                        <TextInput
                          style={styles.ingredientAmount}
                          placeholder="Amount"
                          value={ingredient.amount}
                          onChangeText={(amount) =>
                            handleChangeIngredientAmount(index, amount)
                          }
                          placeholderTextColor="#A67B5B"
                          key="ingridientAmount"
                        />
                        <IngredientAmountDropdown
                          onChange={(unit) => handleChangeUnit(index, unit)}
                        />
                      </View>
                    </View>
                  ))}
                  <TouchableOpacity
                    style={styles.addIngredientButton}
                    onPress={handleOpenIngredientsModal}
                  >
                    <MaterialIcons name="add" size={20} color="#8B4513" />
                    <Text style={styles.addIngredientButtonText}>Add Ingredient</Text>
                  </TouchableOpacity>
                </View>
              </View>
            </View>
            {/* Right Page */}
            <View style={styles.rightPage}>
              <TextInput
                style={[styles.input, styles.descriptionInput]}
                placeholder="Description"
                value={newRecipeDescription}
                onChangeText={setNewRecipeDescription}
                placeholderTextColor="#A67B5B"
                multiline
                key="Description"
              />
              <View style={styles.recipeDetails}>
                <TextInput
                  style={[styles.input, styles.detailsInput]}
                  placeholder="Preparation Time"
                  value={newRecipePreparationTime}
                  onChangeText={setNewRecipePreparationTime}
                  placeholderTextColor="#A67B5B"
                  key="preperationTime"
                />
                <TextInput
                  style={[styles.input, styles.detailsInput]}
                  placeholder="Servings ex.2"
                  value={newRecipeServings.toString()}
                  onChangeText={(value) =>
                    setNewRecipeServings(value)
                  }
                  placeholderTextColor="#A67B5B"
                  keyboardType="numeric"
                  key="servings"
                />
                <TextInput
                  keyboardType="default"
                  style={[styles.input, styles.detailsInput]}
                  placeholder="Difficulty"
                  value={newRecipeDifficulty}
                  onChangeText={setNewRecipeDifficulty}
                  placeholderTextColor="#A67B5B"
                  key="difficulty"
                />
                <TouchableOpacity
                  style={styles.addRecipeButton}
                  onPress={handleAddRecipe}
                >
                  <Text style={styles.addRecipeButtonText}>Add Recipe</Text>
                </TouchableOpacity>
              </View>
            </View>
          </View>
        </View>
      </Modal>
      <Modal
        isVisible={isIngredientsModalVisible}
        onBackdropPress={handleCloseIngredientsModal}
        onBackButtonPress={handleCloseIngredientsModal}
        backdropOpacity={0.5}
        animationIn="fadeIn"
        animationOut="fadeOut"
        style={styles.modal}
      >
        <View style={styles.ingredientsModalContainer}>
          <View style={styles.ingredientsModalHeader}>
            <Text style={styles.ingredientsModalTitle}>Select Ingredient</Text>
            <TouchableOpacity
              onPress={handleCloseIngredientsModal}
              style={styles.closeButton}
            >
              <MaterialIcons name="close" size={24} color="#8B4513" />
            </TouchableOpacity>
          </View>
          <ScrollView style={styles.ingredientsModalContent}>
            {products.map((product) => (
              <TouchableOpacity
                key={product.id}
                style={styles.ingredientItem}
                onPress={() => handleAddSelectedIngredient(product, '')}
              >
                {product.imageUrl != '' ? (
                <Image
                  source={{ uri: product.imageUrl }}
                  style={styles.ingredientImage}
                />
                ) : (
                    <MaterialIcons name="photo-library" size={80} color="#8B4513" />
                )}
                <Text style={styles.ingredientName}>{product.name}</Text>
              </TouchableOpacity>
            ))}
          </ScrollView>
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
      maxWidth: 800,
      maxHeight:'100%',
      backgroundColor: '#FFF9E5',
      borderRadius: 12,
      overflow: 'scroll',
      elevation: 8,
      shadowColor: '#000',
      shadowOffset: { width: 0, height: 2 },
      shadowOpacity: 0.25,
      paddingVertical: 24,
    },
    header: {
      flexDirection: 'row',
      justifyContent: 'space-between',
      alignItems: 'center',
      marginBottom: 16,
      paddingHorizontal: 24,
    },
    title: {
      fontSize: 20,
      fontWeight: 'bold',
      color: '#6B4423',
    },
    closeButton: {
      padding: 4,
    },
    bookLayout: {
      flexDirection: 'row',
      height: '90%',
      gap: 24,
    },
    leftPage: {
      flex: 1,
      backgroundColor: '#FFF9E5',
      padding: 24,
    },
    rightPage: {
      flex: 1,
      backgroundColor: '#FFF9E5',
      padding: 24,
    },
    selectedIngredientsContent: {
        flex: 1,
        gap: 16,
    },
    input: {
      backgroundColor: '#FFF',
      borderRadius: 8,
      padding: 12,
      fontSize: 16,
      color: '#8B4513',
      borderWidth: 1,
      borderColor: '#D4B483',
    },
    recipeNameInput: {
      marginBottom: 16,
    },
    descriptionInput: {
      marginBottom: 16,
      flex: 1,
    },
    detailsInput: {
      marginBottom: 12,
    },
    imageInput: {
      marginTop: 12,
    },
    label: {
      fontSize: 16,
      fontWeight: '600',
      color: '#6B4423',
      marginBottom: 8,
    },
    ingredientsContainer: {
      gap: 16,
    },
    ingredientsList: {
      flexDirection: 'row',
      gap: 16,
      paddingVertical: 8,
    },
    ingredientItem: {
      flexDirection: 'row',
      alignItems: 'center',
      gap: 8,
    },
    ingredientImage: {
      width: 80,
      height: 80,
      borderRadius: 8,
    },
    ingredientName: {
      fontSize: 14,
      color: '#8B4513',
      textAlign: 'center',
    },
    selectedIngredients: {
      gap: 12,
    },
    ingredientRow: {
      flexDirection: 'row',
      alignItems: 'center',
      gap: 12,
    },
    ingredientInfo: {
      flex: 1,
      flexDirection: 'row',
      alignItems: 'center',
      gap: 12,
    },
    ingredientAmount: {
      backgroundColor: '#F4F4F4',
      borderRadius: 4,
      padding: 8,
      fontSize: 14,
      color: '#8B4513',
      width: 100,
    },
    imageContainer: {
      alignItems: 'center',
      justifyContent: 'center',
      gap: 12,
    },
    imagePickerButton: {
      flexDirection: 'row',
      alignItems: 'center',
      gap: 4,
      padding: 12,
      backgroundColor: '#FFF',
      borderRadius: 8,
      borderWidth: 1,
      borderColor: '#D4B483',
    },
    imagePickerButtonText: {
      fontSize: 14,
      color: '#8B4513',
    },
    imagePlaceholder: {
      width: '100%',
      height: 200,
      backgroundColor: '#F4F4F4',
      justifyContent: 'center',
      alignItems: 'center',
      borderRadius: 8,
    },
    imagePlaceholderText: {
      fontSize: 16,
      color: '#A67B5B',
      textAlign: 'center',
    },
    selectedImage: {
      width: '100%',
      height: 200,
      resizeMode: 'contain',
      borderRadius: 8,
    },
    recipeDetails: {
      gap: 12
    },
    addRecipeButton: {
      backgroundColor: '#8B4513',
      borderRadius: 8,
      padding: 16,
      alignItems: 'center',
    },
    addRecipeButtonText: {
      fontSize: 16,
      fontWeight: '600',
      color: '#FFF',
    },
    addIngredientButton: {
        flexDirection: 'row',
        alignItems: 'center',
        gap: 8,
      },
      addIngredientButtonText: {
        fontSize: 14,
        color: '#8B4513',
      },
      ingredientsModalContainer: {
        backgroundColor: '#FFF9E5',
        borderRadius: 12,
        overflow: 'hidden',
        elevation: 8,
        shadowColor: '#000',
        shadowOffset: { width: 0, height: 2 },
        shadowOpacity: 0.25,
        width: '90%',
        maxWidth: 600,
      },
      ingredientsModalHeader: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
        backgroundColor: '#8B4513',
        padding: 16,
      },
      ingredientsModalTitle: {
        fontSize: 18,
        fontWeight: 'bold',
        color: '#FFF',
      },
      ingredientsModalContent: {
        padding: 16,
      },
  });

export default AddRecipeDialog;