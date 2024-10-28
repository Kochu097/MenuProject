import React, { useState } from 'react';
import {
  StyleSheet,
  View,
  Text,
  TouchableOpacity,
  Animated,
  Image,
  Dimensions,
} from 'react-native';
import { useUser } from '@/context/UserContext';
import { MaterialIcons } from '@expo/vector-icons';

const Sidebar: React.FC = () => {

  const { userInfo, isAuthenticated, login, logout } = useUser();

  const [isOpen, setIsOpen] = useState(false);
  const slideAnim = useState(new Animated.Value(-300))[0];

  const toggleSidebar = () => {
    const toValue = isOpen ? -300 : 0;
    Animated.timing(slideAnim, {
      toValue,
      duration: 300,
      useNativeDriver: true,
    }).start();
    setIsOpen(!isOpen);
  };

  return (
    <>
      {/* Hamburger Menu Button */}
      <TouchableOpacity
        style={styles.menuButton}
        onPress={toggleSidebar}
      >
        <MaterialIcons
          name={isOpen ? 'close' : 'menu'}
          size={24}
          color="#8B4513"
        />
      </TouchableOpacity>

      {/* Sidebar */}
      <Animated.View
        style={[
          styles.sidebar,
          {
            transform: [{ translateX: slideAnim }],
          },
        ]}
      >
        <View style={styles.sidebarContent}>
          {!isAuthenticated ? (
            <View style={styles.authButtons}>
              <TouchableOpacity
                style={[styles.authButton, { backgroundColor: '#DB4437' }]}
                onPress={() => login?.('google')}
              >
                <Text style={styles.authButtonText}>Sign in with Google</Text>
              </TouchableOpacity>
              
              <TouchableOpacity
                style={[styles.authButton, { backgroundColor: '#4267B2' }]}
                onPress={() => login?.('facebook')}
              >
                <Text style={styles.authButtonText}>Sign in with Facebook</Text>
              </TouchableOpacity>
              
              <TouchableOpacity
                style={[styles.authButton, { backgroundColor: '#000000' }]}
                onPress={() => login?.('icloud')}
              >
                <Text style={styles.authButtonText}>Sign in with iCloud</Text>
              </TouchableOpacity>
            </View>
          ) : (
            <View style={styles.userInfo}>
              {userInfo?.photoURL && (
                <Image
                  source={{ uri: userInfo.photoURL }}
                  style={styles.profilePic}
                />
              )}
              <Text style={styles.userName}>{userInfo?.displayName}</Text>
              <Text style={styles.userEmail}>{userInfo?.email}</Text>
              <TouchableOpacity
                style={styles.logoutButton}
                onPress={logout}
              >
                <Text style={styles.logoutButtonText}>Sign Out</Text>
              </TouchableOpacity>
            </View>
          )}

          <View style={styles.welcomeBox}>
            <Text style={styles.welcomeText}>
              Welcome to your cozy meal planner! Start planning your delicious week ahead.
            </Text>
          </View>
        </View>
      </Animated.View>
    </>
  );
};

const styles = StyleSheet.create({
  menuButton: {
    position: 'absolute',
    top: 40,
    left: 20,
    zIndex: 100,
    padding: 10,
    borderRadius: 25,
    backgroundColor: '#FFF8F0',
  },
  sidebar: {
    position: 'absolute',
    left: 0,
    top: 0,
    bottom: 0,
    width: 300,
    backgroundColor: '#FFF8F0',
    zIndex: 99,
    elevation: 5,
    shadowColor: '#000',
    shadowOffset: {
      width: 2,
      height: 0,
    },
    shadowOpacity: 0.25,
    shadowRadius: 3.84,
  },
  sidebarContent: {
    paddingTop: 80,
    paddingHorizontal: 20,
  },
  authButtons: {
    gap: 12,
  },
  authButton: {
    padding: 12,
    borderRadius: 8,
    alignItems: 'center',
    marginBottom: 12,
  },
  authButtonText: {
    color: '#FFFFFF',
    fontWeight: 'bold',
  },
  userInfo: {
    alignItems: 'center',
    padding: 20,
    backgroundColor: '#E6CCB2',
    borderRadius: 12,
  },
  profilePic: {
    width: 80,
    height: 80,
    borderRadius: 40,
    marginBottom: 12,
  },
  userName: {
    fontSize: 18,
    fontWeight: 'bold',
    color: '#2C1810',
    marginBottom: 4,
  },
  userEmail: {
    fontSize: 14,
    color: '#8B4513',
    marginBottom: 12,
  },
  logoutButton: {
    backgroundColor: '#C17817',
    padding: 10,
    borderRadius: 8,
    width: '100%',
    alignItems: 'center',
  },
  logoutButtonText: {
    color: '#FFFFFF',
    fontWeight: 'bold',
  },
  welcomeBox: {
    marginTop: 24,
    padding: 16,
    backgroundColor: '#E6CCB2',
    borderRadius: 12,
  },
  welcomeText: {
    color: '#2C1810',
    fontSize: 14,
    textAlign: 'center',
    lineHeight: 20,
  },
});

export default Sidebar;