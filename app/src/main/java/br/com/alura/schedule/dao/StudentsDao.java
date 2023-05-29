package br.com.alura.schedule.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.alura.schedule.models.Student;

public class StudentsDao {
    private final static List<Student> students = new ArrayList<>();

    public void save(Student student) {
        students.add(student);
    }

    public List<Student> getStudents() {
        return new ArrayList<>(students);
    }
}
