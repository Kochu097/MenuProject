import { render, fireEvent, cleanup, act, waitFor } from '@testing-library/react-native';
import HomeScreen from '@/app/HomeScreen';
import { UserProvider } from '@/context/UserContext';
import MealPlanFrame from '@/components/MealPlanFrame';
import AddMealDialog from '@/components/AddMealDialog/AddMealDialog';
import MealCard from '@/components/MealCard';
import { fetchRecipes, fetchProducts, fetchMenu, addMenuItem } from '@/hooks/useMealAPI';
import MealTypesEnum from '@/components/Enums/MealTypesEnum';
import Difficulty from '@/components/Enums/DifficultyEnum';
import MenuItemSearchable from '@/components/AddMealDialog/MenuItemSearchable';
import { Recipe } from '@/components/Interfaces/ICommon';
import ProductWeightUnit from '@/components/Enums/ProductWeghtUnit';
import React from 'react';

jest.mock('@/hooks/useMealAPI');

describe('HomeScreen', () => {
  test('renders correctly', () => {
    const { getByTestId } = render(<UserProvider><HomeScreen /></UserProvider>);
    expect(getByTestId('home-screen')).toBeTruthy();
  });

  test('renders MealPlanFrame component', () => {
    const { getByTestId } = render(<UserProvider><HomeScreen /></UserProvider>);
    expect(getByTestId('home-screen').findByType(MealPlanFrame)).toBeTruthy();
  });

  test('has correct background color', () => {
    const { getByTestId } = render(<UserProvider><HomeScreen /></UserProvider>);
    const homeScreenView = getByTestId('home-screen');
    expect(homeScreenView.props.style.backgroundColor).toBe('#8B4513');
  });
});

describe('AddMealDialog', () => {
  test('renders correctly', () => {
    const { getByTestId } = render(<AddMealDialog onAddMenuItem={jest.fn} date={new Date()} selectedMealType={MealTypesEnum.BREAKFAST} />);
    expect(getByTestId('add-meal-dialog')).toBeTruthy();
  });
});

describe('MenuItemSearchable', () => {
  const mockOnSelectedItem = jest.fn();
  let userContextSpy: jest.SpyInstance;

  beforeEach(() => {
    // Mock UserContext values
    userContextSpy = jest.spyOn(require('@/context/UserContext'), 'useUser')
    .mockImplementation(() => ({
      isAuthenticated: true,
      token: 'fake-token'
    }));

    (fetchRecipes as jest.Mock).mockResolvedValue([{
      name: 'Pasta',
      description: 'Delicious pasta',
      imageUrl: 'http://example.com/pasta.jpg',
      ingredients: [],
      preparationTime: '30 mins',
      servings: 2,
      difficulty: Difficulty.Easy
    }]);
    (fetchProducts as jest.Mock).mockResolvedValue([{
      name: 'Tomato',
      description: 'Fresh tomato',
      imageUrl: 'http://example.com/tomato.jpg',
      weight: 200,
      weightUnit: ProductWeightUnit.GRAM,
      calories: 18,
      shared: true
    }]);
  });

  beforeAll(() => {
    jest.useFakeTimers();
  });

  afterAll(() => {
    jest.useRealTimers();
  });

  afterEach(async () => {
    try {
      await act(async () => {
        await Promise.resolve();
        cleanup();
        userContextSpy.mockRestore();
        jest.clearAllMocks();
        jest.clearAllTimers();
      });
    } catch (error) {
      console.error('Cleanup error:', error);
    }
  });

  test('renders correctly', async () => {
    const { getByTestId } = render(
      <UserProvider>
        <MenuItemSearchable onSelectedItem={mockOnSelectedItem} isVisible={true} />
      </UserProvider>
    );
    
    await act(async () => {
      expect(getByTestId('menu-item-searchable')).toBeTruthy();
    });

    await act(async () => {
      await Promise.resolve();
    });
  });

  test('toggles between recipe and product', async () => {
    const { getByText, getByTestId } = render(
      <UserProvider>
        <MenuItemSearchable onSelectedItem={mockOnSelectedItem} isVisible={true} />
      </UserProvider>
    );
    await act(async () => {
      const recipeButton = getByTestId('recipe-button');
      const productButton = getByTestId('product-button');

      fireEvent.press(productButton);
      await waitFor(() => {
        expect(productButton.props.style.backgroundColor).toBe('#FFF');
      });

      fireEvent.press(recipeButton);
      await waitFor(() => {
        expect(recipeButton.props.style.backgroundColor).toBe('#FFF');
      });
    });

    await act(async () => {
      await Promise.resolve();
    });
  });

  test('handles search input correctly', async() => {
    const { getByPlaceholderText } = render(
      <UserProvider>
        <MenuItemSearchable onSelectedItem={mockOnSelectedItem} isVisible={true} />
      </UserProvider>
    );

    await act(async () => {
      const searchInput = getByPlaceholderText('Search recipes...');
      fireEvent.changeText(searchInput, 'Pasta');
      await waitFor(() => {
        expect(searchInput.props.value).toBe('Pasta');
      });
    });

    await act(async () => {
      await Promise.resolve();
    });
  });

  test('displays filtered items correctly', async () => {

    const { getByText, getByPlaceholderText, findByText, getByTestId, toJSON } = render(
      <UserProvider>
        <MenuItemSearchable onSelectedItem={mockOnSelectedItem} isVisible={true} />
      </UserProvider>
    );

    await act(async () => {
    const recipeButton = getByTestId('recipe-button');
    fireEvent.press(recipeButton);

    const pastaItem = await findByText('Pasta');
    expect(pastaItem).toBeTruthy();
    });

    await act(async () => {
      await Promise.resolve();
    });

  });

  test('handles item selection', async () => {

    const { getByPlaceholderText, findByText } = render(
      <UserProvider>
        <MenuItemSearchable onSelectedItem={mockOnSelectedItem} isVisible={true} />
      </UserProvider>
    );

    await act(async () => {
      const searchInput = getByPlaceholderText('Search recipes...');
      fireEvent.changeText(searchInput, 'Pasta');

      const pastaItem = await findByText('Pasta');
      fireEvent.press(pastaItem);

      expect(mockOnSelectedItem).toHaveBeenCalledWith(
        expect.objectContaining({ name: 'Pasta' }),
        1,
        'recipe'
      );
    });

    await act(async () => {
      await Promise.resolve();
    });
  });

    
});

