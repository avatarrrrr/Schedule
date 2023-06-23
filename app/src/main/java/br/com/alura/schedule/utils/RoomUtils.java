package br.com.alura.schedule.utils;

import android.content.Context;

import androidx.room.Room;

import br.com.alura.schedule.R;
import br.com.alura.schedule.database.ScheduleDatabase;
import br.com.alura.schedule.database.dao.RoomStudentDAO;

public class RoomUtils {
    public static RoomStudentDAO getDAO(Context context){
        ScheduleDatabase database = Room.databaseBuilder(context, ScheduleDatabase.class, context.getString(R.string.app_name) + ".db").allowMainThreadQueries().build();
        return database.getDAO();
    }
}
