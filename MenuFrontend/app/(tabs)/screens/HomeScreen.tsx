import { View } from 'react-native';
import Sidebar from '@/components/HomeScreenSidebar';
import MealPlanFrame from '@/components/MealPlanFrame';

const CORK_PATTERN = `
  <svg width="100" height="100" xmlns="http://www.w3.org/2000/svg">
    <filter id="noise">
      <feTurbulence type="fractalNoise" baseFrequency="0.8" numOctaves="4" stitchTiles="stitch"/>
    </filter>
    <rect width="100" height="100" filter="url(#noise)" opacity="0.4"/>
  </svg>
`;

const HomeScreen: React.FC = () => {

  return (
    <View style={{
      flex: 1,
      backgroundColor: '#8B4513'
      }}>
      <Sidebar />
      <MealPlanFrame />
    </View>
  );
};

export default HomeScreen;