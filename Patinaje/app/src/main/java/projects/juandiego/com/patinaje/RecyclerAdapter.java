package projects.juandiego.com.patinaje;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Juan Diego on 27/08/2016.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>  {

    OnHeadlineSelectedListener mCallback;

    ArrayList<Person> people;

    String[] names;
    String[] lastNames;
    String[] ages;
    String[] months;
    String[] data;

    public RecyclerAdapter(String[] names, String[] lastNames, String[] ages, String[] months){
        this.names=names;
        this.lastNames=lastNames;
        this.ages=ages;
        this.months=months;
        data = new String[4];
    }

    public RecyclerAdapter(ArrayList<Person> p){
        this.people = p;
        data = new String[4];
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView itemTitle;
        public TextView itemDetail;
        public TextView IDs;

        public ViewHolder(View itemView) {
            super(itemView);
            itemTitle = (TextView)itemView.findViewById(R.id.txtName);
            itemDetail =(TextView)itemView.findViewById(R.id.txtAge);
            IDs = (TextView) itemView.findViewById(R.id.txtMonths);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    data[0] = itemTitle.getText().toString();
                    data[1] = itemDetail.getText().toString();
                    data[2] = itemDetail.getText().toString();
                    data[3] = IDs.getText().toString();
                    mCallback.onArticleSelected(data);
                }
            });
        }
    }

    public interface OnHeadlineSelectedListener {
        /** Called by HeadlinesFragment when a list item is selected */
        public void onArticleSelected(String datos[]);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            mCallback = (OnHeadlineSelectedListener) recyclerView.getContext();
        } catch (ClassCastException e) {
            throw new ClassCastException(recyclerView.getContext().toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Person person = people.get(i);
        viewHolder.itemTitle.setText(person.getName()+" "+person.getLastName());
        viewHolder.itemDetail.setText(person.getAge());
        viewHolder.IDs.setText(person.getMonths());
        /*viewHolder.itemTitle.setText(names[i]+" "+lastNames[i]);
        viewHolder.itemDetail.setText(ages[i]);
        viewHolder.IDs.setText(months[i]);*/
    }

    @Override
    public int getItemCount() {
        return people.size();
    }

    public void setFilter(ArrayList<Person> p){
        people = new ArrayList<>();
        people.addAll(p);
        notifyDataSetChanged();
    }

}
