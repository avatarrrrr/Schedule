package br.com.alura.schedule.database.converters;

import androidx.room.TypeConverter;

import br.com.alura.schedule.models.enums.TelephoneType;

public class TelephoneTypeConverter {
    @TypeConverter
    public String toString(TelephoneType value) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }

    @TypeConverter
    public TelephoneType toTelephoneType(String value) {
        if (value == null) {
            return TelephoneType.LANDLINE;
        }
        return TelephoneType.valueOf(value);
    }
}
