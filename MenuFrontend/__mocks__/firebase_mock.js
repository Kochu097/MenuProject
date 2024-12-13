module.exports = {
    getApps: jest.fn(() => []),
    initializeApp: jest.fn(),
    getApp: jest.fn(),
    getFirestore: jest.fn(() => ({
      collection: jest.fn(),
    })),
    collection: jest.fn(() => ({
      withConverter: jest.fn(),
    })),
  }