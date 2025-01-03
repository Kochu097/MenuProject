import { useRef, useState } from "react";
import Modal from 'react-native-modal';
import { Product } from "../Interfaces/ICommon";
import { TouchableOpacity, Text, StyleSheet, View, Image, TextInput} from "react-native";
import { MaterialIcons } from "@expo/vector-icons";
import { LinearGradient } from "expo-linear-gradient";
import Select, { StylesConfig } from 'react-select';
import ProductWeightUnit from "../Enums/ProductWeghtUnit";
import React from "react";

interface Option {
    label: string;
    value: string;
  }
  
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
        width: 100,
        boxShadow: '0 2px 4px rgba(0, 0, 0, 0.25)',
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
        boxShadow: '0 2px 4px rgba(0, 0, 0, 0.25)',
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

interface AddProductDialogProps {
    onAddProduct: (product: Product, file: File | undefined) => void;
}

const AddProductDialog: React.FC<AddProductDialogProps> = ({onAddProduct}) => {

    const weightOptions = Object.values(ProductWeightUnit).map(unit => ({
        value: unit, 
        label: unit,
      }));

    const [isVisible, setIsVisible] = useState(false);
    const [selectedImage, setSelectedImage] = useState<File | undefined>(undefined);
    const [selectedImageUrl, setSelectedImageUrl] = useState<string | undefined>(undefined);
    const [productName, setProductName] = useState<string>('');
    const [productDescription, setProductDescription] = useState<string>('');
    const [productWeight, setProductWeight] = useState<string>('');
    const [productCalories, setProductCalories] = useState<string>('');
    const [weightUnitOption, setWeightUnitOption] = useState<Option>(weightOptions[0]);
    const fileInputRef = useRef<HTMLInputElement>(null);


    const clearForm = () => {
        setSelectedImage(undefined);
        setProductName('');
        setProductDescription('');
        setProductWeight('');
        setProductCalories('');
        setWeightUnitOption(weightOptions[0]);
    }

    const handleSetWeightUnitOption = (unit: Option | null) => {
        if(unit != null)
        setWeightUnitOption(unit);
    }

    const handleOnClose = () => {
        setIsVisible(false);
        clearForm();
    }

    const handleImageUpload = (event: React.ChangeEvent<HTMLInputElement>) => {
      if (event.target.files && event.target.files.length > 0) {
        const file = event.target.files[0];
        const imageUrl = URL.createObjectURL(file);
        setSelectedImageUrl(imageUrl);
        setSelectedImage(file);
      }
    };

    const handleOpenImagePicker = () => {
      if (fileInputRef.current) {
        fileInputRef.current.click();
      }
    };

    const handleAddProduct = () => {
        const newProduct: Product = {
            name: productName,
            description: productDescription,
            imageUrl: selectedImage ? URL.createObjectURL(selectedImage) : undefined,  
            weight: Number(productWeight),
            weightUnit: Object.keys(ProductWeightUnit).find(key => ProductWeightUnit[key as keyof typeof ProductWeightUnit] === weightUnitOption.value) as ProductWeightUnit,
            calories: productCalories === '' ? 0 : Number(productCalories),
            shared: false,
        };
        onAddProduct(newProduct, selectedImage);
        setIsVisible(false);
        clearForm();

    }

    return (
        <>
            <TouchableOpacity
            style={styles.addButton}
            onPress={() => setIsVisible(true)}
            >
                <MaterialIcons name="add" size={16} color="#8B4513" />
                <Text style={styles.addButtonText}>Add Product</Text>
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

                {/* HEADER */}
                <View style={styles.dialogContainer}>
                    <LinearGradient
                    colors={['#8B4513', '#A0522D', '#8B4513']}
                    style={styles.header}
                    >
                        <Text style={styles.title}>Add New Product</Text>
                        <TouchableOpacity
                        onPress={() => setIsVisible(false)}
                        style={styles.closeButton}
                        >
                        <MaterialIcons name="close" size={24} color="#FFF8F0" />
                        </TouchableOpacity>
                    </LinearGradient>

                    {/* CONTENT */}
                    <View style={styles.content}>
                        <View style={styles.imageContainer}>
                            {selectedImage ? (
                            <Image
                                source={{ uri: selectedImageUrl }}
                                style={styles.selectedImage}
                                resizeMode="center"
                            />
                            ) : (
                            <View style={styles.imagePlaceholder}>
                                <Text style={styles.imagePlaceholderText}>
                                Select an image
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
                        </View>
                        <TextInput
                            style={[styles.input]}
                            placeholder="Product Name"
                            value={productName}
                            onChangeText={setProductName}
                            placeholderTextColor="#A67B5B"
                            key="productName"
                        />
                        <TextInput
                            style={[styles.input, styles.descriptionInput]}
                            placeholder="Product Description"
                            value={productDescription}
                            onChangeText={setProductDescription}
                            placeholderTextColor="#A67B5B"
                            multiline
                            key="productDescription"
                        />
                        <View style={styles.weightInput}>
                            <TextInput
                                style={[styles.input]}
                                placeholder="Product Weight"
                                value={productWeight}
                                onChangeText={(value) =>
                                    setProductWeight(value.replace(/[^0-9]/g, ''))
                                }
                                placeholderTextColor="#A67B5B"
                                key="productWeight"
                            />
                            <Select 
                            options={weightOptions}
                            value={weightUnitOption}
                            onChange={handleSetWeightUnitOption}
                            placeholder={weightOptions[0].label}
                            styles={customStyles}
                            menuPlacement='top'
                            components={{
                                DropdownIndicator: () => (
                                  <MaterialIcons name="arrow-drop-down" size={24} color="#8B4513" />
                                )
                              }}
                            />
                        </View>
                        
                        <TextInput
                            style={[styles.input]}
                            placeholder="Kcal"
                            value={productCalories}
                            onChangeText={(value) =>
                                setProductCalories(value.replace(/[^0-9]/g, ''))
                              }
                            placeholderTextColor="#A67B5B"
                            key="productCalories"
                        />
                    </View>

                    {/* FOOTER */}
                    <View style={styles.footer}>
                    <TouchableOpacity
                    style={styles.addProductButton}
                    onPress={handleAddProduct}
                    >
                    <Text style={styles.addProductButtonText}>Add Recipe</Text>
                    </TouchableOpacity>
                    </View>
                </View>
            </Modal>
        </>
    );
}

const styles = StyleSheet.create({
    addButton: {
        flexDirection: 'row',
        alignItems: 'center',
        gap: 4,
        padding: 8,
        boxShadow: '0 2px 4px rgba(0, 0, 0, 0.25)',
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
        backgroundColor: '#FFF9E5',
        justifyContent: 'space-between',
        alignItems: 'center',
    },
    addProductButton: {
        backgroundColor: '#8B4513',
        borderRadius: 8,
        padding: 16,
        alignItems: 'center',
        boxShadow: '0 2px 4px rgba(0, 0, 0, 0.25)',
    },
    addProductButtonText: {
        fontSize: 16,
        fontWeight: '600',
        color: '#FFF',
    },
    footer: {
        padding: 16,
        gap: 16,
        backgroundColor: '#FFF9E5',
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
        boxShadow: '0 2px 4px rgba(0, 0, 0, 0.25)',
    },
    imagePickerButtonText: {
        fontSize: 14,
        color: '#8B4513',
    },
    imagePlaceholder: {
        width: 200,
        height: 200,
        backgroundColor: '#F4F4F4',
        justifyContent: 'center',
        alignItems: 'center',
        borderRadius: 8,
        boxShadow: '0 2px 4px rgba(0, 0, 0, 0.25)',
    },
    imagePlaceholderText: {
        fontSize: 16,
        color: '#A67B5B',
        textAlign: 'center',
    },
    selectedImage: {
        width: 200,
        height: 200,
        borderRadius: 8,
        boxShadow: '0 2px 4px rgba(0, 0, 0, 0.25)',
    },
    input: {
        width: '100%',
        backgroundColor: '#FFF',
        borderRadius: 8,
        padding: 12,
        fontSize: 16,
        color: '#8B4513',
        borderWidth: 1,
        borderColor: '#D4B483',
        marginBottom: 16,
        boxShadow: '0 2px 4px rgba(0, 0, 0, 0.25)',
    },
    descriptionInput: {
        flex: 1,
    },
    weightInput: {
        width: '100%',
        flex: 1,
        flexDirection: 'row',
        alignItems: 'center',
        gap: 12,
        position: 'relative',
    },
});

export default AddProductDialog;