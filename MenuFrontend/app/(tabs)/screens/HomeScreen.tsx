import { View } from 'react-native';
import Sidebar from '@/components/HomeScreenSidebar';
import MealPlanFrame from '@/components/MealPlanFrame';

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