describe('MealCard', () => {
  test('renders correctly', () => {
    const menu = {
      day: new Date(),
      menuItems: []
    }
    const { getByTestId } = render(<MealCard date={new Date()} menu={menu} onAddMenuItem={function (): void {
      throw new Error('Function not implemented.');
    } } index={0} isToday={false} showFullWeek={false} />);
    expect(getByTestId('meal-card')).toBeTruthy();
  });

  test('displays correct date and menu items', () => {
    const menu = {
      day: new Date(),
      menuItems: []
    }
    const { getByTestId } = render(<MealCard date={new Date()} menu={menu} onAddMenuItem={jest.fn() } index={0} isToday={false} showFullWeek={false} />);
    const dateText = getByTestId('date-text');
    const expectedDate = new Date().toLocaleDateString('en-US', { weekday: 'short', month: 'short', day: 'numeric' }).replace(',', ''); 
    expect(dateText.children.map((child: any) => String(child.props.children)).join(' ')).toBe(expectedDate);
  });
});

describe('API Hooks', () => {
  test('fetchRecipes works correctly', async () => {
    (fetchRecipes as jest.Mock).mockResolvedValue([{ name: 'Recipe1' }]);
    const recipes = await fetchRecipes('token');
    expect(recipes).toEqual([{ name: 'Recipe1' }]);
  });

  test('fetchProducts works correctly', async () => {
    (fetchProducts as jest.Mock).mockResolvedValue([{ name: 'Product1' }]);
    const products = await fetchProducts('token');
    expect(products).toEqual([{ name: 'Product1' }]);
  });

  test('fetchMenu works correctly', async () => {
    (fetchMenu as jest.Mock).mockResolvedValue([{ date: new Date(), menuItems: [] }]);
    const menu = await fetchMenu('token', new Date(), new Date());
    expect(menu).toEqual([{ date: new Date(), menuItems: [] }]);
  });

  test('addMenuItem works correctly', async () => {
    (addMenuItem as jest.Mock).mockResolvedValue({ success: true });
    const response = await addMenuItem('token', {
      menuItemType: MealTypesEnum.BREAKFAST, recipe: { name: 'Recipe1', description: 'Delicious breakfast', imageUrl: 'http://example.com/image.jpg', ingredients: [], preparationTime: "10", servings: 1, difficulty: Difficulty.Easy }, servings: 1,
      product: undefined
    }, new Date());
    expect(response).toEqual({ success: true });
  });
});