import React, { useEffect, useState } from 'react';
import { View, Text, TouchableOpacity, StyleSheet } from 'react-native';

interface TimePickerProps {
    preparationTime: string;
    setPreparationTime: (time: string) => void;
}

const TimePicker: React.FC<TimePickerProps> = ({ preparationTime, setPreparationTime }) => {
    const [hours, setHours] = useState(parseInt(preparationTime.split(':')[0]));
    const [minutes, setMinutes] = useState(parseInt(preparationTime.split(':')[1]));

    const handleIncreaseHours = () => {
        setHours((prev) => (prev + 1) % 24);
    };

    const handleDecreaseHours = () => {
        setHours((prev) => (prev - 1 + 24) % 24);
    };

    const handleIncreaseMinutes = () => {
        setMinutes((prev) => (prev + 1) % 60);
    };

    const handleDecreaseMinutes = () => {
        setMinutes((prev) => (prev - 1 + 60) % 60);
    };

    useEffect(() => {
        setPreparationTime(`${String(hours).padStart(2, '0')}:${String(minutes).padStart(2, '0')}`);
    }, [hours, minutes]);

    return (
        <View style={styles.timePicker}>
        <View style={styles.timePickerColumn}>
            <TouchableOpacity onPress={handleIncreaseHours} style={styles.timePickerButton}>
            <Text style={styles.timePickerButtonText}>+</Text>
            </TouchableOpacity>
            <Text style={styles.timePickerValue}>{String(hours).padStart(2, '0')}</Text>
            <TouchableOpacity onPress={handleDecreaseHours} style={styles.timePickerButton}>
            <Text style={styles.timePickerButtonText}>-</Text>
            </TouchableOpacity>
        </View>
        <Text style={styles.timePickerSeparator}>:</Text>
        <View style={styles.timePickerColumn}>
            <TouchableOpacity onPress={handleIncreaseMinutes} style={styles.timePickerButton}>
            <Text style={styles.timePickerButtonText}>+</Text>
            </TouchableOpacity>
            <Text style={styles.timePickerValue}>{String(minutes).padStart(2, '0')}</Text>
            <TouchableOpacity onPress={handleDecreaseMinutes} style={styles.timePickerButton}>
            <Text style={styles.timePickerButtonText}>-</Text>
            </TouchableOpacity>
        </View>
        </View>
    );
};

const styles = StyleSheet.create({
timePicker: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
},
timePickerColumn: {
    alignItems: 'center',
},
timePickerButton: {
    padding: 8,
    backgroundColor: '#FFF',
    borderRadius: 8,
    borderWidth: 1,
    borderColor: '#D4B483',
    marginBottom: 4,
},
timePickerButtonText: {
    fontSize: 16,
    color: '#8B4513',
},
timePickerValue: {
    fontSize: 16,
    color: '#8B4513',
    marginVertical: 4,
},
timePickerSeparator: {
    fontSize: 16,
    color: '#8B4513',
    marginHorizontal: 8,
},
});

export default TimePicker;