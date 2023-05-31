package br.com.alura.schedule.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import br.com.alura.schedule.R;
import br.com.alura.schedule.dao.StudentsDao;
import br.com.alura.schedule.models.Student;

public class StudentListActivity extends AppCompatActivity {
    static String STUDENT_KEY = "student";
    final StudentsDao studentsDAO = new StudentsDao();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        setTitle("Student list 👇🏾");
        buttonAddPressBehavior();
        studentsDAO.save(new Student("Avatar", "+55 71 99733 3774", "avatar@gmail.com"));
        studentsDAO.save(new Student("Marcos", "+55 71 99733 3774", "marcosrios@gmail.com"));
    }

    private void buttonAddPressBehavior() {
        final View button = findViewById(R.id.activity_students_fab_add_student);
        button.setOnClickListener(v -> startActivity(new Intent(StudentListActivity.this, StudentFormActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        configList();
    }

    private void configList() {
        final List<Student> students = studentsDAO.getStudents();
        ListView studentsView = findViewById(R.id.activity_students_list_listview);
        studentsView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, students));
        studentsView.setOnItemClickListener((parent, view, position, id) -> {
            final Student student = students.get(position);
            Intent intentForm = new Intent(this, StudentFormActivity.class);
            intentForm.putExtra(STUDENT_KEY, student);
            startActivity(intentForm);
        });
    }
}
