module.exports = {
    preset: "jest-expo",
    setupFiles: ["<rootDir>/jest.setup.js"],
    transform: {
      "^.+\\.[t|j]sx?$": "babel-jest"
    },
    testMatch: ["**/?(*.)+(spec|test).ts?(x)"],
    collectCoverageFrom: [
      "**/*.{ts,tsx}",
      "!**/coverage/**",
      "!**/node_modules/**",
      "!**/babel.config.js",
      "!**/jest.setup.js",
    ],
    moduleFileExtensions: ["ts", "tsx", "js", "jsx", "json", "node", "android.js", "ios.js", "js.flow"],
    transformIgnorePatterns: [
      "node_modules/(?!((jest-)?react-native|@react-native(-community)?)|expo(nent)?|@expo(nent)?/.*|@expo-google-fonts/.*|react-navigation|@react-navigation/.*|@sentry/react-native|native-base|react-native-svg)"
    ],
    coverageReporters: ["json-summary", "text", "lcov"],
    moduleNameMapper: {
      "^@/(.*)$": "<rootDir>/$1"
    },
  };