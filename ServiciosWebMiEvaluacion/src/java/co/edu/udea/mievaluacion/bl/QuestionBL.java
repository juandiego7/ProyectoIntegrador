package co.edu.udea.mievaluacion.bl;

import co.edu.udea.mievaluacion.model.Professor;
import co.edu.udea.mievaluacion.model.Question;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Juan Diego
 */
public class QuestionBL {
    
    public List<Question> getAllQuestions(){
       Question question1 = new Question("1","Seguridad en exposiciones");
       Question question2 = new Question("2","Respuesta clara y acertada a preguntas");
       Question question3 = new Question("3","Dominio de los temas del curso o actividad curricular");
       
       
       List<Question> questions = new ArrayList<>();
       questions.add(question1);
       questions.add(question2);
       questions.add(question3);
       
       return questions;
    }
}
