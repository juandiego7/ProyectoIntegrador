package projects.juandiego.com.evaluacioncursos.viewHolders;

/**
 * Created by Juan Diego on 12/04/2017.
 */

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;

import projects.juandiego.com.evaluacioncursos.R;

public class TeacherViewHolder extends ParentViewHolder {
    public TextView tvTeacherName;
    public TextView _btnEnviar;

    public TeacherViewHolder(View itemView) {
        super(itemView);
        tvTeacherName = (TextView)itemView.findViewById(R.id.tvTeacherName);
        _btnEnviar = (TextView) itemView.findViewById(R.id.btnSendEvaluacion);
    }
}