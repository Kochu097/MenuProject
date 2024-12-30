import React, { createContext, useContext, useState, ReactNode } from 'react';
import { GoogleAuthProvider, getAuth, signInWithPopup, signOut, createUserWithEmailAndPassword, signInWithEmailAndPassword } from 'firebase/auth';
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
  const googleProvider = new GoogleAuthProvider();
  googleProvider.addScope('https://www.googleapis.com/auth/userinfo.profile');


  auth.onAuthStateChanged((user) => {
    if (user) {
      setIsAuthenticated(true);
      auth.currentUser?.getIdToken(false).then(function(tokenId) {
        setToken(tokenId);
      })
      setUserInfo({
        displayName: user.displayName != null ? user.displayName : '',
        email: user.email != null ? user.email : '',
        photoURL: user.photoURL != null ? user.photoURL : '',
      })
    } else {
      setIsAuthenticated(false);
      setToken(null);
      setUserInfo(null);
    }
  });

  const login = (source: string, email?: string, password?: string) => {
    if(source == 'google') {
      signInWithPopup(auth, googleProvider)
      .then((result) => {
          const credential = GoogleAuthProvider.credentialFromResult(result);
          const user = result.user;
          auth.currentUser?.getIdToken(true).then(function(tokenId) {
            setToken(tokenId);
          })
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
    } else if (source == 'register' && email && password) {
      createUserWithEmailAndPassword(auth, email, password)
      .then((userCredential) => {
        const user = userCredential.user;
        auth.currentUser?.getIdToken(true).then(function(tokenId) {
          setToken(tokenId);
        })
        setLoginSource(source);
        setIsAuthenticated(true);
        setUserInfo({
          displayName: user.displayName != null ? user.displayName : '',
          email: user.email != null ? user.email : '',
          photoURL: user.photoURL != null ? user.photoURL : '',
        })
      })
      .catch((error) => {
        const errorCode = error.code;
        const errorMessage = error.message;
      });
    } else if (source == 'login' && email && password) {
      signInWithEmailAndPassword(auth, email, password)
      .then((userCredential) => {
        const user = userCredential.user;
        auth.currentUser?.getIdToken(true).then(function(tokenId) {
          setToken(tokenId);
        })
        setLoginSource(source);
        setIsAuthenticated(true);
        setUserInfo({
          displayName: user.displayName != null ? user.displayName : '',
          email: user.email != null ? user.email : '',
          photoURL: user.photoURL != null ? user.photoURL : '',
        })
      })
      .catch((error) => {
        const errorCode = error.code;
        const errorMessage = error.message;
        console.log("Error Message: "+errorMessage);
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