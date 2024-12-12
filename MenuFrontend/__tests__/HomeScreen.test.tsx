import { render } from '@testing-library/react-native';
import HomeScreen from '@/app/HomeScreen';

describe('HomeScreen', () => {
    it('renders correctly', () => {
        // expect(1).toBe(1);
    const { getByTestId } = render(<HomeScreen />);
    expect(getByTestId('home-screen')).toBeTruthy();
  });
});