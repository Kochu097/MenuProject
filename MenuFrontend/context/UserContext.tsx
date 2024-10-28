import React, { createContext, useContext, useState, ReactNode } from 'react';
import { GoogleAuthProvider, getAuth, signInWithPopup, signOut } from 'firebase/auth';
import app from '@/components/FirebaseInit';
import { UserContextType, UserInfo } from './UserContextType';

// Create context with a default value
const UserContext = createContext<UserContextType | undefined>(undefined);

// Create provider component
export const UserProvider = ({ children }: { children: ReactNode }) => {
  const [userInfo, setUserInfo] = useState<UserInfo | null>(null);
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [token, setToken] = useState<string | null>(null);
  const [loginSource, setLoginSource] = useState<string | null>(null);

  const auth = getAuth(app);
  const provider = new GoogleAuthProvider();
  provider.addScope('https://www.googleapis.com/auth/userinfo.profile');

  const login = (source: string) => {
    if(source == 'google') {
      signInWithPopup(auth, provider)
      .then((result) => {
          const credential = GoogleAuthProvider.credentialFromResult(result);
          const token = credential?.accessToken;
          const user = result.user;

          setToken(credential?.accessToken != undefined ? credential?.accessToken : null);
          setLoginSource(source);
          setIsAuthenticated(true);
          setUserInfo({
            displayName: user.displayName != null ? user.displayName : '',
            email: user.email != null ? user.email : '',
            photoURL: user.photoURL != null ? user.photoURL : '',
          })
      }).catch((error) => {
          const errorCode = error.code;
          const errorMessage = error.message;
          const email = error.customData.email;

          const credential = GoogleAuthProvider.credentialFromError(error);
          setIsAuthenticated(false);
      });
    }
};

  const logout = () => {
    signOut(auth).then(() => {
        setUserInfo(null);
        setLoginSource(null);
        setToken(null);
        setIsAuthenticated(false);
      }).catch((error) => {
        // An error happened.
      });
  };

  const value = {
    userInfo,
    loginSource,
    token,
    isAuthenticated,
    login,
    logout
  };

  return (
    <UserContext.Provider value={value}>
      {children}
    </UserContext.Provider>
  );
};

export const useUser = () => {
    const context = useContext(UserContext);
    if (context === undefined) {
      throw new Error('useUser must be used within a UserProvider');
    }
    return context;
  };