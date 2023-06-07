package br.com.alura.schedule.ui.utils;

import android.view.View;
import android.widget.TextView;

import br.com.alura.schedule.R;

public class ViewHolder {
    public final TextView name;
    public final TextView telephone;

    public ViewHolder(View view) {
        name = view.findViewById(R.id.item_student_name);
        telephone = view.findViewById(R.id.item_student_telephone);
    }
}
