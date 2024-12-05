import { LinearGradient } from "expo-linear-gradient";
import { StyleSheet, ScrollView, TouchableOpacity, View, Text } from "react-native";
import MealCard from "./MealCard";
import { useCallback, useEffect, useMemo, useState } from "react";
import { MaterialIcons } from "@expo/vector-icons";
import { useUser } from "@/context/UserContext";
import React from "react";
import LoginButton from "./Buttons/LoginButton";
import { MenuItem, Menu } from "./Interfaces/ICommon";
import { fetchMenu } from "@/hooks/useMealAPI";
import MealTypesEnum from "./Enums/MealTypesEnum";

const MealPlanFrame: React.FC = () => {
  const { isAuthenticated, token } = useUser();
  const [showFullWeek, setShowFullWeek] = useState(false);
  const [menu, setMenu] = useState<Menu[]>([]);

  useEffect(() => {
    if (isAuthenticated) {
      refreshPage();
    }
  }, [isAuthenticated, token, showFullWeek]);
  

  const refreshPage = () => {
    if(isAuthenticated && token) {
      const today = new Date();
      const startDate = showFullWeek ? new Date(today.setDate(today.getDate() - today.getDay()+1)) : today;
      const endDate = new Date();
      endDate.setDate(startDate.getDate() + (showFullWeek ? 6 : 0));
      fetchMenu(token, startDate, endDate).then((fetchedMenu) => {
        if(fetchedMenu){
          setMenu(fetchedMenu as Menu[]);
        }
        
      }).catch((error) => {
        console.log('error fetching menu', error);
        setMenu([]);
      });
    }
  }
    
  const getDates = useCallback(() => {
    const dates = [];
    const today = new Date();
    const currentDay = showFullWeek ? new Date(today.setDate(today.getDate() - today.getDay()+1)) : today;
    
    for (let i = 0; i < (showFullWeek ? 7 : 1); i++) {
      const date = new Date(currentDay);
      date.setDate(currentDay.getDate() + i);
      dates.push(date);
    }
    return dates;
  }, [showFullWeek]);
  
  const getMenuItems = (date: Date) => {
    if(menu.length > 0) {
    const menuItems = menu.filter((singleMenu) => {return singleMenu.day.toString() === date.toISOString().split("T")[0].toString() })[0]?.menuItems || [];
    return menuItems;
    }
    return [];
  };

  const getMealsTypes = Object.values(MealTypesEnum)
    
  return (
      <> 
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
              {isAuthenticated ? (
                <>
                  {getDates().map((date, index) => (
                    <React.Fragment key={index}>
                      <MealCard
                      key={index}
                      date={date}
                      menu={menu.filter((singleMenu) => {return singleMenu.day.toString() === date.toISOString().split("T")[0].toString() })[0] || {day: date, menuItems: []}}
                      mealTypes={getMealsTypes}
                      onAddMenuItem={() => refreshPage()}
                      index={index}
                      isToday={date.toDateString() === new Date().toDateString()}
                      showFullWeek={showFullWeek}
                      />
                    </React.Fragment>
                  ))}
                </>
              ) : (
                <>
                  <Text>In order to use an app, please log in</Text>
                  <LoginButton loginMethod="google"/>
                </>
              )}
              
            </ScrollView>
        </View>

        {/* Wooden Frame Bottom */}
        <LinearGradient
            colors={['#8B4513', '#A0522D', '#8B4513']}
            style={styles.footer}
        />
    </>
  );
};

const styles = StyleSheet.create({
  header: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    padding: 20,
    paddingTop: 40,
    boxShadow: '0px 2px 4px rgba(0, 0, 0, 0.3)',
  },
  title: {
    fontSize: 24,
    fontWeight: 'bold',
    color: '#FFF8F0',
  },
  weekToggle: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 8,
    padding: 8,
    backgroundColor: '#FFF8F0',
    borderRadius: 20,
    boxShadow: '0px 1px 2px rgba(0, 0, 0, 0.2)',
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
    boxShadow: '0px -2px 4px rgba(0, 0, 0, 0.3)',
  },
});

export default MealPlanFrame;