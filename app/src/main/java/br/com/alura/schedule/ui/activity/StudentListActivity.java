package br.com.alura.schedule.ui.activity;

import static br.com.alura.schedule.ui.activity.ConstantsActivities.STUDENT_KEY;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import br.com.alura.schedule.R;
import br.com.alura.schedule.dao.StudentsDao;
import br.com.alura.schedule.models.Student;
import br.com.alura.schedule.ui.adapter.StudentsListAdapter;

public class StudentListActivity extends AppCompatActivity {
    final StudentsDao studentsDAO = new StudentsDao();
    private StudentsListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_list);
        setTitle("Student list 👇🏾");
        buttonAddPressBehavior();
        configList();
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
            confirmDeleteDialog(menuInfo);
        }
        return super.onContextItemSelected(item);
    }

    private void confirmDeleteDialog(final AdapterView.AdapterContextMenuInfo menuInfo) {
        new AlertDialog.Builder(StudentListActivity.this)
                .setTitle("Delete student")
                .setMessage("You are sure?")
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", (dialog, which) -> deleteItemOnList(adapter.getItem(menuInfo.position)))
                .show();
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
        adapter.update(studentsDAO.getStudents());
    }

    private void configList() {
        final ListView studentsView = findViewById(R.id.activity_students_list_listview);

        setAdapter(studentsView);
        setOnItemClickBehavior(studentsView);
        registerForContextMenu(studentsView);
    }

    private void setAdapter(ListView studentsView) {
        adapter = new StudentsListAdapter(this);
        studentsView.setAdapter(adapter);
    }

    private void deleteItemOnList(Student student) {
        studentsDAO.delete(student);
        adapter.remove(student);
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
