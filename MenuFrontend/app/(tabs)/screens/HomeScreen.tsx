import React from 'react';
import { StyleSheet, Text, View, Button, TouchableOpacity } from 'react-native';
import { ScrollView } from 'react-native-gesture-handler';
import { NativeStackNavigationProp } from '@react-navigation/native-stack';

// Simple Menu Data for Today
const todayMenu = {
  breakfast: "Pancakes with syrup",
  dinner: "Grilled chicken with veggies",
  supper: "Spaghetti Bolognese"
};

interface Props {
  navigation: NativeStackNavigationProp<any>;
}

const HomeScreen: React.FC<Props> = ({ navigation }) => {
  return (
    <View style={styles.container}>
      {/* Header with Login Link */}
      <View style={styles.header}>
        <Text style={styles.title}>Menu Project</Text>
        <TouchableOpacity style={styles.loginLink}>
        <Button title="Login" onPress={() => navigation.navigate('Login')} />
        </TouchableOpacity>
      </View>

      {/* Menu Bar */}
      <View style={styles.menuBar}>
        <Button title="Show menu" onPress={() => alert('Show menu')} />
        <Button title="Add new meal" onPress={() => alert('Add new meal')} />
        <Button title="New meal plan" onPress={() => alert('New meal plan')} />
      </View>

      {/* Main Content: Today's Menu */}
      <ScrollView contentContainerStyle={styles.mainContent}>
        <Text style={styles.heading}>Today's Meal Menu</Text>
        <View style={styles.table}>
          <View style={styles.tableRow}>
            <Text style={styles.tableHeader}>Meal</Text>
            <Text style={styles.tableHeader}>Menu</Text>
          </View>
          <View style={styles.tableRow}>
            <Text style={styles.tableCell}>Breakfast</Text>
            <Text style={styles.tableCell}>{todayMenu.breakfast}</Text>
          </View>
          <View style={styles.tableRow}>
            <Text style={styles.tableCell}>Dinner</Text>
            <Text style={styles.tableCell}>{todayMenu.dinner}</Text>
          </View>
          <View style={styles.tableRow}>
            <Text style={styles.tableCell}>Supper</Text>
            <Text style={styles.tableCell}>{todayMenu.supper}</Text>
          </View>
        </View>
      </ScrollView>
    </View>
  );
}

// Styles
const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    paddingTop: 40,
    paddingHorizontal: 20,
  },
  header: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: 20,
  },
  title: {
    fontSize: 24,
    fontWeight: 'bold',
  },
  loginLink: {
    backgroundColor: '#3498db',
    padding: 10,
    borderRadius: 5,
  },
  loginText: {
    color: 'white',
    fontWeight: 'bold',
  },
  menuBar: {
    flexDirection: 'row',
    justifyContent: 'space-around',
    marginBottom: 20,
  },
  mainContent: {
    flexGrow: 1,
    alignItems: 'center',
  },
  heading: {
    fontSize: 22,
    fontWeight: 'bold',
    marginBottom: 10,
  },
  table: {
    width: '100%',
    borderWidth: 1,
    borderColor: '#ddd',
  },
  tableRow: {
    flexDirection: 'row',
    padding: 10,
    borderBottomWidth: 1,
    borderColor: '#ddd',
  },
  tableHeader: {
    flex: 1,
    fontWeight: 'bold',
  },
  tableCell: {
    flex: 1,
  },
});

export default HomeScreen;