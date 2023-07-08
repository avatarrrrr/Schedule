package br.com.alura.schedule.ui.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import br.com.alura.schedule.R;
import br.com.alura.schedule.ui.activity.controller.NewOrEditStudentFormActivityController;

public class NewOrEditStudentFormActivity extends AppCompatActivity {
    NewOrEditStudentFormActivityController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_or_edit_student_form);

        controller = new NewOrEditStudentFormActivityController(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_student_form_menu_options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.activity_student_form_menu_save) {
            controller.saveOrEdit();
        }
        return super.onOptionsItemSelected(item);
    }
}