package br.com.alura.schedule;

import android.app.Application;

import br.com.alura.schedule.dao.StudentsDao;
import br.com.alura.schedule.models.Student;

public class ScheduleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        createTestStudents();
    }

    private static void createTestStudents() {
        final StudentsDao studentsDAO = new StudentsDao();
        studentsDAO.save(new Student("Avatar", "+55 11 11111 1111", "avatar@gmail.com"));
        studentsDAO.save(new Student("Marcos", "+55 22 22222 2222", "marcos@gmail.com"));
    }
}
