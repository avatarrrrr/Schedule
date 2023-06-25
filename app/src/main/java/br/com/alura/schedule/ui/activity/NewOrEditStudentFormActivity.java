package br.com.alura.schedule.ui.activity;

import static br.com.alura.schedule.ui.activity.constants.ConstantsActivities.STUDENT_KEY;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import br.com.alura.schedule.R;
import br.com.alura.schedule.database.ScheduleDatabase;
import br.com.alura.schedule.database.dao.RoomStudentDAO;
import br.com.alura.schedule.models.Student;

public class NewOrEditStudentFormActivity extends AppCompatActivity {
    private Student student = null;
    private RoomStudentDAO dao;
    private EditText nameEditText;
    private EditText phoneEditText;
    private EditText emailEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_or_edit_student_form);
        dao = ScheduleDatabase.getInstance(this).getDAO();
        initializeViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_student_form_menu_options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.activity_student_form_menu_save) {
            save();
        }
        return super.onOptionsItemSelected(item);
    }

    private void save() {
        fillStudent();
        if (student.hasValidIdentifier()) {
            dao.edit(student);
        } else {
            dao.save(student);
        }
        finish();
    }

    private void fillStudent() {
        student.setName(nameEditText.getText().toString());
        student.setTelephone(phoneEditText.getText().toString());
        student.setEmail(emailEditText.getText().toString());
    }

    private void initializeViews() {
        nameEditText = findViewById(R.id.activity_student_form_name);
        phoneEditText = findViewById(R.id.activity_student_form_telephone);
        emailEditText = findViewById(R.id.activity_student_form_email);

        isEdit();
    }

    private void isEdit() {
        Intent intent = getIntent();

        if (intent.hasExtra(STUDENT_KEY)) {
            setTitle(getString(R.string.new_or_edit_student_form_activity_title_edit));
            student = intent.getExtras().getParcelable(STUDENT_KEY);
            nameEditText.setText(student.getName());
            phoneEditText.setText(student.getTelephone());
            emailEditText.setText(student.getEmail());
        } else {
            setTitle(getString(R.string.new_or_edit_student_form_activity_title_new));
            student = new Student();
        }
    }
}