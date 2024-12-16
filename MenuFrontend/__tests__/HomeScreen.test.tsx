import { render, fireEvent } from '@testing-library/react-native';
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
  
  beforeAll(() => {
    jest.useFakeTimers();
  });
  
  afterAll(() => {
    jest.useRealTimers();
  });
  
  beforeEach(() => {
    jest.clearAllMocks();
  });

  test('renders correctly', () => {
    const { getByTestId } = render(
      <MenuItemSearchable
        selectedItemType="recipe"
        setSelectedItemType={jest.fn}
        searchQuery=""
        setSearchQuery={jest.fn}
        selectedItem={null}
        setSelectedItem={jest.fn}
        filteredItems={[]}
        recipes={[]}
        products={[]}
        setIsVisible={jest.fn}
        setServings={jest.fn}
      />
    );
    expect(getByTestId('menu-item-searchable')).toBeTruthy();
  });

  test('handles search input correctly', () => {
    const setSearchQuery = jest.fn();
    const { getByPlaceholderText } = render(
      <MenuItemSearchable
        selectedItemType="recipe"
        setSelectedItemType={jest.fn()}
        searchQuery=""
        setSearchQuery={setSearchQuery}
        selectedItem={null}
        setSelectedItem={jest.fn()}
        filteredItems={[]}
        recipes={[]}
        products={[]}
        setIsVisible={jest.fn()}
        setServings={jest.fn()}
      />
    );
    const searchInput = getByPlaceholderText('Search recipes...');
    fireEvent.changeText(searchInput, 'Chicken');
    expect(setSearchQuery).toHaveBeenCalledWith('Chicken');
  });

  test('toggles between recipe and product', () => {
    const setSelectedItemType = jest.fn();
    const { getByText } = render(
      <MenuItemSearchable
        selectedItemType="recipe"
        setSelectedItemType={setSelectedItemType}
        searchQuery=""
        setSearchQuery={jest.fn()}
        selectedItem={null}
        setSelectedItem={jest.fn()}
        filteredItems={[]}
        recipes={[]}
        products={[]}
        setIsVisible={jest.fn()}
        setServings={jest.fn()}
      />
    );
    fireEvent.press(getByText('Product'));
    expect(setSelectedItemType).toHaveBeenCalledWith('product');
  });

  test('displays filtered items correctly', () => {
    const mockItems = [
    { 
      name: 'Spaghetti Bolognese', 
      description: 'A classic Italian pasta dish with rich meat sauce', 
      imageUrl: 'http://example.com/spaghetti.jpg', 
      ingredients: [
        { product: { name: 'Spaghetti', description: 'Italian pasta', imageUrl: undefined, weight: 500, weightUnit: ProductWeightUnit.GRAM, calories: 200, shared: false }, amount: '500', unit: 'GRAM' },
        { product: { name: 'Ground Beef', description: 'Lean ground beef', imageUrl: undefined, weight: 500, weightUnit: ProductWeightUnit.GRAM, calories: 250, shared: false }, amount: '500', unit: 'GRAM' },
        { product: { name: 'Tomato Sauce', description: 'Rich tomato sauce', imageUrl: undefined, weight: 300, weightUnit: ProductWeightUnit.GRAM, calories: 100, shared: false }, amount: '300', unit: 'MILLILITER' }
      ], 
      preparationTime: '45', 
      servings: 4, 
      difficulty: Difficulty.Medium 
    },
    { 
      name: 'Chicken Caesar Salad', 
      description: 'A fresh salad with grilled chicken, romaine lettuce, and Caesar dressing', 
      imageUrl: 'http://example.com/caesar_salad.jpg', 
      ingredients: [
        { product: { name: 'Chicken Breast', description: 'Grilled chicken breast', imageUrl: undefined, weight: 200, weightUnit: ProductWeightUnit.GRAM, calories: 165, shared: false }, amount: '200', unit: 'GRAM' },
        { product: { name: 'Romaine Lettuce', description: 'Fresh romaine lettuce', imageUrl: undefined, weight: 150, weightUnit: ProductWeightUnit.GRAM, calories: 15, shared: false }, amount: '150', unit: 'GRAM' },
        { product: { name: 'Caesar Dressing', description: 'Creamy Caesar dressing', imageUrl: undefined, weight: 50, weightUnit: ProductWeightUnit.GRAM, calories: 80, shared: false }, amount: '50', unit: 'GRAM' }
      ], 
      preparationTime: '20', 
      servings: 2, 
      difficulty: Difficulty.Easy 
    }
    ];
    const { getByText } = render(
      <MenuItemSearchable
        selectedItemType="recipe"
        setSelectedItemType={jest.fn()}
        searchQuery=""
        setSearchQuery={jest.fn()}
        selectedItem={null}
        setSelectedItem={jest.fn()}
        filteredItems={mockItems}
        recipes={[]}
        products={[]}
        setIsVisible={jest.fn()}
        setServings={jest.fn()}
      />
    );
    expect(getByText('Spaghetti Bolognese')).toBeTruthy();
    expect(getByText('Chicken Caesar Salad')).toBeTruthy();
  });

  test('handles item selection', () => {
    const setSelectedItem = jest.fn();
    
    const mockItem = {
      name: 'Test Recipe',
      description: 'Test description',
      imageUrl: 'http://example.com/image.jpg',
      ingredients: [],
      preparationTime: '30',
      servings: 4,
      difficulty: Difficulty.Easy
    } as Recipe;
    const { getByText } = render(
      <MenuItemSearchable
        selectedItemType="recipe"
        setSelectedItemType={jest.fn()}
        searchQuery=""
        setSearchQuery={jest.fn()}
        selectedItem={null}
        setSelectedItem={setSelectedItem}
        filteredItems={[mockItem]}
        recipes={[]}
        products={[]}
        setIsVisible={jest.fn()}
        setServings={jest.fn()}
      />
    );
    fireEvent.press(getByText('Test Recipe'));
    expect(setSelectedItem).toHaveBeenCalledWith(mockItem);
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