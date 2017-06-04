package projects.juandiego.com.evaluacioncursos;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import projects.juandiego.com.evaluacioncursos.adapter.MyAdapter;
import projects.juandiego.com.evaluacioncursos.config.DatabaseHelper;
import projects.juandiego.com.evaluacioncursos.models.Materia;
import projects.juandiego.com.evaluacioncursos.models.Pregunta;
import projects.juandiego.com.evaluacioncursos.models.TitleChild;
import projects.juandiego.com.evaluacioncursos.models.TitleParent;
import projects.juandiego.com.evaluacioncursos.models.Ver;
import projects.juandiego.com.evaluacioncursos.services.DescargarIntentService;
import projects.juandiego.com.evaluacioncursos.viewHolders.TitleParentViewHolder;

public class MainActivity extends AppCompatActivity {

    public static String ACTION = "projects.juandiego.com.evaluacioncursos";
    public static String TAG_CEDULA = "tagCedula";
    public static String TAG_CODIGO = "tagCodigo";

    private RecyclerView recyclerView;
    private TextView btnEvaluar;
    public static Context context;
    private Toolbar toolbar;
    private String cedula;
    private String codigoMat = "";

    public static Context getContext(){
        return context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        setTitle("Mis Notas UdeA");

        toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView)findViewById(R.id.myRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnEvaluar = (TextView) findViewById(R.id.btnEvaluar);

        inicializarDatos();
    }

