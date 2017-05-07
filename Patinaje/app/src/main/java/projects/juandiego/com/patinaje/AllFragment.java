package projects.juandiego.com.patinaje;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllFragment extends Fragment{


    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private SQLiteDatabase dataBase;
    private BdHelper dBHelper;
    private ArrayList<Person> persons;

    public AllFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_all, container, false);

        recyclerView = (RecyclerView)v.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        persons = new ArrayList<>();

        dBHelper = new BdHelper(getActivity().getApplicationContext());
        dataBase =  dBHelper.getWritableDatabase();

        Cursor cursorPlace = dataBase.rawQuery("select * from " + Database.TABLE_USER,null);

        int cont = 0;
        String[] names = new String[cursorPlace.getCount()];
        String[] lastNames = new String[cursorPlace.getCount()];
        String[] ages = new String[cursorPlace.getCount()];
        String[] months = new String[cursorPlace.getCount()];

        while (cursorPlace.moveToNext()){
            Person person = new Person(
                    cursorPlace.getString(cursorPlace.getColumnIndex(Database.Column_User.NAME)),
                    cursorPlace.getString(cursorPlace.getColumnIndex(Database.Column_User.LAST_NAME)),
                    cursorPlace.getString(cursorPlace.getColumnIndex(Database.Column_User.DOCUMENT)),
                    cursorPlace.getString(cursorPlace.getColumnIndex(Database.Column_User.BIRTHDAY)),
                    cursorPlace.getString(cursorPlace.getColumnIndex(Database.Column_User.DATE_START)),
                    cursorPlace.getString(cursorPlace.getColumnIndex(Database.Column_User.STATUS)),
                    setAge(cursorPlace.getString(cursorPlace.getColumnIndex(Database.Column_User.BIRTHDAY)))+"",
                    "0");
            persons.add(person);
            /*names[cont] = cursorPlace.getString(cursorPlace.getColumnIndex(Database.Column_User.NAME));
            lastNames[cont] = cursorPlace.getString(cursorPlace.getColumnIndex(Database.Column_User.LAST_NAME));

            ages[cont] = setAge(cursorPlace.getString(cursorPlace.getColumnIndex(Database.Column_User.BIRTHDAY)))+"";
            months[cont] = "0";
            //Bitmap bmp = BitmapFactory.decodeByteArray(imagen,0,imagen.length);
            cont++;
            //Actualizar los parametros del Adapter*/

        }

        //adapter = new RecyclerAdapter(names,lastNames,ages,months);
        adapter = new RecyclerAdapter(persons);
        recyclerView.setAdapter(adapter);
        return v;
    }


    private void insertarLugares(){
        ContentValues valores;

        String[] nombres = {"Acropolis","Agora","Museo acropolis","Partenon","Plaka","Museo arqueologico","Plaza sintagma","Templo de zeus olimpico"};
        String[] descripcion = {
                "Antigua grecia, templo, atenea, arquitectura, historia",
                "Antigua grecia, museo, historia",
                "Museo, arquitectura",
                "Templo, antigua grecia, arquitectura, historia",
                "Shopping, paso, musica, iglesia, historia",
                "Museo",
                "Shopping, vida nocturna, cultura, museo, arquitectura",
                "Zeus, templo, arquitectura",
        };
        String[] detalle={"detalle1","detalle2","detalle3","detalle4","detalle5","detalle6","detalle7","detalle8"};
        String[] ubicacion={"ubicacion1","ubicacion2","ubicacion3","ubicacion4","ubicacion5","ubicacion6","ubicacion7","ubicacion8"};
        String[] temp={"temperatura1","temperatura2","temperatura3","temperatura4","temperatura5","temperatura6","temperatura7","temperatura8"};
        String[] sitios={"sitios1","sitios2","sitios3","sitios4","sitios5","sitios6","sitios7","sitios8"};

        Bitmap bmpImagen;
        ByteArrayOutputStream byteImagen;

        for (int i=0; i<=7;i++){
            valores = new ContentValues();
            //bmpImagen = BitmapFactory.decodeResource(getResources(), images[i]);
            byteImagen = new ByteArrayOutputStream();
            //bmpImagen.compress(Bitmap.CompressFormat.PNG, 100, byteImagen);

           /* valores.put(BaseDeDatos.Column_Place.NOMBRE,nombres[i]);
            valores.put(BaseDeDatos.Column_Place.INFO_GENERAL,detalle[i]);
            valores.put(BaseDeDatos.Column_Place.DESCRIPCION,descripcion[i]);
            valores.put(BaseDeDatos.Column_Place.UBICACION,ubicacion[i]);
            valores.put(BaseDeDatos.Column_Place.TEMPERATURA,temp[i]);
            valores.put(BaseDeDatos.Column_Place.SITIOS_RECOMENDADOS,sitios[i]);
            valores.put(BaseDeDatos.Column_Place.PUNTUACION,puntos[i]);
            valores.put(BaseDeDatos.Column_Place.FOTO, byteImagen.toByteArray());*/

           // dataBase.insertWithOnConflict(BaseDeDatos.TABLE_PLACE, null, valores, SQLiteDatabase.CONFLICT_IGNORE);
        }
    }

    public int setAge(String birthday) {     //fecha_nac debe tener el formato dd/MM/yyyy

        Date fechaActual = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String hoy = formato.format(fechaActual);
        String[] dat1 = birthday.split("/");
        String[] dat2 = hoy.split("/");
        int anos = Integer.parseInt(dat2[2]) - Integer.parseInt(dat1[2]);
        int mes = Integer.parseInt(dat2[1]) - Integer.parseInt(dat1[1]);
        if (mes < 0) {
            anos = anos - 1;
        } else if (mes == 0) {
            int dia = Integer.parseInt(dat2[0]) - Integer.parseInt(dat1[0]);
            if (dia > 0) {
                anos = anos - 1;
            }
        }
        return anos;
    }

    public ArrayList<Person> getPersons(){
        return persons;
    }

    public RecyclerAdapter getAdapter(){
        return (RecyclerAdapter) adapter;
    }
}
