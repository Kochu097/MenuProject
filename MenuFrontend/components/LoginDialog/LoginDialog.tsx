import React, { useState } from 'react';
import { StyleSheet, View, Text, TouchableOpacity, TextInput } from 'react-native';
import Modal from 'react-native-modal';
import { MaterialIcons } from '@expo/vector-icons';
import { LinearGradient } from 'expo-linear-gradient';
import LoginButton from '../Buttons/LoginButton';
import { useUser } from '@/context/UserContext';

const LoginDialog: React.FC = () => {
    const [isVisible, setIsVisible] = useState(false);
    const [isRegister, setIsRegister] = useState(false);
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const { login } = useUser();

    const handleLogin = () => {
        login('login', email, password);
        setIsVisible(false);
    };

    const handleRegister = () => {
        // Handle registration logic here
        if (password === confirmPassword) {
            login('register', email, password);
            setIsVisible(false);
        } else {
            // Handle password mismatch
        }
    };

    return (
        <>
            <TouchableOpacity
                style={styles.authButton}
                onPress={() => setIsVisible(true)}
            >
                <Text style={styles.authButtonText}>Login / Register</Text>
            </TouchableOpacity>

            <Modal
                isVisible={isVisible}
                onBackdropPress={() => setIsVisible(false)}
                backdropOpacity={0.5}
                deviceHeight={Number.POSITIVE_INFINITY}
                animationIn="fadeIn"
                animationOut="fadeOut"
                style={styles.modal}
            >
                <View style={styles.dialogContainer}>
                    <LinearGradient
                        colors={['#8B4513', '#A0522D', '#8B4513']}
                        style={styles.header}
                    >
                        <Text style={styles.title}>{isRegister ? 'Register' : 'Login'}</Text>
                        <TouchableOpacity
                            onPress={() => setIsVisible(false)}
                            style={styles.closeButton}
                        >
                            <MaterialIcons name="close" size={24} color="#FFF8F0" />
                        </TouchableOpacity>
                    </LinearGradient>

                    <View style={styles.content}>
                        <TextInput
                            style={styles.input}
                            placeholder="Email"
                            value={email}
                            onChangeText={setEmail}
                            keyboardType="email-address"
                            autoCapitalize="none"
                        />
                        <TextInput
                            style={styles.input}
                            placeholder="Password"
                            value={password}
                            onChangeText={setPassword}
                            secureTextEntry
                        />
                        {isRegister && (
                            <TextInput
                                style={styles.input}
                                placeholder="Confirm Password"
                                value={confirmPassword}
                                onChangeText={setConfirmPassword}
                                secureTextEntry
                            />
                        )}

                        <TouchableOpacity
                            style={styles.authButton}
                            onPress={isRegister ? handleRegister : handleLogin}
                        >
                            <Text style={styles.authButtonText}>{isRegister ? 'Register' : 'Login'}</Text>
                        </TouchableOpacity>

                        <Text style={styles.orText}>OR</Text>

                        <LoginButton loginMethod="google" />

                        <TouchableOpacity
                            onPress={() => setIsRegister(!isRegister)}
                            style={styles.switchButton}
                        >
                            <Text style={styles.switchButtonText}>
                                {isRegister ? 'Already have an account? Login' : "Don't have an account? Register"}
                            </Text>
                        </TouchableOpacity>
                    </View>
                </View>
            </Modal>
        </>
    );
};

const styles = StyleSheet.create({
    authButton: {
        padding: 12,
        borderRadius: 8,
        alignItems: 'center',
        marginBottom: 12,
        backgroundColor: '#8B4513',
    },
    authButtonText: {
        color: '#FFFFFF',
        fontWeight: 'bold',
    },
    modal: {
        margin: 0,
        justifyContent: 'center',
        alignItems: 'center',
        width: '100%',
    },
    dialogContainer: {
        width: '90%',
        maxWidth: 500,
        backgroundColor: '#FFF9E5',
        borderRadius: 12,
        overflow: 'hidden',
        elevation: 8,
        boxShadow: '0 2px 4px rgba(0, 0, 0, 0.25)',
    },
    header: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
        padding: 16,
    },
    title: {
        fontSize: 20,
        fontWeight: 'bold',
        color: '#FFF8F0',
    },
    closeButton: {
        padding: 4,
    },
    content: {
        padding: 16,
        gap: 16,
    },
    input: {
        backgroundColor: '#FFF',
        borderRadius: 8,
        padding: 12,
        borderWidth: 1,
        borderColor: '#D4B483',
    },
    orText: {
        textAlign: 'center',
        marginVertical: 8,
        color: '#8B4513',
    },
    switchButton: {
        marginTop: 16,
        alignItems: 'center',
    },
    switchButtonText: {
        color: '#8B4513',
        textDecorationLine: 'underline',
    },
});

export default LoginDialog;