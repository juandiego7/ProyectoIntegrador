package projects.juandiego.com.evaluacioncursos.adapter;

/**
 * Created by Juan Diego on 12/04/2017.
 */


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.List;

import projects.juandiego.com.evaluacioncursos.R;
import projects.juandiego.com.evaluacioncursos.models.Question;
import projects.juandiego.com.evaluacioncursos.models.Teacher;
import projects.juandiego.com.evaluacioncursos.viewHolders.QuestionViewHolder;
import projects.juandiego.com.evaluacioncursos.viewHolders.TeacherViewHolder;


/**
 * Created by reale on 23/11/2016.
 */

public class AdapterEvaluacion extends ExpandableRecyclerAdapter<TeacherViewHolder,QuestionViewHolder> {

    LayoutInflater inflater;

    public AdapterEvaluacion(Context context, List<ParentObject> parentItemList) {
        super(context, parentItemList);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public TeacherViewHolder onCreateParentViewHolder(ViewGroup viewGroup) {
        View view = inflater.inflate(R.layout.list_teachers,viewGroup,false);
        return new TeacherViewHolder(view);
    }

    @Override
    public QuestionViewHolder onCreateChildViewHolder(ViewGroup viewGroup) {
        View view = inflater.inflate(R.layout.list_question,viewGroup,false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindParentViewHolder(TeacherViewHolder teacherViewHolder, int i, Object o) {
        Teacher title = (Teacher)o;
        teacherViewHolder.tvTeacherName.setText(title.getName());

    }

    @Override
    public void onBindChildViewHolder(QuestionViewHolder questionViewHolder, int i, Object o) {
        Question title = (Question) o;
        //questionViewHolder.tvType.setText(title.getType());
        questionViewHolder.tvDescription.setText(title.getDescription());
        if ("0".equals(title.getType())){
            questionViewHolder.spAnswers.setVisibility(View.INVISIBLE);
        }

        //questionViewHolder..setText(title.getAnswer());

    }
}