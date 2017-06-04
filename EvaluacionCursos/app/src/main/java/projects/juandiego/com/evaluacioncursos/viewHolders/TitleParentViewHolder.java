package projects.juandiego.com.evaluacioncursos.viewHolders;

/**
 * Created by Juan Diego on 12/04/2017.
 */


import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;

import projects.juandiego.com.evaluacioncursos.R;

public class TitleParentViewHolder extends ParentViewHolder {
    public TextView _textView;
    public ImageButton _imageButton;
    public TextView _textEvaluar;

    public TitleParentViewHolder(View itemView) {
        super(itemView);
        _textView = (TextView)itemView.findViewById(R.id.parentTitle);
        _imageButton = (ImageButton) itemView.findViewById(R.id.expandArrow);
        _textEvaluar = (TextView) itemView.findViewById(R.id.btnEvaluar);
    }
}