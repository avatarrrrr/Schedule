package br.com.alura.schedule.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import br.com.alura.schedule.R;
import br.com.alura.schedule.database.dao.RoomStudentDAO;
import br.com.alura.schedule.models.Student;

@Database(version = 2, entities = {Student.class}, exportSchema = false)
public abstract class ScheduleDatabase extends RoomDatabase {
    private static ScheduleDatabase instance;

    public abstract RoomStudentDAO getDAO();

    public static ScheduleDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, ScheduleDatabase.class, context.getString(R.string.app_name) + ".db").allowMainThreadQueries().build();
        }

        return instance;
    }
}
