package projects.juandiego.com.evaluacioncursos.viewHolders;

/**
 * Created by Juan Diego on 12/04/2017.
 */

import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;

import projects.juandiego.com.evaluacioncursos.R;


public class QuestionViewHolder extends ChildViewHolder {
    public TextView tvType;
    public TextView tvDescription;
    public Spinner spAnswers;
    public QuestionViewHolder(View itemView) {
        super(itemView);
        //tvType = (TextView)itemView.findViewById(R.id.tvQuestionType);
        tvDescription = (TextView)itemView.findViewById(R.id.tvQuestionDescription);
        spAnswers = (Spinner)itemView.findViewById(R.id.answer_spinner);
    }
}