package br.com.alura.schedule.ui.activity;

import static br.com.alura.schedule.ui.activity.constants.ConstantsActivities.STUDENT_KEY;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import br.com.alura.schedule.R;
import br.com.alura.schedule.database.ScheduleDatabase;
import br.com.alura.schedule.database.dao.RoomStudentDAO;
import br.com.alura.schedule.database.dao.RoomTelephoneDAO;
import br.com.alura.schedule.models.Student;
import br.com.alura.schedule.models.Telephone;
import br.com.alura.schedule.models.enums.TelephoneType;

public class NewOrEditStudentFormActivity extends AppCompatActivity {
    private Student student = null;
    private RoomStudentDAO roomStudentDAO;
    private RoomTelephoneDAO roomTelephoneDAO;
    private EditText nameEditText;
    private EditText landlineEditText;
    private EditText emailEditText;
    private EditText cellPhoneEditText;
    private List<Telephone> telephones;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_or_edit_student_form);
        final ScheduleDatabase database = ScheduleDatabase.getInstance(this);
        roomStudentDAO = database.getStudentDAO();
        roomTelephoneDAO = database.getTelephoneDAO();
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
            saveOrEdit();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initializeViews() {
        nameEditText = findViewById(R.id.activity_student_form_name);
        landlineEditText = findViewById(R.id.activity_student_form_landline);
        emailEditText = findViewById(R.id.activity_student_form_email);
        cellPhoneEditText = findViewById(R.id.activity_student_form_cell_phone);

        isEdit();
    }

    private void saveOrEdit() {
        Telephone landline = new Telephone(student.getIdentifier(), landlineEditText.getText().toString(), TelephoneType.LANDLINE);
        Telephone cellphone = new Telephone(student.getIdentifier(), cellPhoneEditText.getText().toString(), TelephoneType.CELL_PHONE);

        fillStudent();
        if (student.hasValidIdentifier()) {
            edit(landline, cellphone);
        } else {
            save(landline, cellphone);
        }
        finish();
    }

    private void edit(Telephone landline, Telephone cellPhone) {
        roomStudentDAO.edit(student);
        assignExistingTelephonesIdentifier(landline, cellPhone);
        updateOrDelete(landline, cellPhone);
    }

    private void updateOrDelete(Telephone landline, Telephone cellPhone) {
        if (landline.getNumber().length() == 0) {
            roomTelephoneDAO.delete(landline);
        } else {
            roomTelephoneDAO.update(landline);
        }
        if (cellPhone.getNumber().length() == 0) {
            roomTelephoneDAO.delete(cellPhone);
        } else {
            roomTelephoneDAO.update(cellPhone);
        }
    }

    private void assignExistingTelephonesIdentifier(Telephone landline, Telephone cellPhone) {
        telephones.forEach(telephone -> {
            if (telephone.getTelephoneType() == TelephoneType.LANDLINE) {
                landline.setIdentifier(telephone.getIdentifier());
            } else {
                cellPhone.setIdentifier(telephone.getIdentifier());
            }
        });
    }

    private void save(Telephone landline, Telephone cellPhone) {
        final int studentIdentifier = roomStudentDAO.save(student).intValue();
        landline.setStudentIdentifier(studentIdentifier);
        cellPhone.setStudentIdentifier(studentIdentifier);
        if (landlineEditText.getText().length() != 0) {
            roomTelephoneDAO.save(landline);
        }
        if (cellPhoneEditText.getText().length() != 0) {
            roomTelephoneDAO.save(cellPhone);
        }
    }

    private void fillStudent() {
        student.setName(nameEditText.getText().toString());
        student.setEmail(emailEditText.getText().toString());
    }

    private void isEdit() {
        Intent intent = getIntent();

        if (intent.hasExtra(STUDENT_KEY)) {
            setTitle(getString(R.string.new_or_edit_student_form_activity_title_edit));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                student = intent.getParcelableExtra(STUDENT_KEY, Student.class);
            } else {
                student = intent.getParcelableExtra(STUDENT_KEY);
            }
            nameEditText.setText(student.getName());
            emailEditText.setText(student.getEmail());
            setTelephones();
        } else {
            setTitle(getString(R.string.new_or_edit_student_form_activity_title_new));
            student = new Student();
        }
    }

    private void setTelephones() {
        telephones = roomTelephoneDAO.getTelephonesFromStudent(student.getIdentifier());
        telephones.forEach(telephone -> {
            if (telephone.getTelephoneType() == TelephoneType.LANDLINE) {
                landlineEditText.setText(telephone.getNumber());

            } else {
                cellPhoneEditText.setText(telephone.getNumber());
            }
        });
    }
}