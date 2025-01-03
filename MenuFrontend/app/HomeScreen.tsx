import { View } from 'react-native';
import MealPlanFrame from '@/components/MealPlanFrame';

const HomeScreen: React.FC = () => {

  return (
    <View style={{
      flex: 1,
      backgroundColor: '#8B4513'
      }}
      testID='home-screen'>
      <MealPlanFrame />
    </View>
  );
};

export default HomeScreen;