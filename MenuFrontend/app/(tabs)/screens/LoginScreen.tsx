import React, { useState } from 'react';
import { StyleSheet, Text, View, TouchableOpacity, Image, Button } from 'react-native';
import * as WebBrowser from 'expo-web-browser';
import GoogleLoginButton from '@/components/GoogleLoginButton';
import axios from 'axios';

WebBrowser.maybeCompleteAuthSession();

// Define types for user info
interface UserInfo {
  displayName: string;
  email: string;
  photoURL: string;
}


const LoginScreen: React.FC = () => {
  const [userInfo, setUserInfo] = useState<UserInfo | null>(null);

  // Fetch user info from Google/Facebook
  const getUserInfo = ( user: any) => {
    setUserInfo(user);
  };

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Login to Menu Project</Text>
      {!userInfo ? (
        <View style={styles.authOptions}>
          <GoogleLoginButton onChange={getUserInfo}></GoogleLoginButton>
        </View>
      ) : (
        <View style={styles.userInfo}>
          <Image source={{ uri: userInfo.photoURL }} style={styles.profilePic} />
          <Text>Welcome, {userInfo.displayName}!</Text>
          <Text>Email: {userInfo.email}</Text>
        </View>
      )}
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#fff',
    paddingTop: 40,
    paddingHorizontal: 20,
  },
  title: {
    fontSize: 24,
    fontWeight: 'bold',
    marginBottom: 20,
  },
  authOptions: {
    justifyContent: 'center',
    alignItems: 'center',
  },
  loginButton: {
    backgroundColor: '#4285F4',
    padding: 10,
    borderRadius: 5,
    marginVertical: 10,
    width: 200,
    alignItems: 'center',
  },
  loginText: {
    color: 'white',
    fontWeight: 'bold',
  },
  userInfo: {
    alignItems: 'center',
    marginTop: 20,
  },
  profilePic: {
    width: 100,
    height: 100,
    borderRadius: 50,
    marginBottom: 10,
  },
});

export default LoginScreen;
