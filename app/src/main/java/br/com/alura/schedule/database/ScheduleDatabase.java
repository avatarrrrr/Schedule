package br.com.alura.schedule.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import br.com.alura.schedule.database.dao.RoomStudentDAO;
import br.com.alura.schedule.models.Student;

@Database(version = 1, entities = {Student.class}, exportSchema = false)
public abstract class ScheduleDatabase extends RoomDatabase {
    public abstract RoomStudentDAO getDAO();
}
