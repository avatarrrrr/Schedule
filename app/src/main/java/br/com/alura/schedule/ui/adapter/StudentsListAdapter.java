package br.com.alura.schedule.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import br.com.alura.schedule.R;
import br.com.alura.schedule.database.ScheduleDatabase;
import br.com.alura.schedule.database.dao.RoomTelephoneDAO;
import br.com.alura.schedule.models.Student;
import br.com.alura.schedule.ui.adapter.viewholders.ViewHolder;

public class StudentsListAdapter extends BaseAdapter {

    final List<Student> students = new ArrayList<>();
    final private Context context;

    final RoomTelephoneDAO telephoneDAO;

    public StudentsListAdapter(Context context) {
        this.context = context;
        telephoneDAO = ScheduleDatabase.getInstance(this.context).getTelephoneDAO();
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
        View itemStudentLayout;
        ViewHolder holder;

        if (convertView == null) {
            itemStudentLayout = createView(parent);
            holder = new ViewHolder(itemStudentLayout);
            itemStudentLayout.setTag(holder);
        } else {
            itemStudentLayout = convertView;
            holder = (ViewHolder) itemStudentLayout.getTag();
        }

        assignStudentOnView(position, holder);

        return itemStudentLayout;
    }

    private void assignStudentOnView(int position, ViewHolder holder) {
        final Student student = this.students.get(position);
        holder.name.setText(student.getName());
        holder.telephone.setText(telephoneDAO.getFirstTelephoneFromStudent(student.getIdentifier()).getNumber());
    }


    private View createView(ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_student, parent, false);
    }

    public void update(List<Student> studentsToAdd) {
        this.students.clear();
        this.students.addAll(studentsToAdd);
        notifyDataSetChanged();
    }

    public void remove(Student student) {
        this.students.remove(student);
        notifyDataSetChanged();
    }
}
