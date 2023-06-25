package br.com.alura.schedule.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import br.com.alura.schedule.R;
import br.com.alura.schedule.database.converters.CalendarConverter;
import br.com.alura.schedule.database.dao.RoomStudentDAO;
import br.com.alura.schedule.database.migrations.Migrations;
import br.com.alura.schedule.models.Student;

@Database(version = 4, entities = {Student.class}, exportSchema = false)
@TypeConverters({CalendarConverter.class})
public abstract class ScheduleDatabase extends RoomDatabase {
    private static ScheduleDatabase instance;

    public abstract RoomStudentDAO getDAO();

    public static ScheduleDatabase getInstance(Context context) {
        if (instance == null) {
            Builder<ScheduleDatabase> scheduleDatabaseBuilder = Room.databaseBuilder(context, ScheduleDatabase.class, context.getString(R.string.app_name) + ".db");
            scheduleDatabaseBuilder.allowMainThreadQueries();
            scheduleDatabaseBuilder.addMigrations(Migrations.all);
            instance = scheduleDatabaseBuilder.build();
        }

        return instance;
    }
}
