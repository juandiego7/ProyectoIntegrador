package projects.juandiego.com.patinaje;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private EditText txtDocument;
    private EditText txtName;
    private EditText txtLastName;
    private TextView fechaNac;
    private String document;
    private String name;
    private String lastName;
    private String dateStart;
    private String fecha_Nac;
    private String status;
    private int dia, mes, año; //Se define los componentes de la fecha
    BdHelper dBHelper;
    SQLiteDatabase dataBase;
    ContentValues valores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Registro");

        txtName = (EditText)findViewById(R.id.txtNombre);
        txtLastName = (EditText)findViewById(R.id.txtLastName);
        txtDocument = (EditText)findViewById(R.id.txtIdentidad);
        fechaNac = (TextView) findViewById(R.id.lblDate); //Instanciamos el TextView de fecha
    }

    public void registraUsuario(View view){
        dBHelper = new BdHelper(this);
        dataBase = dBHelper.getWritableDatabase();

        document = txtDocument.getText().toString();
        name = txtName.getText().toString();
        lastName = txtLastName.getText().toString();
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("d/MM/yyyy");
        dateStart = sdf.format(date);
        fecha_Nac = fechaNac.getText().toString().substring(18);
        status = "0";

        //Toast.makeText(this,,Toast.LENGTH_SHORT).show();

        Cursor cursorU = dataBase.rawQuery("select * from " + Database.TABLE_USER + " where " + Database.Column_User.DOCUMENT + " = '" + document + "'", null);

        if (document.equals("") || name.equals("") || fecha_Nac.equals("")){
            Toast.makeText(this,"Ninugno de los campos * puede estar vacío",Toast.LENGTH_SHORT).show();
        }else if(cursorU.moveToFirst()){
            Toast.makeText(this,"El numero de documento ya esta regisrsado", Toast.LENGTH_SHORT).show();
        }else{
            valores = new ContentValues(); //se crea un nuevo elemento valores
            valores.put(Database.Column_User.DOCUMENT, document); //se insertan los ṕares de elementos: clave-valor
            valores.put(Database.Column_User.NAME, name);
            valores.put(Database.Column_User.LAST_NAME, lastName);
            valores.put(Database.Column_User.DATE_START, dateStart);
            valores.put(Database.Column_User.BIRTHDAY, fecha_Nac);
            valores.put(Database.Column_User.STATUS, status);

            //validaciones

            dataBase.insertWithOnConflict(Database.TABLE_USER, null, valores, SQLiteDatabase.CONFLICT_IGNORE);//se inserta en la base de data el elmento valores en la tabla USER
            Toast.makeText(this, "Creado", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }


        //Cursor c = dataBase.rawQuery("SELECT "+BaseDeDatos.Column_User.USER+", "+BaseDeDatos.Column_User.PASSWORD+", "+BaseDeDatos.Column_User.EMAIL+" FROM " + BaseDeDatos.TABLE_USER, null);
        /*Cursor c = dataBase.query(BaseDeDatos.TABLE_USER, null, null, null, null, null, null); //consulta de todos los elementos de la tabla USER y los almacena en el Cursor

        cont = 0;

        while (c.moveToNext()){ //se recorre elemento por elemento los data del Cursor
            cont++;
            String v = c.getString(c.getColumnIndex(BaseDeDatos.Column_User.USER)); //se obtiene el valor de la columna USER, los usuarios, aqui puede ser BaseDeDatos.Column_User.EMAIL para obtener los correos
            Toast.makeText(this, "cont: " + cont + ", Usuario: " + v, Toast.LENGTH_SHORT).show();
        }*/

        dataBase.close();

    }

    public void updateDate(View v){ //Metodo de accion para modificar la fecha de nacimiento
        DialogFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(getFragmentManager(), "datePicker");
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        fechaNac.setText("Fecha Nacimiento: " + day + "/" + (month + 1) + "/" + year);
    }

    @Override
    protected void onSaveInstanceState(Bundle guardaEstado) {
        super.onSaveInstanceState(guardaEstado);
        //Guardar fecha, sexo, resultado
        guardaEstado.putString("fecha", fechaNac.getText().toString());
        //guardaEstado.putString("sexo", sexo);
        //guardaEstado.putString("resultado", resultado.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle recuperaEstado) {
        super.onRestoreInstanceState(recuperaEstado);
        //Recupaeramos los data del Bundle
        fechaNac.setText(recuperaEstado.getString("fecha"));
        //sexo=recuperaEstado.getString("sexo");
        //resultado.setText(recuperaEstado.getString("resultado"));


    }

}
