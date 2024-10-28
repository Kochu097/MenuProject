export interface UserInfo {
    displayName: string;
    email: string;
    photoURL: string;
  };

export interface UserContextType {
    userInfo: UserInfo | null;
    loginSource: string | null;
    token: string | null;
    isAuthenticated: boolean;
    login: (source: string) => void;
    logout: () => void;
  }