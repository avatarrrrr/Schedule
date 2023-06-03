package br.com.alura.schedule.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.alura.schedule.R;
import br.com.alura.schedule.models.Student;

public class StudentsListAdapter extends BaseAdapter {

    final List<Student> students = new ArrayList<>();
    final private Context context;

    public StudentsListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.students.size();
    }

    @Override
    public Student getItem(int position) {
        return this.students.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.students.get(position).getIdentifier();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder") final View itemStudentLayout = LayoutInflater.from(context).inflate(R.layout.item_student, parent, false);
        final TextView nameTextView = itemStudentLayout.findViewById(R.id.item_student_name);
        final TextView telephoneTextView = itemStudentLayout.findViewById(R.id.item_student_telephone);
        final Student student = this.students.get(position);

        nameTextView.setText(student.getName());
        telephoneTextView.setText(student.getTelephone());

        return itemStudentLayout;

    }

    public void clear() {
        this.students.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Student> studentsToAdd) {
        this.students.addAll(studentsToAdd);
        notifyDataSetChanged();
    }

    public void remove(Student student) {
        this.students.remove(student);
        notifyDataSetChanged();
    }
}
