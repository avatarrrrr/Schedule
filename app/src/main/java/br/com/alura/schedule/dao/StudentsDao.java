package br.com.alura.schedule.dao;

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
        students.forEach(studentItem -> {
            if(student.getIdentifier() == studentItem.getIdentifier()){
                students.set(students.indexOf(studentItem), student);
            }
        });
    }

}
