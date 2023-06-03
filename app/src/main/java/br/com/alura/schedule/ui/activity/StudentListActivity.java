package br.com.alura.schedule.ui.activity;

import static br.com.alura.schedule.ui.activity.ConstantsActivities.STUDENT_KEY;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import br.com.alura.schedule.R;
import br.com.alura.schedule.dao.StudentsDao;
import br.com.alura.schedule.models.Student;

public class StudentListActivity extends AppCompatActivity {
    final StudentsDao studentsDAO = new StudentsDao();
    ArrayAdapter<Student> listViewAdapter = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_list);
        setTitle("Student list 👇🏾");
        buttonAddPressBehavior();
        configList();
        studentsDAO.save(new Student("Avatar", "+55 71 99733 3774", "avatar@gmail.com"));
        studentsDAO.save(new Student("Marcos", "+55 71 99733 3774", "marcosrios@gmail.com"));
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.activity_students_list_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.activity_students_list_menu_delete) {
            final AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            deleteItemOnList(listViewAdapter.getItem(menuInfo.position));
        }
        return super.onContextItemSelected(item);
    }

    private void buttonAddPressBehavior() {
        final View button = findViewById(R.id.activity_students_fab_add_student);
        button.setOnClickListener(v -> startActivity(new Intent(StudentListActivity.this, StudentFormActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateAdapter();
    }

    private void updateAdapter() {
        listViewAdapter.clear();
        listViewAdapter.addAll(studentsDAO.getStudents());
    }

    private void configList() {
        final ListView studentsView = findViewById(R.id.activity_students_list_listview);

        listViewAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

        studentsView.setAdapter(listViewAdapter);
        setOnItemClickBehavior(studentsView);
        registerForContextMenu(studentsView);
    }

    private void deleteItemOnList(Student student) {
        studentsDAO.delete(student);
        listViewAdapter.remove(student);
    }

    private void setOnItemClickBehavior(ListView studentsView) {
        studentsView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            final Student student = (Student) parent.getItemAtPosition(position);
            goToFormsOnEditMode(student);
        });
    }

    private void goToFormsOnEditMode(Student student) {
        Intent intentForm = new Intent(this, StudentFormActivity.class);
        intentForm.putExtra(STUDENT_KEY, student);
        startActivity(intentForm);
    }
}
