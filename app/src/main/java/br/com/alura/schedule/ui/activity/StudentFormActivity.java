package br.com.alura.schedule.ui.activity;

import static br.com.alura.schedule.ui.activity.ConstantsActivities.STUDENT_KEY;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import br.com.alura.schedule.R;
import br.com.alura.schedule.dao.StudentsDao;
import br.com.alura.schedule.models.Student;

public class StudentFormActivity extends AppCompatActivity {
    private Student student = null;
    private final StudentsDao studentsDAO = new StudentsDao();
    private EditText nameEditText;
    private EditText phoneEditText;
    private EditText emailEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_form);
        initializeViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_student_form_menu_options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.activity_student_form_menu_save){
            save();
        }
        return super.onOptionsItemSelected(item);
    }

    private void save() {
        fillStudent();
        if (student.hasValidIdentifier()) {
            studentsDAO.edit(student);
        } else {
            studentsDAO.save(student);
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
            setTitle("Edit Student");
            student = intent.getExtras().getParcelable(STUDENT_KEY);
            nameEditText.setText(student.getName());
            phoneEditText.setText(student.getTelephone());
            emailEditText.setText(student.getEmail());
        } else {
            setTitle("New Student");
            student = new Student();
        }
    }
}