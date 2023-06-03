package br.com.alura.schedule.dao;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import br.com.alura.schedule.models.Student;

public class StudentsDao {
    private final static List<Student> students = new ArrayList<>();
    private static int previousID = 1;

    public List<Student> getStudents() {
        return new ArrayList<>(students);
    }

    public void save(Student student) {
        student.setIdentifier(previousID);
        students.add(student);
        previousID++;
    }

    public void edit(Student student) {
        Student studentFound = findStudentByID(student);
        if (studentFound != null) {
            students.set(students.indexOf(studentFound), student);
        }
    }

    public void delete(Student student) {
        Student studentFound = findStudentByID(student);
        if (studentFound != null) {
            students.remove(studentFound);
        }
    }

    @Nullable
    private static Student findStudentByID(Student student) {
        Student studentFound = null;
        for (Student studentItem : students) {
            if (student.getIdentifier() == studentItem.getIdentifier()) {
                studentFound = studentItem;
            }
        }
        return studentFound;
    }

}
