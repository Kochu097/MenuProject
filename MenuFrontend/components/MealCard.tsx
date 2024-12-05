import React, { useEffect, useMemo } from 'react';
import { 
  StyleSheet, 
  View, 
  Text, 
  Animated,
  Pressable,
  ImageBackground
} from 'react-native';
import { LinearGradient } from 'expo-linear-gradient';
import AddMealDialog from './AddMealDialog/AddMealDialog';
import { Menu, MenuItem } from './Interfaces/ICommon';
import MealTypesEnum from './Enums/MealTypesEnum';

interface MealCardProps {
  date: Date;
  menu: Menu;
  mealTypes: string[];
  onAddMenuItem: () => void;
  index: number;
  isToday: boolean;
  showFullWeek: boolean;
}

console.log('rendering MealCard');


const PIN_COLORS = [
  ['#FF4B4B', '#CC3333'],
  ['#4B8BFF', '#3366CC'],
  ['#FFD84B', '#CCA833'],
  ['#4BFF4B', '#33CC33'],
];

const PAPER_COLORS = [
  '#FFF9E5',
  '#FFF5E1',
  '#FFEDD6',
];

const MealCard: React.FC<MealCardProps> = ({ 
  date, 
  menu, 
  mealTypes,
  onAddMenuItem: onAddMenuItem, 
  index,
  isToday,
  showFullWeek
}) => {
  // Animations
  const scaleAnim = React.useRef(new Animated.Value(1)).current;
  const rotateAnim = React.useRef(new Animated.Value(0)).current;
  const floatAnim = React.useRef(new Animated.Value(0)).current;

  // Get random variations based on index
  
  const paperColorIndex = index % PAPER_COLORS.length;
  const pinColorIndex = index % PIN_COLORS.length;
  const [pinMainColor, pinShadowColor] = PIN_COLORS[pinColorIndex];

  const baseRotation = ((index % 3) - 1) * 2;

  // Calculate dimensions based on whether it's today's card in single view
  const cardWidth = showFullWeek ? '45%' : '80%';

  return (
    <View style={[
      styles.cardContainer,
      { width: cardWidth },
    ]}
    key={index}>
      {/* Enhanced pin with 3D effect */}
      <View style={styles.pinContainer}>
        <LinearGradient
          colors={[pinMainColor, pinShadowColor]}
          style={styles.pin}
        >
          <View style={styles.pinReflection} />
          <View style={styles.pinShadow} />
        </LinearGradient>
      </View>

      <Pressable
        style={[
          styles.card,
          { backgroundColor: PAPER_COLORS[paperColorIndex] },
          isToday && styles.todayCard,
        ]}
      >
        {/* Enhanced paper texture */}
        <ImageBackground
          style={styles.paperTexture}
        />
        
        {/* Coffee stain effect (random position) */}
        {index % 3 === 0 && (
          <View style={[
            styles.coffeeStain,
            { 
              right: `${20 + (index % 4) * 10}%`,
              bottom: `${10 + (index % 3) * 10}%` 
            }
          ]} />
        )}

        <View style={styles.dateHeader}>
          <Text style={styles.dayName}>
            {date.toLocaleDateString('en-US', { weekday: 'short' })}
          </Text>
          <Text style={styles.date}>
            {date.toLocaleDateString('en-US', { month: 'short', day: 'numeric' })}
          </Text>
          {isToday && <View style={styles.todayIndicator} />}
        </View>

        <View style={styles.mealsList}>
          {Object.values(MealTypesEnum).map((mealType, index) => { 
            const menuItems = menu.menuItems.filter(m => m.menuItemType === mealType);
            
            return (
              <View key={index} style={styles.mealItem}>
                <Text style={styles.mealType}>{mealType}</Text>
                  <View style={styles.mealNameContainer}>
                    {menuItems.map((menuItem, index) => (
                    <Text key={index} style={styles.mealName}>{menuItem ? (menuItem.product ? menuItem.product.name : menuItem.recipe?.name) : ''}</Text>
                    ))}
                    <View style={[
                      styles.underline,
                      { transform: [{ rotate: `${(index % 2) * 0.5}deg` }] }
                    ]} />
                  </View>
                
                  <AddMealDialog 
                  onAddMenuItem={onAddMenuItem}
                  date={date}/>
              </View>
            );
          })}
        </View>

        <View style={[
          styles.dogEar,
          { transform: [{ rotate: `${45 + (index % 5)}deg` }] }
        ]} />
        
        {/* Tape effect */}
        <View style={[
          styles.tape,
          { transform: [{ rotate: `${(index % 2) * 5}deg` }] }
        ]} />
      </Pressable>
    </View>
  );
};

