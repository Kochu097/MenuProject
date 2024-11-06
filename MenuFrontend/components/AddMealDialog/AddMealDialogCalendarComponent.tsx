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
          {WEEKDAYS.map(day => (
            <Text key={day} style={styles.weekDayText}>{day}</Text>
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
                <>
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
                </>
                
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
    addButton: {
      flexDirection: 'row',
      alignItems: 'center',
      gap: 4,
      padding: 8,
    },
    addButtonText: {
      fontSize: 12,
      color: '#8B4513',
      fontStyle: 'italic',
    },
    modal: {
      margin: 0,
      justifyContent: 'center',
      alignItems: 'center',
    },
    dialogContainer: {
      width: '90%',
      maxWidth: 500,
      backgroundColor: '#FFF9E5',
      borderRadius: 12,
      overflow: 'hidden',
      elevation: 8,
      shadowColor: '#000',
      shadowOffset: { width: 0, height: 2 },
      shadowOpacity: 0.25,
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
    section: {
      gap: 8,
    },
    label: {
      fontSize: 14,
      fontWeight: '600',
      color: '#6B4423',
    },
    dropdown: {
      flexDirection: 'row',
      justifyContent: 'space-between',
      alignItems: 'center',
      padding: 12,
      backgroundColor: '#FFF',
      borderRadius: 8,
      borderWidth: 1,
      borderColor: '#D4B483',
    },
    dropdownText: {
      fontSize: 16,
      color: '#8B4513',
    },
    dropdownList: {
      position: 'absolute',
      top: '100%',
      left: 0,
      right: 0,
      backgroundColor: '#FFF',
      borderRadius: 8,
      borderWidth: 1,
      borderColor: '#D4B483',
      marginTop: 4,
      zIndex: 1000,
    },
    dropdownItem: {
      padding: 12,
      borderBottomWidth: 1,
      borderBottomColor: '#D4B483',
    },
    dropdownItemText: {
      fontSize: 16,
      color: '#8B4513',
    },
    toggleContainer: {
      flexDirection: 'row',
      backgroundColor: '#D4B483',
      borderRadius: 8,
      padding: 4,
    },
    toggleButton: {
      flex: 1,
      padding: 8,
      alignItems: 'center',
      borderRadius: 6,
    },
    toggleButtonActive: {
      backgroundColor: '#FFF',
    },
    toggleButtonText: {
      fontSize: 14,
      color: '#8B4513',
      opacity: 0.7,
    },
    toggleButtonTextActive: {
      opacity: 1,
      fontWeight: '600',
    },
    searchContainer: {
      flexDirection: 'row',
      alignItems: 'center',
      backgroundColor: '#FFF',
      borderRadius: 8,
      padding: 8,
      borderWidth: 1,
      borderColor: '#D4B483',
    },
    searchInput: {
      flex: 1,
      marginLeft: 8,
      fontSize: 16,
      color: '#8B4513',
    },
    itemsList: {
      maxHeight: 300,
    },
    itemCard: {
      backgroundColor: '#FFF',
      borderRadius: 8,
      padding: 12,
      marginBottom: 8,
      borderWidth: 1,
      borderColor: '#D4B483',
    },
    itemCardSelected: {
      borderColor: '#8B4513',
      borderWidth: 2,
    },
    itemName: {
      fontSize: 16,
      fontWeight: '600',
      color: '#8B4513',
    },
    itemDescription: {
      fontSize: 14,
      color: '#A67B5B',
      marginTop: 4,
    },
    itemMeta: {
      marginTop: 8,
      flexDirection: 'row',
      alignItems: 'center',
    },
    itemMetaText: {
      fontSize: 12,
      color: '#6B4423',
      fontStyle: 'italic',
    },
    addMealButton: {
      backgroundColor: '#8B4513',
      borderRadius: 8,
      padding: 16,
      alignItems: 'center',
    },
    addMealButtonDisabled: {
      backgroundColor: '#D4B483',
    },
    addMealButtonText: {
      fontSize: 16,
      fontWeight: '600',
      color: '#FFF',
    }, mealTypeButtons: {
      flexDirection: 'row',
      flexWrap: 'wrap',
      gap: 8,
      marginTop: 8,
    },
    mealTypeButton: {
      paddingVertical: 8,
      paddingHorizontal: 16,
      borderRadius: 20,
      backgroundColor: '#FFF',
      borderWidth: 1,
      borderColor: '#D4B483',
      minWidth: 100,
      alignItems: 'center',
    },
    mealTypeButtonSelected: {
      backgroundColor: '#8B4513',
      borderColor: '#8B4513',
    },
    mealTypeButtonText: {
      fontSize: 14,
      color: '#8B4513',
      fontWeight: '500',
    },
    mealTypeButtonTextSelected: {
      color: '#FFF',
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