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
import projects.juandiego.com.evaluacioncursos.models.TitleChild;
import projects.juandiego.com.evaluacioncursos.models.TitleParent;
import projects.juandiego.com.evaluacioncursos.viewHolders.TitleChildViewHolder;
import projects.juandiego.com.evaluacioncursos.viewHolders.TitleParentViewHolder;


/**
 * Created by reale on 23/11/2016.
 */

public class MyAdapter extends ExpandableRecyclerAdapter<TitleParentViewHolder,TitleChildViewHolder> {

    LayoutInflater inflater;

    public MyAdapter(Context context, List<ParentObject> parentItemList) {
        super(context, parentItemList);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public TitleParentViewHolder onCreateParentViewHolder(ViewGroup viewGroup) {
        View view = inflater.inflate(R.layout.list_parent,viewGroup,false);
        return new TitleParentViewHolder(view);
    }

    @Override
    public TitleChildViewHolder onCreateChildViewHolder(ViewGroup viewGroup) {
        View view = inflater.inflate(R.layout.list_child,viewGroup,false);
        return new TitleChildViewHolder(view);
    }

    @Override
    public void onBindParentViewHolder(TitleParentViewHolder titleParentViewHolder, int i, Object o) {
        TitleParent title = (TitleParent)o;
        titleParentViewHolder._textView.setText(title.getTitle());

    }

    @Override
    public void onBindChildViewHolder(TitleChildViewHolder titleChildViewHolder, int i, Object o) {
        TitleChild title = (TitleChild)o;
        titleChildViewHolder.option1.setText(title.getOption1());
        titleChildViewHolder.option2.setText(title.getOption2());
        titleChildViewHolder.option3.setText(title.getOption3());
        titleChildViewHolder.option4.setText(title.getOption4());

    }
}