    private void inicializarDatos(){
        final SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        cedula = sharedPref.getString(TAG_CEDULA, "");

        if("".equals(cedula)){
            final EditText txtDialog = new EditText(this);
            txtDialog.setInputType(InputType.TYPE_CLASS_NUMBER);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(txtDialog);
            builder.setTitle("Ingrese su cedula");
            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id8) {
                    cedula = txtDialog.getText().toString();
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString(TAG_CEDULA, cedula);
                    editor.commit();
                    DescargarIntentService.startActionDescargarNotas(cedula);
                }
            });
            builder.setCancelable(false);
            builder.show();
        }else{
            ConnectivityManager connectivityManager = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo actNetInfo = connectivityManager.getActiveNetworkInfo();

            if(actNetInfo != null && actNetInfo.isConnected()){
                DescargarIntentService.startActionDescargarNotas(cedula);
            }else{
                Toast.makeText(getApplicationContext(), "Error: No hay acceso a Internet", Toast.LENGTH_SHORT).show();
                actualiceVistaNotas();
            }

            //actualiceVistaNotas();
        }
    }

    public void actualiceVistaNotas(){
        MyAdapter adapter = new MyAdapter(this,initData());
        adapter.setParentClickableViewAnimationDefaultDuration();
        adapter.setParentAndIconExpandOnClick(true);

        recyclerView.setAdapter(adapter);
    }

    private boolean verificarVerNotas_DescargarProfesores() {
        List<Ver> listVer = null;
        try {
            listVer = DatabaseHelper.getInstance().getDao(Ver.class).queryForEq("puede", "N");
        } catch (SQLException e) {
            Log.d(DescargarIntentService.ONHANDLEINTENT, "Error cosultando Ver de la base de datos: " + e.getMessage());
            //e.printStackTrace();
        }
        return listVer.isEmpty();
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnEvaluar:
                TextView txt = (TextView) view;
                //Toast.makeText(getApplicationContext(), txt.getHint().toString(), Toast.LENGTH_SHORT).show();
                codigoMat = txt.getHint().toString();
                Log.d(DescargarIntentService.ONHANDLEINTENT, "Cod Mat: "+codigoMat);
                try {
                    long cantP = DatabaseHelper.getInstance().getDao(Pregunta.class).countOf();
                    //sacar
                    if (cantP > 0){
                        Intent intent = new Intent(this, EvaluarActivity.class);
                        intent.putExtra(TAG_CODIGO, codigoMat);
                        startActivity(intent);
                    }else{
                        DescargarIntentService.startActionDescargarPreguntas();
                        Toast.makeText(this, "Descargando preguntas... Por favor Espere ", Toast.LENGTH_SHORT).show();
                    }
                } catch (SQLException e) {
                    Log.d(DescargarIntentService.ONHANDLEINTENT, "Error cosultando Materias de la base de datos: " + e.getMessage());
                    //e.printStackTrace();
                }
            break;
        }
    }

    private List<ParentObject> initData() {
        List<Materia> listMateria = null;
        List<Ver> listVer = null;
        try {
            listMateria = DatabaseHelper.getInstance().getDao(Materia.class).queryForAll();
            listVer = DatabaseHelper.getInstance().getDao(Ver.class).queryForAll();
        } catch (SQLException e) {
            Log.d(DescargarIntentService.ONHANDLEINTENT, "Error cosultando Materias o Ver de la base de datos: " + e.getMessage());
            //e.printStackTrace();
        }

        List<ParentObject> parentObject = new ArrayList<>();

        if (listMateria != null && !listMateria.isEmpty()){
            for(Materia m: listMateria){
                TitleParent titleParent = new TitleParent(m.getNombre());
                titleParent.setCodigo(m.getCodigo());
                String puede = "S";
                List<Object> childList = new ArrayList<>();
                for (Ver v: listVer){
                    if (v.getMateria().equals(m.getCodigo())){
                        puede = v.getPuede();
                    }
                }
                titleParent.setPuedeVerNota(puede);
                if ("S".equals(puede)) {
                    childList.add(new TitleChild("1","Especificacion requisitos","SIN","15%"));
                    childList.add(new TitleChild("2","Seguimiento","4.6","15%"));
                }else {
                    childList.add(new TitleChild("", "Debe realizar la evaluación", "", ""));
                }
                titleParent.setChildObjectList(childList);
                parentObject.add(titleParent);
            }
        }else{
            Log.d(DescargarIntentService.ONHANDLEINTENT, "Base de datos vacía, Materia");
            TitleParent titleParent = new TitleParent("Sin Materias");
            parentObject.add(titleParent);
        }
        return parentObject;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        MyAdapter adapter = (MyAdapter)recyclerView.getAdapter();
        if (adapter != null){
            adapter.onSaveInstanceState(outState);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DescargarIntentService.ACTION_DESCARGAR_NOTAS);
        intentFilter.addAction(DescargarIntentService.ACTION_DESCARGAR_PREGUNTAS);
        intentFilter.addAction(DescargarIntentService.ACTION_DESCARGAR_PROFESORES);
        intentFilter.addAction(DescargarIntentService.ACTION_DESCARGAR_PUEDE_VER_NOTAS);
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(mReceiver, intentFilter);
        //DescargarIntentService.startActionDescargarPreguntas();
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(mReceiver);
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(DescargarIntentService.ONHANDLEINTENT, "Recibido " + intent.getAction());
            //Toast.makeText(MainActivity.getContext(), "Recibido "+ intent.getAction(), Toast.LENGTH_SHORT).show();
            Intent i = new Intent(MainActivity.getContext(), EvaluarActivity.class);
            //startActivity(i);
            if (intent.getStringExtra(DescargarIntentService.ERROR) != null){
                actualiceVistaNotas();
                Log.d(DescargarIntentService.ONHANDLEINTENT, "Error: " + intent.getStringExtra(DescargarIntentService.ERROR));
                Toast.makeText(getApplication(), intent.getStringExtra(DescargarIntentService.ERROR), Toast.LENGTH_SHORT).show();
            } else if(DescargarIntentService.ACTION_DESCARGAR_NOTAS.equals(intent.getAction())){
                DescargarIntentService.startActionDescargarPuedeVerNotas("20171", cedula);
            }else if (DescargarIntentService.ACTION_DESCARGAR_PREGUNTAS.equals(intent.getAction())){
                i.putExtra(TAG_CODIGO, codigoMat);
                Log.d(DescargarIntentService.ONHANDLEINTENT, "Actualizando Vista PREGUNTAS " + codigoMat);
                startActivity(i);

                //Toast.makeText(getApplicationContext(), "Actualizando Vista PREGUNTAS", Toast.LENGTH_SHORT).show();
                //actualiceVistaNotas();
            } else if(DescargarIntentService.ACTION_DESCARGAR_PUEDE_VER_NOTAS.equals(intent.getAction())){
                if(!verificarVerNotas_DescargarProfesores()){
                    DescargarIntentService.startActionDescargarProfesores("20171", cedula);
                    //DescargarIntentService.startActionDescargarPreguntas();
                }else{
                    actualiceVistaNotas();
                }
            } else if(DescargarIntentService.ACTION_DESCARGAR_PROFESORES.equals(intent.getAction())){
                Log.d(DescargarIntentService.ONHANDLEINTENT, "Actualizando Vista PROFESORES");
                //Toast.makeText(getApplicationContext(), "Actualizando Vista PROFESORES", Toast.LENGTH_SHORT).show();
                actualiceVistaNotas();
            }
        }
    };
}
