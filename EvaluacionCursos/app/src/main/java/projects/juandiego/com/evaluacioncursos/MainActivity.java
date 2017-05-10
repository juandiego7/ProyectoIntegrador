package projects.juandiego.com.evaluacioncursos;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.ArrayList;
import java.util.List;

import projects.juandiego.com.evaluacioncursos.adapter.MyAdapter;
import projects.juandiego.com.evaluacioncursos.models.TitleChild;
import projects.juandiego.com.evaluacioncursos.models.TitleCreator;
import projects.juandiego.com.evaluacioncursos.models.TitleParent;
import projects.juandiego.com.evaluacioncursos.services.DescargarIntentService;
import projects.juandiego.com.evaluacioncursos.services.MyReceiver;


public class MainActivity extends AppCompatActivity {

    public static String ACTION = "projects.juandiego.com.evaluacioncursos";

    private RecyclerView recyclerView;
    private Button btnEvaluar;

    private MyReceiver mReceiver;

    public static Context getContext(){
        return context;
    }

    public static Context context;


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ((MyAdapter)recyclerView.getAdapter()).onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        recyclerView = (RecyclerView)findViewById(R.id.myRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnEvaluar = (Button)findViewById(R.id.btnEvaluar);

        MyAdapter adapter = new MyAdapter(this,initData());
        adapter.setParentClickableViewAnimationDefaultDuration();
        adapter.setParentAndIconExpandOnClick(true);

        recyclerView.setAdapter(adapter);


    }


    public void onClick(View view){
        DescargarIntentService.startActionDescargarPreguntas();
    }

    private List<ParentObject> initData() {
        TitleCreator titleCreator = TitleCreator.get(this);
        List<TitleParent> titles = titleCreator.getAll();
        List<ParentObject> parentObject = new ArrayList<>();
        for(TitleParent title:titles)
        {
            List<Object> childList = new ArrayList<>();
            childList.add(new TitleChild("1","Especificacion requisitos","SIN","15%"));
            childList.add(new TitleChild("2","Seguimiento","4.6","15%"));
            title.setChildObjectList(childList);
            parentObject.add(title);
        }
        return parentObject;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mReceiver = new MyReceiver();
        registerReceiver(mReceiver, new IntentFilter(DescargarIntentService.ACTION_DESCARGAR_PREGUNTAS));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }
}
