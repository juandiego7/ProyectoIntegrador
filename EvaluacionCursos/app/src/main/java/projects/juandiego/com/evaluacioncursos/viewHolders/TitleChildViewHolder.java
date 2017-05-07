package projects.juandiego.com.evaluacioncursos.viewHolders;

/**
 * Created by Juan Diego on 12/04/2017.
 */


import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;

import projects.juandiego.com.evaluacioncursos.R;


public class TitleChildViewHolder extends ChildViewHolder {
    public TextView option1,option2,option3,option4;
    public TitleChildViewHolder(View itemView) {
        super(itemView);
        option1 = (TextView)itemView.findViewById(R.id.tvNumeroActividad);
        option2 = (TextView)itemView.findViewById(R.id.tvdescripcionActividad);
        option3 = (TextView)itemView.findViewById(R.id.tvPorcentajeActividad);
        option4 = (TextView)itemView.findViewById(R.id.tvNotaActividad);
    }
}