package br.com.alura.schedule.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import br.com.alura.schedule.R;
import br.com.alura.schedule.database.converters.CalendarConverter;
import br.com.alura.schedule.database.converters.TelephoneTypeConverter;
import br.com.alura.schedule.database.dao.RoomStudentDAO;
import br.com.alura.schedule.database.dao.RoomTelephoneDAO;
import br.com.alura.schedule.database.migrations.Migrations;
import br.com.alura.schedule.models.Student;
import br.com.alura.schedule.models.Telephone;

@Database(version = 6, entities = {Student.class, Telephone.class}, exportSchema = false)
@TypeConverters({CalendarConverter.class, TelephoneTypeConverter.class})
public abstract class ScheduleDatabaseSingleton extends RoomDatabase {
    private static ScheduleDatabaseSingleton instance;

    public static ScheduleDatabaseSingleton getInstance(Context context) {
        if (instance == null) {
            Builder<ScheduleDatabaseSingleton> scheduleDatabaseBuilder = Room.databaseBuilder(context, ScheduleDatabaseSingleton.class, context.getString(R.string.app_name) + ".db");
            scheduleDatabaseBuilder.addMigrations(Migrations.all);
            instance = scheduleDatabaseBuilder.build();
        }

        return instance;
    }

    public abstract RoomStudentDAO getStudentDAO();

    public abstract RoomTelephoneDAO getTelephoneDAO();
}
