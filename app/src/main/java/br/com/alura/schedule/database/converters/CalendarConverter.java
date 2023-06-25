package br.com.alura.schedule.database.converters;

import androidx.room.TypeConverter;

import java.util.Calendar;

public class CalendarConverter {
    @TypeConverter
    public Long toLong(Calendar value) {
        if (value != null) {
            return value.getTimeInMillis();
        }

        return null;
    }

    @TypeConverter
    public Calendar toCalendar(Long value) {
        final Calendar result = Calendar.getInstance();
        if (value != null) {
            result.setTimeInMillis(value);
        }
        return result;
    }
}
