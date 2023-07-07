package br.com.alura.schedule.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import br.com.alura.schedule.R;
import br.com.alura.schedule.database.ScheduleDatabaseSingleton;
import br.com.alura.schedule.database.dao.RoomTelephoneDAO;
import br.com.alura.schedule.models.Student;
import br.com.alura.schedule.services.ExecutorServiceSingleton;
import br.com.alura.schedule.ui.adapter.viewholders.ViewHolder;

public class StudentsListAdapter extends BaseAdapter {

    private final List<Student> students = new ArrayList<>();
    private final Activity context;
    private final RoomTelephoneDAO telephoneDAO;
    private final ExecutorService executorMultiThread = ExecutorServiceSingleton.getInstance().getExecutorService();


    public StudentsListAdapter(Activity context) {
        this.context = context;
        telephoneDAO = ScheduleDatabaseSingleton.getInstance(this.context).getTelephoneDAO();
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
        executorMultiThread.execute(() -> {
            final String number = telephoneDAO.getFirstTelephoneFromStudent(student.getIdentifier()).getNumber();
            context.runOnUiThread(() -> holder.telephone.setText(number));
        });
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
