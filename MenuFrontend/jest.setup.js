
jest.mock('@/context/UserContext', () => {
  return {
    UserProvider: ({ children }) => children,
    useUser: () => ({
      user: { name: 'Test User', id: '1' },
      setUser: jest.fn(),
    }),
  };
});

jest.mock('expo-font', () => ({
  isLoaded: jest.fn().mockReturnValue(true),
  loadAsync: jest.fn(),
  useFonts: () => [true],
}));