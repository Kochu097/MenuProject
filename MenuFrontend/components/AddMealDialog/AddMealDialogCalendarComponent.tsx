import { MaterialIcons } from "@expo/vector-icons";
import { useState } from "react";
import { View, TouchableOpacity, Text, StyleSheet } from "react-native";


const WEEKDAYS = ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'];

interface CalendarComponentProp {
    onSelectedDate: (date: Date) => void,
    selectedDate: Date
}

const CalendarComponent: React.FC<CalendarComponentProp> = ({onSelectedDate, selectedDate}) => {

    // Get next few weeks for the expanded calendar
    const getWeeks = () => {
        const weeks = [];
        const today = new Date();
        const firstDay = new Date(today.getFullYear(), today.getMonth(), 1);
        const lastDay = new Date(today.getFullYear(), today.getMonth()+1, 1);
        const currentDay = new Date(firstDay);

        for (let week = 0; week < 5; week++) {
        const dates = [];
        while(dates.length < currentDay.getDay()-1) {
            dates.push(null);
        }
        
        do {
            const date = new Date(currentDay);
            dates.push(date);
            currentDay.setDate(currentDay.getDate()+1);
        } while(currentDay.getDay() != 1 && currentDay.getDate() != lastDay.getDate());

        while(dates.length != 7 ) {
            dates.push(null);
        }

        weeks.push(dates);
        }
        return weeks;
    };

    const isToday = (date: Date) => {
        const today = new Date();
        return date.toDateString() === today.toDateString();
    };

    const isSameDate = (date1: Date, date2: Date) => {
        return date1.toDateString() === date2.toDateString();
    };

    return (
        <View style={styles.calendarContainer}>
        <View style={styles.weekDaysHeader}>
          {WEEKDAYS.map((day, index) => (
            <Text key={index} style={styles.weekDayText}>{day}</Text>
          ))}
        </View>
        {getWeeks().map((week, weekIndex) => (
          <View key={weekIndex} style={styles.weekRow}>
            {week.map((date: Date | null, dateIndex) => {
  
              if(date == null) {
                return (
                <TouchableOpacity
                  key={dateIndex}
                  style={[
                    styles.dateCell
                  ]}
                ></TouchableOpacity>
              )
              }
              
              return (
                <TouchableOpacity
                  key={date.getDay()+dateIndex}
                  style={[
                    styles.dateCell,
                    isSameDate(date, selectedDate) && styles.selectedDate,
                    isToday(date) && styles.todayDate,
                  ]}
                  onPress={() => {
                    onSelectedDate(date);
                  }}
                >
                  <Text style={[
                    styles.dateCellText,
                    isSameDate(date, selectedDate) && styles.selectedDateText,
                    isToday(date) && styles.todayDateText,
                  ]}>
                    {date.getDate()}
                  </Text>
                </TouchableOpacity>
                
              )
        })}
          </View>
        ))}
      </View>
    );
}

interface DatePickerButtonProp {
    onSelectedDate: (date: Date) => void
    selectedDate: Date
}

const DatePickerButton: React.FC<DatePickerButtonProp> = ({onSelectedDate, selectedDate}) => {
    const [isCalendarOpen, setIsCalendarOpen] = useState(false);
    const formatDate = (date: Date) => {
      return date.toLocaleDateString('en-US', { 
        month: 'short', 
        day: 'numeric' 
      });
    };

    const handleOnSelectedDate = (date: Date) => {
        onSelectedDate(date);
        setIsCalendarOpen(false);
    };

    return (
        <View style={styles.section}>
            <Text style={styles.label}>Select Date</Text>
                <TouchableOpacity
                style={styles.datePickerButton}
                onPress={() => setIsCalendarOpen(!isCalendarOpen)}
                >
                    <MaterialIcons name="event" size={20} color="#8B4513" />
                    <Text style={styles.datePickerButtonText}>
                        {formatDate(selectedDate)}
                    </Text>
                    <MaterialIcons
                        name={isCalendarOpen ? 'expand-less' : 'expand-more'}
                        size={24}
                        color="#8B4513"
                    />
                </TouchableOpacity>
                {isCalendarOpen && <CalendarComponent onSelectedDate={handleOnSelectedDate} selectedDate={selectedDate} /> }
        </View>
    );
}

const styles = StyleSheet.create({
    section: {
      gap: 8,
    },
    label: {
      fontSize: 14,
      fontWeight: '600',
      color: '#6B4423',
    },
    datePickerButton: {
      flexDirection: 'row',
      alignItems: 'center',
      padding: 12,
      backgroundColor: '#FFF',
      borderRadius: 8,
      borderWidth: 1,
      borderColor: '#D4B483',
      gap: 8,
    },
    datePickerButtonText: {
      flex: 1,
      fontSize: 16,
      color: '#8B4513',
    },
    calendarContainer: {
      backgroundColor: '#FFF',
      borderRadius: 8,
      padding: 12,
      marginTop: 8,
      borderWidth: 1,
      borderColor: '#D4B483',
    },
    weekDaysHeader: {
      flexDirection: 'row',
      justifyContent: 'space-around',
      marginBottom: 8,
      borderBottomWidth: 1,
      borderBottomColor: '#D4B483',
      paddingBottom: 8,
    },
    weekDayText: {
      fontSize: 12,
      color: '#6B4423',
      fontWeight: '600',
      width: 36,
      textAlign: 'center',
    },
    weekRow: {
      flexDirection: 'row',
      justifyContent: 'space-around',
      marginBottom: 8,
    },
    dateCell: {
      width: 36,
      height: 36,
      justifyContent: 'center',
      alignItems: 'center',
      borderRadius: 18,
    },
    dateCellText: {
      fontSize: 14,
      color: '#8B4513',
    },
    selectedDate: {
      backgroundColor: '#8B4513',
    },
    selectedDateText: {
      color: '#FFF',
      fontWeight: '600',
    },
    todayDate: {
      backgroundColor: '#D4B483',
    },
    todayDateText: {
      fontWeight: '600',
    },
  });

export default DatePickerButton;