import React from 'react';
import { render } from '@testing-library/react-native';
import HomeScreen from '@/app/HomeScreen';

describe('HomeScreen', () => {
  it('renders correctly', () => {
    const { getByTestId } = render(<HomeScreen />);
    expect(getByTestId('home-screen')).toBeTruthy();
  });
});