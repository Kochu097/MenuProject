import { registerRootComponent } from 'expo';
import React from 'react';
import HomeScreen from './HomeScreen';
import { UserProvider } from '@/context/UserContext';


const App: React.FC = () => {
  return (
    <UserProvider>
      <HomeScreen />
    </UserProvider>
  );
};

export default App;
