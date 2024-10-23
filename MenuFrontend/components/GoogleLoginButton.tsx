
import { Text, TouchableOpacity } from 'react-native';
import { GoogleAuthProvider, signInWithPopup } from "firebase/auth";
import auth from './FirebaseInit';


const GoogleLoginButton = ({ onChange }: { onChange: (User: any) => void }) => {

    const provider = new GoogleAuthProvider();
    provider.addScope('https://www.googleapis.com/auth/userinfo.profile');

    const googlePromptAsync = () => {
        signInWithPopup(auth, provider)
        .then((result) => {
            // This gives you a Google Access Token. You can use it to access the Google API.
            const credential = GoogleAuthProvider.credentialFromResult(result);
            const token = credential?.accessToken; 
            // The signed-in user info.
            const user = result.user;
            console.log(token);
            console.log(auth.currentUser?.getIdToken(true));
            onChange(user)
        }).catch((error) => {
            // Handle Errors here.
            const errorCode = error.code;
            const errorMessage = error.message;
            // The email of the user's account used.
            const email = error.customData.email;
            // The AuthCredential type that was used.
            const credential = GoogleAuthProvider.credentialFromError(error);
            // ...
        });
    }

return (
    <TouchableOpacity style={{
        backgroundColor: '#4285F4',
        padding: 10,
        borderRadius: 5,
        marginVertical: 10,
        width: 200,
        alignItems: 'center',
      }} onPress={() => googlePromptAsync()}>
    <Text style={{
        color: 'white',
        fontWeight: 'bold',
    }}>Login with Google</Text>
  </TouchableOpacity>
);
}

export default GoogleLoginButton;