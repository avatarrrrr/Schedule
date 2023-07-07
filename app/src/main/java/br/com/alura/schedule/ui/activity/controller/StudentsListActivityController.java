package br.com.alura.schedule.ui.activity.controller;

import static br.com.alura.schedule.ui.activity.constants.ConstantsActivities.STUDENT_KEY;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import java.util.List;
import java.util.concurrent.ExecutorService;

import br.com.alura.schedule.R;
import br.com.alura.schedule.database.ScheduleDatabaseSingleton;
import br.com.alura.schedule.database.dao.RoomStudentDAO;
import br.com.alura.schedule.models.Student;
import br.com.alura.schedule.services.ExecutorServiceSingleton;
import br.com.alura.schedule.ui.activity.NewOrEditStudentFormActivity;
import br.com.alura.schedule.ui.adapter.StudentsListAdapter;

public class StudentsListActivityController {
    private final RoomStudentDAO studentsDAO;
    private final StudentsListAdapter adapter;
    private final Activity context;
    private final ExecutorService executorMultiThread = ExecutorServiceSingleton.getInstance().getExecutorService();


    public StudentsListActivityController(Activity context) {
        this.context = context;
        this.adapter = new StudentsListAdapter(this.context);
        this.studentsDAO = ScheduleDatabaseSingleton.getInstance(this.context).getStudentDAO();
    }

    public void confirmDeleteDialog(final AdapterView.AdapterContextMenuInfo menuInfo) {
        new AlertDialog.Builder(context)
                .setTitle("Delete student")
                .setMessage("You are sure?")
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", (dialog, which) -> deleteItemOnList(adapter.getItem(menuInfo.position)))
                .show();
    }

    public void updateAdapter() {
        executorMultiThread.execute(() -> {
            final List<Student> students = studentsDAO.getStudents();
            context.runOnUiThread(() -> adapter.update(students));
        });
    }

    public void setAdapter(@NonNull ListView studentsView) {
        studentsView.setAdapter(adapter);
    }

    public void deleteItemOnList(final Student student) {
        executorMultiThread.execute(() -> {
            studentsDAO.delete(student);
            context.runOnUiThread(() -> adapter.remove(student));
        });
    }

    public void buttonAddPressBehavior() {
        final View button = context.findViewById(R.id.activity_students_fab_add_student);
        button.setOnClickListener(v -> context.startActivity(new Intent(context, NewOrEditStudentFormActivity.class)));
    }

    public void configList() {
        final ListView studentsView = context.findViewById(R.id.activity_students_list_listview);

        setAdapter(studentsView);
        setOnItemClickBehavior(studentsView);
        context.registerForContextMenu(studentsView);
    }

    private void setOnItemClickBehavior(ListView studentsView) {
        studentsView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            final Student student = (Student) parent.getItemAtPosition(position);
            goToFormsOnEditMode(student);
        });
    }

    private void goToFormsOnEditMode(Student student) {
        Intent intentForm = new Intent(context, NewOrEditStudentFormActivity.class);
        intentForm.putExtra(STUDENT_KEY, student);
        context.startActivity(intentForm);
    }
}
