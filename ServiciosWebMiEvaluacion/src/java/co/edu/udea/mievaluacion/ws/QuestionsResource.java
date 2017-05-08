package co.edu.udea.mievaluacion.ws;

import co.edu.udea.mievaluacion.bl.QuestionBL;
import co.edu.udea.mievaluacion.model.Question;
import java.util.List;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Juan Diego
 */
@Path("/questions")
public class QuestionsResource {

    QuestionBL questionBL = new QuestionBL();
    
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<Question> getQuestions() {
        return questionBL.getAllQuestions();
    }
}
