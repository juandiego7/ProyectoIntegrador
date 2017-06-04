package projects.juandiego.com.evaluacioncursos.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by johnny.castaneda on 31/05/2017.
 */

public class DialogInfo extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("La evaluación docente le permite al estudiante valorar la calidad y el cumplimiento de las actividades académicas, además permite al profesor información de cómo el estudiante responde a su metodología de trabajo, mejorar la relación entre ambos y la importancia de su curso dentro del plan de estudios del estudiante.\n" +
                "\n" +
                "La evaluación consta de 2 partes: La primera tiene 20 preguntas para evaluar al profesor en: conocimiento, metodología, relación con los estudiantes y manejo de la evaluación. La segunda tiene 6 preguntas que corresponden a la evaluación del curso.\n" +
                "\n" +
                "La Facultad garantiza confiabilidad en el proceso de evaluación. La información registrada por cada estudiante se realiza en forma anónima para lograr un análisis del docente y del curso. LA CÉDULA SE SOLICITA SOLO PARA VERIFICAR QUE EL ESTUDIANTE ESTE MATRICULADO EN EL CURSO QUE SE ESTA EVALUANDO Y QUE NO ELABORE MAS DE UNA EVALUACIÓN DEL CURSO. LOS DATOS QUE SE PROCESAN ESTADÍSTICAMENTE NO INCLUYEN LA CÉDULA DEL ESTUDIANTE.\n"+
                "\nESCALA VALORACIÓN: Calificar de 1 a 5, siendo 1 muy deficiente, 5 muy bueno, NR No Responde")
                .setTitle("Señor Estudiante")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getActivity(),"Se selecciono Aceptar",Toast.LENGTH_SHORT).show();
                    }
                });
        return builder.create();
    }
}
