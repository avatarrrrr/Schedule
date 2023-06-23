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
import androidx.appcompat.app.AppCompatActivity;

import br.com.alura.schedule.R;
import br.com.alura.schedule.models.Student;
import br.com.alura.schedule.ui.activity.controller.StudentsListActivityController;

public class StudentListActivity extends AppCompatActivity {
    private StudentsListActivityController studentsListActivityController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_list);
        studentsListActivityController = new StudentsListActivityController(this);
        setTitle(getString(R.string.students_list_activity_title));
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
            studentsListActivityController.confirmDeleteDialog(menuInfo);
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
        studentsListActivityController.updateAdapter();
    }

    private void configList() {
        final ListView studentsView = findViewById(R.id.activity_students_list_listview);

        studentsListActivityController.setAdapter(studentsView);
        setOnItemClickBehavior(studentsView);
        registerForContextMenu(studentsView);
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
