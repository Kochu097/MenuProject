global.__DEV__ = true;

// Mocking native modules
jest.mock('react-native/Libraries/EventEmitter/NativeEventEmitter');
jest.mock('react-native/Libraries/BatchedBridge/NativeModules', () => ({
  ...jest.requireActual('react-native/Libraries/BatchedBridge/NativeModules'),
  PlatformConstants: {
    forceTouchAvailable: false,
  },
}));