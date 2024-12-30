import React, { memo, useMemo, useState } from 'react';
import LoginButton from './Buttons/LoginButton';
import { useUser } from '@/context/UserContext';
import { StyleSheet, Image, TouchableOpacity, View, Text} from "react-native";
import { ThemedText } from './ThemedText';
import { ThemedView } from './ThemedView';
import { MaterialIcons } from '@expo/vector-icons';
import LoginDialog from './LoginDialog/LoginDialog';

interface UserComponentProps {
  isAuthenticated: boolean, 
  profilePicture : String | undefined, 
  userName: String | undefined, 
  logout: () => void
}

const UserComponent: React.FC<UserComponentProps> = ({isAuthenticated, profilePicture, userName, logout}) => {

  const [dropdownVisible, setDropdownVisible] = useState(false);
  const [LoginDialogVisible, setLoginDialogVisible] = useState(false);

    const toggleDropdown = () => {
        setDropdownVisible(!dropdownVisible);
      };
    
    const handleLogout = () => {
        setDropdownVisible(false);
        logout();
      }

    const memoizedComponent = useMemo(() => {
      if (!isAuthenticated) {
          return <LoginDialog />
      }
      return (
          <View style={styles.container}>
              <TouchableOpacity onPress={toggleDropdown}>
                  <ThemedView style={styles.userInfoContainer}>
                      {profilePicture && (
                          <Image
                              source={{ uri: profilePicture?.toString() }}
                              alt={`${userName}'s profile`}
                              style={styles.profileImage}
                          />
                      )}
                      <ThemedText type="default">Hello {userName?.split(" ")[0] || 'User'}</ThemedText>
                      <MaterialIcons name="arrow-drop-down" size={24} color="black" />
                  </ThemedView>
              </TouchableOpacity>
              {dropdownVisible && (
                  <ThemedView style={styles.dropdown}>
                      <TouchableOpacity onPress={handleLogout}>
                          <ThemedText type="default" style={styles.logoutText}>Logout</ThemedText>
                      </TouchableOpacity>
                  </ThemedView>
              )}
          </View>
      );
    }, [isAuthenticated, profilePicture, userName, dropdownVisible]);

    return memoizedComponent;
};

const styles = StyleSheet.create({
  container: {
    position: 'relative',
  },
  userInfoContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: 'invisible',
  },
  profileImage: {
    width: 40,
    height: 40,
    borderRadius: 20,
  },
  dropdown: {
    position: 'absolute',
    top: 50,
    right: 0,
    backgroundColor: '#FFF8F0',
    padding: 10,
    borderRadius: 5,
    borderWidth: 1,
    borderColor: '#ccc',
    boxShadow: '0px 2px 4px rgba(0, 0, 0, 0.2)',
  },
  logoutText: {
    color: 'black',
  }
});

export default memo(UserComponent);