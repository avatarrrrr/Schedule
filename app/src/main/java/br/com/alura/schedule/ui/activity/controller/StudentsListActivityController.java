package br.com.alura.schedule.ui.activity.controller;

import android.content.Context;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import br.com.alura.schedule.database.ScheduleDatabase;
import br.com.alura.schedule.database.dao.RoomStudentDAO;
import br.com.alura.schedule.models.Student;
import br.com.alura.schedule.ui.adapter.StudentsListAdapter;

public class StudentsListActivityController {
    final private RoomStudentDAO studentsDAO;
    final private StudentsListAdapter adapter;
    final private Context context;

    public StudentsListActivityController(Context context) {
        this.context = context;
        this.adapter = new StudentsListAdapter(this.context);
        this.studentsDAO = ScheduleDatabase.getInstance(this.context).getStudentDAO();
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
        adapter.update(studentsDAO.getStudents());
    }

    public void setAdapter(@NonNull ListView studentsView) {
        studentsView.setAdapter(adapter);
    }

    public void deleteItemOnList(Student student) {
        studentsDAO.delete(student);
        adapter.remove(student);
    }
}
