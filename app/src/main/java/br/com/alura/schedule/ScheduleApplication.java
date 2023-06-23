package br.com.alura.schedule;

import android.app.Application;

import br.com.alura.schedule.database.dao.RoomStudentDAO;
import br.com.alura.schedule.models.Student;
import br.com.alura.schedule.utils.RoomUtils;

public class ScheduleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        createTestStudents();
    }

    private void createTestStudents() {
        final RoomStudentDAO dao = RoomUtils.getDAO(this);
        dao.save(new Student("Avatar", "+55 11 11111 1111", "avatar@gmail.com"));
        dao.save(new Student("Marcos", "+55 22 22222 2222", "marcos@gmail.com"));
    }
}
