import { registerRootComponent } from 'expo';
import React from 'react';
import HomeScreen from './screens/HomeScreen';
import { UserProvider } from '@/context/UserContext';


const App: React.FC = () => {
  return (
    <UserProvider>
      <HomeScreen />
    </UserProvider>
  );
};

export default registerRootComponent(App);
