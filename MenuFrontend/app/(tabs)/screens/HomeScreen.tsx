import React, { useState, useCallback } from 'react';
import {
  StyleSheet,
  View,
  ScrollView,
  Text,
  TouchableOpacity,
  Dimensions,
} from 'react-native';
import { MaterialIcons } from '@expo/vector-icons';
import { LinearGradient } from 'expo-linear-gradient';
import Sidebar from '@/components/HomeScreenSidebar';
import MealCard from '@/components/MealCard';
import { googleOauth } from '@/hooks/useOauth';

const CORK_PATTERN = `
  <svg width="100" height="100" xmlns="http://www.w3.org/2000/svg">
    <filter id="noise">
      <feTurbulence type="fractalNoise" baseFrequency="0.8" numOctaves="4" stitchTiles="stitch"/>
    </filter>
    <rect width="100" height="100" filter="url(#noise)" opacity="0.4"/>
  </svg>
`;

const HomeScreen: React.FC = () => {
  const [showFullWeek, setShowFullWeek] = useState(true);
  
  const getDates = useCallback(() => {
    const dates = [];
    const today = new Date();
    const startOfWeek = new Date(today);
    startOfWeek.setDate(today.getDate() - today.getDay());
    
    for (let i = 0; i < (showFullWeek ? 7 : 1); i++) {
      const date = new Date(startOfWeek);
      date.setDate(startOfWeek.getDate() + i);
      dates.push(date);
    }
    return dates;
  }, [showFullWeek]);

  const getMealsForDate = (date: Date) => {
    return [
      { type: 'Breakfast', name: 'Oatmeal with berries' },
      { type: 'Lunch', name: 'Grilled chicken salad' },
    ];
  };

  const handleAddMeal = (date: Date, mealType: string) => {
    console.log(`Adding meal for ${date.toDateString()}, ${mealType}`);
  };

  const login = (provider: string) => {
      if(provider == 'google') {
        googleOauth();
      }
  }

  return (
    <View style={styles.container}>
      <Sidebar isLoggedIn={false} userInfo={null} />
      
      {/* Wooden Frame Header */}
      <LinearGradient
        colors={['#8B4513', '#A0522D', '#8B4513']}
        style={styles.header}
      >
        <Text style={styles.title}>My Meal Plan</Text>
        <TouchableOpacity
          style={styles.weekToggle}
          onPress={() => setShowFullWeek(!showFullWeek)}
        >
          <MaterialIcons
            name={showFullWeek ? 'view-day' : 'view-week'}
            size={24}
            color="#8B4513"
          />
          <Text style={styles.weekToggleText}>
            {showFullWeek ? 'Show Today' : 'Show Week'}
          </Text>
        </TouchableOpacity>
      </LinearGradient>

      {/* Corkboard with texture */}
      <View style={styles.corkboard}>
        <View style={styles.corkTexture} />
        <ScrollView 
          contentContainerStyle={styles.cardGrid}
          showsVerticalScrollIndicator={false}
        >
          {getDates().map((date, index) => (
            <MealCard
              key={date.toISOString()}
              date={date}
              meals={getMealsForDate(date)}
              onAddMeal={(mealType) => handleAddMeal(date, mealType)}
              index={index} isToday={showFullWeek} showFullWeek={showFullWeek}            
              />
          ))}
        </ScrollView>
      </View>

      {/* Wooden Frame Bottom */}
      <LinearGradient
        colors={['#8B4513', '#A0522D', '#8B4513']}
        style={styles.footer}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#8B4513',
  },
  header: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    padding: 20,
    paddingTop: 40,
    elevation: 5,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.3,
    shadowRadius: 4,
  },
  title: {
    fontSize: 24,
    fontWeight: 'bold',
    color: '#FFF8F0',
    textShadowColor: 'rgba(0, 0, 0, 0.3)',
    textShadowOffset: { width: 1, height: 1 },
    textShadowRadius: 2,
  },
  weekToggle: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 8,
    padding: 8,
    backgroundColor: '#FFF8F0',
    borderRadius: 20,
    elevation: 2,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 1 },
    shadowOpacity: 0.2,
    shadowRadius: 2,
  },
  weekToggleText: {
    color: '#8B4513',
    fontWeight: '500',
  },
  corkboard: {
    flex: 1,
    backgroundColor: '#D4B483',
    position: 'relative',
  },
  corkTexture: {
    ...StyleSheet.absoluteFillObject,
    opacity: 0.1,
    backgroundColor: '#000',
  },
  cardGrid: {
    flexDirection: 'row',
    flexWrap: 'wrap',
    padding: 10,
    paddingTop: 20,
  },
  footer: {
    height: 20,
    elevation: 5,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: -2 },
    shadowOpacity: 0.3,
    shadowRadius: 4,
  },
});

export default HomeScreen;