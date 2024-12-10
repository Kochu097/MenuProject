import { LinearGradient } from "expo-linear-gradient";
import { StyleSheet, ScrollView, TouchableOpacity, View, Text } from "react-native";
import MealCard from "./MealCard";
import { useCallback, useEffect, useMemo, useState } from "react";
import { MaterialIcons } from "@expo/vector-icons";
import { useUser } from "@/context/UserContext";
import React from "react";
import LoginButton from "./Buttons/LoginButton";
import { Menu } from "./Interfaces/ICommon";
import { fetchMenu } from "@/hooks/useMealAPI";

interface SelectedDateRange {
  startDate: Date;
  endDate: Date;
  today: Date;
}

const MealPlanFrame: React.FC = () => {
  const { isAuthenticated, token } = useUser();
  const [showFullWeek, setShowFullWeek] = useState(false);
  const [menu, setMenu] = useState<Menu[]>([]);
  const [selectedDate, setSelectedDate] = useState<SelectedDateRange>({
    startDate: new Date(),
    endDate: new Date(),
    today: new Date(),
  });

  const dateArray = useMemo(() => {
    const dates = [];
    let currentDate = new Date(selectedDate.startDate);
    while (currentDate <= selectedDate.endDate) {
      dates.push(new Date(currentDate));
      currentDate.setDate(currentDate.getDate() + 1);
    }
    return dates;
  }, [selectedDate.startDate, selectedDate.endDate]);

  useEffect(() => {
    if (isAuthenticated) {
      refreshPage();
    }
  }, [isAuthenticated, token, showFullWeek, selectedDate]);

  const refreshPage = useCallback(() => {
    if (isAuthenticated && token) {
      fetchMenu(token, selectedDate.startDate, selectedDate.endDate)
        .then((fetchedMenu) => {
          if (fetchedMenu) {
            setMenu(fetchedMenu as Menu[]);
          }
        })
        .catch((error) => {
          console.log("error fetching menu", error);
          setMenu([]);
        });
    }
  }, [isAuthenticated, token, selectedDate.startDate, selectedDate.endDate]);

  useEffect(() => {
    const today = new Date();
    setSelectedDate({
      startDate: showFullWeek
        ? new Date(new Date().setDate(today.getDate() - today.getDay() + 1))
        : today,
      endDate: showFullWeek
        ? new Date(new Date().setDate(today.getDate() - today.getDay() + 7))
        : today,
      today: today,
    });
  }, [showFullWeek]);

  const moveIntime = useCallback((days: number) => {
    setSelectedDate((prev) => {
      const startDate = new Date(prev.startDate);
      startDate.setDate(prev.startDate.getDate() + days);
      const endDate = new Date(prev.endDate);
      endDate.setDate(prev.endDate.getDate() + days);
      return {
        startDate: startDate,
        endDate: endDate,
        today: prev.today,
      };
    });
  }, []);

  const getMenuItems = useCallback(
    (date: Date) => {
      return (
        menu.filter((singleMenu) => {
          return singleMenu.day.toString() === date.toISOString().split("T")[0].toString();
        })[0] || { day: date, menuItems: [] }
      );
    },
    [menu]
  );

  const memoizedMenuItems = useMemo(() => {
    return dateArray.map((date) => ({
      date,
      menuItems: getMenuItems(date),
    }));
  }, [dateArray, getMenuItems]);

  const onAddMenuItem = useCallback(() => {
    refreshPage();
  }, [refreshPage]);

  return (
    <>
      {/* Wooden Frame Header */}
      <LinearGradient colors={["#8B4513", "#A0522D", "#8B4513"]} style={styles.header}>
        <Text style={styles.title}>My Meal Plan</Text>
        <TouchableOpacity style={styles.weekToggle} onPress={() => setShowFullWeek(!showFullWeek)}>
          <MaterialIcons name={showFullWeek ? "view-day" : "view-week"} size={24} color="#8B4513" />
          <Text style={styles.weekToggleText}>{showFullWeek ? "Show Today" : "Show Week"}</Text>
        </TouchableOpacity>
      </LinearGradient>

      {/* Date Selector */}
      <View style={styles.dateSelector}>
        <TouchableOpacity onPress={() => moveIntime(showFullWeek ? -7 : -1)}>
          <MaterialIcons name="chevron-left" size={24} color="#8B4513" />
        </TouchableOpacity>
        <Text style={styles.dateText}>
          {showFullWeek
            ? `${selectedDate.startDate.toDateString()} - ${selectedDate.endDate.toDateString()}`
            : selectedDate.startDate.toDateString()}
        </Text>
        <TouchableOpacity onPress={() => moveIntime(showFullWeek ? 7 : 1)}>
          <MaterialIcons name="chevron-right" size={24} color="#8B4513" />
        </TouchableOpacity>
      </View>

      {/* Corkboard with texture */}
      <View style={styles.corkboard}>
        <View style={styles.corkTexture} />
        <ScrollView contentContainerStyle={styles.cardGrid} showsVerticalScrollIndicator={false}>
          {isAuthenticated ? (
            <>
              {memoizedMenuItems.map(({ menuItems, date }, index) => (
                <React.Fragment key={index}>
                  <MealCard
                    key={index}
                    date={date}
                    menu={menuItems}
                    onAddMenuItem={onAddMenuItem}
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
              <LoginButton loginMethod="google" />
            </>
          )}
        </ScrollView>
      </View>

      {/* Wooden Frame Bottom */}
      <LinearGradient colors={["#8B4513", "#A0522D", "#8B4513"]} style={styles.footer} />
    </>
  );
};

const styles = StyleSheet.create({
  header: {
    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "center",
    padding: 20,
    paddingTop: 40,
    boxShadow: "0px 2px 4px rgba(0, 0, 0, 0.3)",
  },
  title: {
    fontSize: 24,
    fontWeight: "bold",
    color: "#FFF8F0",
  },
  weekToggle: {
    flexDirection: "row",
    alignItems: "center",
    gap: 8,
    padding: 8,
    backgroundColor: "#FFF8F0",
    borderRadius: 20,
    boxShadow: "0px 1px 2px rgba(0, 0, 0, 0.2)",
  },
  weekToggleText: {
    color: "#8B4513",
    fontWeight: "500",
  },
  corkboard: {
    flex: 1,
    backgroundColor: "#D4B483",
    position: "relative",
  },
  corkTexture: {
    ...StyleSheet.absoluteFillObject,
    opacity: 0.1,
    backgroundColor: "#000",
  },
  cardGrid: {
    flexDirection: "row",
    flexWrap: "wrap",
    padding: 10,
    paddingTop: 20,
  },
  footer: {
    height: 20,
    boxShadow: "0px -2px 4px rgba(0, 0, 0, 0.3)",
  },
  dateSelector: {
    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "center",
    padding: 10,
    backgroundColor: "#FFF8F0",
    boxShadow: "0px 1px 2px rgba(0, 0, 0, 0.2)",
  },
  dateText: {
    fontSize: 16,
    fontWeight: "bold",
    color: "#8B4513",
  },
});

export default MealPlanFrame;