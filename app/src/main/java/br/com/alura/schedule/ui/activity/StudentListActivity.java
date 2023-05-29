package br.com.alura.schedule.ui.activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

import br.com.alura.schedule.R;

public class StudentListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        setTitle("Student list 👇🏾");

        List<String> students = Arrays.asList(new String[]{"Avatar", "Aninha", "Vini"});

        ListView studentsView = findViewById(R.id.activity_students_list_listview);
        studentsView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, students));
    }
}
