package br.com.alura.schedule.ui.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import br.com.alura.schedule.R;
import br.com.alura.schedule.dao.StudentsDao;
import br.com.alura.schedule.models.Student;

public class StudentFormActivity extends AppCompatActivity {
    private final StudentsDao studentsDAO = new StudentsDao();
    private EditText nameEditText;
    private EditText phoneEditText;
    private EditText emailEditText;
    private Button saveButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_form);
        setTitle("New Student");

        initializeViews();
        saveButtonPressBehavior();
    }

    private void saveButtonPressBehavior() {
        saveButton.setOnClickListener(v -> {
            createNewStudent();
            finish();
        });
    }

    private void createNewStudent() {
        Student student = new Student(nameEditText.getText().toString(), phoneEditText.getText().toString(), emailEditText.getText().toString());
        studentsDAO.save(student);
    }

    private void initializeViews() {
        nameEditText = findViewById(R.id.activity_student_form_name);
        phoneEditText = findViewById(R.id.activity_student_form_telephone);
        emailEditText = findViewById(R.id.activity_student_form_email);
        saveButton = findViewById(R.id.activity_student_form_save);

        isEdit();
    }

    private void isEdit() {
        Student student = getIntent().getExtras().getParcelable(StudentListActivity.STUDENT_KEY);
        if(student != null){
            nameEditText.setText(student.getName());
            phoneEditText.setText(student.getTelephone());
            emailEditText.setText(student.getEmail());
        }
    }
}