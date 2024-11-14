import { useUser } from "@/context/UserContext";
import { TouchableOpacity, Text, StyleSheet } from "react-native";

interface LoginButtonProp {
    loginMethod: string,
}

const LoginButton: React.FC<LoginButtonProp> = ({loginMethod}) => {

    const { login } = useUser();
    
    switch(loginMethod) {
        case 'google':
            return(
                <TouchableOpacity
                style={[styles.authButton, { backgroundColor: '#DB4437' }]}
                onPress={() => login?.(loginMethod)}
                >
                    <Text style={styles.authButtonText}>Sign in with Google</Text>
                </TouchableOpacity>
            );
        case 'facebook':
            return(
                <TouchableOpacity
                style={[styles.authButton, { backgroundColor: '#4267B2' }]}
                onPress={() => login?.('facebook')}
                >
                    <Text style={styles.authButtonText}>Sign in with Facebook</Text>
                </TouchableOpacity>
            );
        case 'icloud':
            return(
                <TouchableOpacity
                style={[styles.authButton, { backgroundColor: '#000000' }]}
                onPress={() => login?.('icloud')}
                >
                    <Text style={styles.authButtonText}>Sign in with iCloud</Text>
                </TouchableOpacity>
            );
        default:
            return(
                <Text style={styles.authButtonText}>Incorrect login method</Text>
            );
    }
    
}
const styles = StyleSheet.create({
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
});

export default LoginButton;