const styles = StyleSheet.create({
  cardContainer: {
    margin: '2.5%',
    alignItems: 'center',
  },
  pinContainer: {
    zIndex: 1,
    elevation: 8,
  },
  pin: {
    width: 20,
    height: 20,
    borderRadius: 10,
    elevation: 8,
    boxShadow: '0px 2px 4px rgba(0, 0, 0, 0.5)',
  },
  pinReflection: {
    position: 'absolute',
    top: 3,
    left: 3,
    width: 6,
    height: 6,
    borderRadius: 3,
    backgroundColor: 'rgba(255, 255, 255, 0.6)',
  },
  pinShadow: {
    position: 'absolute',
    bottom: -2,
    left: 2,
    right: 2,
    height: 4,
    borderRadius: 2,
    backgroundColor: 'rgba(0, 0, 0, 0.2)',
    transform: [{ scaleX: 0.8 }],
  },
  card: {
    backgroundColor: '#FFF9E5',
    borderRadius: 8,
    padding: 12,
    width: '100%',
    marginTop: -10,
    elevation: 5,
    boxShadow: '2px 2px 4px rgba(0, 0, 0, 0.3)',
    overflow: 'hidden',
  },
  todayCard: {
    elevation: 8,
    boxShadow: '2px 2px 6px rgba(0, 0, 0, 0.4)',
    borderColor: '#8B4513',
    borderWidth: 1,
  },
  paperTexture: {
    ...StyleSheet.absoluteFillObject,
    opacity: 0.05,
  },
  coffeeStain: {
    position: 'absolute',
    width: 30,
    height: 30,
    borderRadius: 15,
    backgroundColor: '#8B451333',
    opacity: 0.1,
  },
  dateHeader: {
    borderBottomWidth: 1,
    borderBottomColor: '#D4B483',
    paddingBottom: 8,
    marginBottom: 8,
    alignItems: 'center',
    position: 'relative',
  },
  todayIndicator: {
    position: 'absolute',
    right: -5,
    top: '50%',
    width: 8,
    height: 8,
    borderRadius: 4,
    backgroundColor: '#FF4B4B',
    marginTop: -4,
  },
  dayName: {
    fontSize: 16,
    fontWeight: 'bold',
    color: '#8B4513',
  },
  date: {
    fontSize: 14,
    color: '#A67B5B',
  },
  mealsList: {
    gap: 8,
  },
  mealItem: {
    gap: 4,
  },
  mealType: {
    fontSize: 12,
    fontWeight: '600',
    color: '#6B4423',
  },
  mealNameContainer: {
    position: 'relative',
  },
  mealName: {
    fontSize: 14,
    color: '#2C1810',
    paddingBottom: 2,
  },
  underline: {
    position: 'absolute',
    bottom: 0,
    left: 0,
    right: 0,
    height: 1,
    backgroundColor: '#D4B483',
    opacity: 0.5,
  },
  addMealButton: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 4,
  },
  addMealText: {
    fontSize: 12,
    color: '#8B4513',
    fontStyle: 'italic',
  },
  dogEar: {
    position: 'absolute',
    top: 0,
    right: 0,
    width: 20,
    height: 20,
    backgroundColor: '#E6DFD4',
    transform: [
      { rotate: '45deg' },
      { translateX: 10 },
      { translateY: -10 },
    ],
  },
  tape: {
    position: 'absolute',
    top: -10,
    left: '50%',
    width: 40,
    height: 20,
    marginLeft: -20,
    backgroundColor: 'rgba(255, 255, 255, 0.6)',
    borderRadius: 2,
  },
});

export default MealCard;