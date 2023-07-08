package br.com.alura.schedule.ui.activity.controller;

import static br.com.alura.schedule.ui.activity.constants.ConstantsActivities.STUDENT_KEY;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import br.com.alura.schedule.R;
import br.com.alura.schedule.database.ScheduleDatabaseSingleton;
import br.com.alura.schedule.database.dao.RoomStudentDAO;
import br.com.alura.schedule.database.dao.RoomTelephoneDAO;
import br.com.alura.schedule.models.Student;
import br.com.alura.schedule.models.Telephone;
import br.com.alura.schedule.models.enums.TelephoneType;
import br.com.alura.schedule.services.ExecutorServiceSingleton;

public class NewOrEditStudentFormActivityController {
    private final ExecutorService executorMultiThread = ExecutorServiceSingleton.getInstance().getExecutorService();
    private final Activity context;
    private final RoomStudentDAO roomStudentDAO;
    private final RoomTelephoneDAO roomTelephoneDAO;
    private final List<Telephone> telephones = new ArrayList<>();

    private Student student;

    private EditText nameEditText;
    private EditText landlineEditText;
    private EditText emailEditText;
    private EditText cellPhoneEditText;

    public NewOrEditStudentFormActivityController(Activity activityContext) {
        final ScheduleDatabaseSingleton database = ScheduleDatabaseSingleton.getInstance(activityContext);

        context = activityContext;
        roomStudentDAO = database.getStudentDAO();
        roomTelephoneDAO = database.getTelephoneDAO();

        initializeViews();
        verifyIfEditMode();
    }

    private void initializeViews() {
        nameEditText = context.findViewById(R.id.activity_student_form_name);
        landlineEditText = context.findViewById(R.id.activity_student_form_landline);
        emailEditText = context.findViewById(R.id.activity_student_form_email);
        cellPhoneEditText = context.findViewById(R.id.activity_student_form_cell_phone);
    }

    private void verifyIfEditMode() {
        Intent intent = context.getIntent();

        if (intent.hasExtra(STUDENT_KEY)) {
            context.setTitle(context.getString(R.string.new_or_edit_student_form_activity_title_edit));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                student = intent.getParcelableExtra(STUDENT_KEY, Student.class);
            } else {
                student = intent.getParcelableExtra(STUDENT_KEY);
            }
            nameEditText.setText(student.getName());
            emailEditText.setText(student.getEmail());
            setTelephones();
        } else {
            context.setTitle(context.getString(R.string.new_or_edit_student_form_activity_title_new));
            student = new Student();
        }
    }

    private void setTelephones() {
        executorMultiThread.execute(() -> {
            telephones.addAll(roomTelephoneDAO.getTelephonesFromStudent(student.getIdentifier()));
            telephones.forEach(telephone -> {
                if (telephone.getTelephoneType() == TelephoneType.LANDLINE) {
                    context.runOnUiThread(() -> landlineEditText.setText(telephone.getNumber()));
                } else {
                    context.runOnUiThread(() -> cellPhoneEditText.setText(telephone.getNumber()));
                }
            });
        });

    }

    public void saveOrEdit() {
        Telephone landline = new Telephone(student.getIdentifier(), landlineEditText.getText().toString(), TelephoneType.LANDLINE);
        Telephone cellphone = new Telephone(student.getIdentifier(), cellPhoneEditText.getText().toString(), TelephoneType.CELL_PHONE);

        fillStudent();

        executorMultiThread.execute(() -> {
            if (student.hasValidIdentifier()) {
                edit(landline, cellphone);
            } else {
                save(landline, cellphone);
            }
            context.runOnUiThread(context::finish);
        });
    }

    private void fillStudent() {
        student.setName(nameEditText.getText().toString());
        student.setEmail(emailEditText.getText().toString());
    }

    private void edit(Telephone landline, Telephone cellPhone) {
        executorMultiThread.execute(() -> roomStudentDAO.edit(student));
        assignExistingTelephonesIdentifier(landline, cellPhone);
        updateOrDelete(landline, cellPhone);
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

    private void updateOrDelete(Telephone landline, Telephone cellPhone) {
        if (landline.getNumber().length() == 0) roomTelephoneDAO.delete(landline);
        else roomTelephoneDAO.update(landline);

        if (cellPhone.getNumber().length() == 0) roomTelephoneDAO.delete(cellPhone);
        else roomTelephoneDAO.update(cellPhone);
    }

    private void save(Telephone landline, Telephone cellPhone) {
        final int studentIdentifier = roomStudentDAO.save(student).intValue();
        landline.setStudentIdentifier(studentIdentifier);
        cellPhone.setStudentIdentifier(studentIdentifier);

        if (landlineEditText.getText().length() != 0) roomTelephoneDAO.save(landline);
        if (cellPhoneEditText.getText().length() != 0) roomTelephoneDAO.save(cellPhone);
    }
}
