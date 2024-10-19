import React, { useEffect, useState } from 'react';
import { StyleSheet, Text, View, TouchableOpacity, Image } from 'react-native';
import * as WebBrowser from 'expo-web-browser';
import * as Google from 'expo-auth-session/providers/google';
import * as Facebook from 'expo-auth-session/providers/facebook';

WebBrowser.maybeCompleteAuthSession();

// Define types for user info
interface UserInfo {
  name: string;
  email: string;
  picture?: string | { data: { url: string } };
}

// Replace with your Google and Facebook OAuth client IDs
const GOOGLE_CLIENT_ID = 'YOUR_GOOGLE_CLIENT_ID.apps.googleusercontent.com';
const FACEBOOK_APP_ID = 'YOUR_FACEBOOK_APP_ID';

const LoginScreen: React.FC = () => {
  const [userInfo, setUserInfo] = useState<UserInfo | null>(null);

  // Google Auth Request
  const [googleRequest, googleResponse, googlePromptAsync] = Google.useAuthRequest({
    clientId: GOOGLE_CLIENT_ID,
    redirectUri: 'https://auth.expo.io/@yourusername/your-app-slug', // Replace with your Expo username and app slug
  });

  // Facebook Auth Request
  const [facebookRequest, facebookResponse, facebookPromptAsync] = Facebook.useAuthRequest({
    clientId: FACEBOOK_APP_ID,
  });

  useEffect(() => {
    if (googleResponse?.type === 'success') {
      const { authentication } = googleResponse;
      getUserInfo('google', authentication?.accessToken);
    }

    if (facebookResponse?.type === 'success') {
      const { authentication } = facebookResponse;
      getUserInfo('facebook', authentication?.accessToken);
    }
  }, [googleResponse, facebookResponse]);

  // Fetch user info from Google/Facebook
  const getUserInfo = async (provider: 'google' | 'facebook', token: string | undefined) => {
    let userInfoUrl = provider === 'google'
      ? 'https://www.googleapis.com/oauth2/v3/userinfo'
      : `https://graph.facebook.com/me?fields=id,name,picture,email&access_token=${token}`;

    try {
      const response = await fetch(userInfoUrl);
      const user: UserInfo = await response.json();
      setUserInfo(user);
    } catch (error) {
      console.log('Failed to fetch user info', error);
    }
  };

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Login to Menu Project</Text>

      {!userInfo ? (
        <View style={styles.authOptions}>
          <TouchableOpacity style={styles.loginButton} onPress={() => googlePromptAsync()}>
            <Text style={styles.loginText}>Login with Google</Text>
          </TouchableOpacity>

          <TouchableOpacity style={styles.loginButton} onPress={() => facebookPromptAsync()}>
            <Text style={styles.loginText}>Login with Facebook</Text>
          </TouchableOpacity>
        </View>
      ) : (
        <View style={styles.userInfo}>
          <Image source={{ uri: userInfo.picture?.data?.url || userInfo.picture }} style={styles.profilePic} />
          <Text>Welcome, {userInfo.name}!</Text>
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
