package projects.juandiego.com.evaluacioncursos.models;

/**
 * Created by Juan Diego on 12/04/2017.
 */

public class Question {
    private String number;
    private String type;
    private String description;
    private String answer;

    public Question(String description) {
        this.description = description;
    }

    public Question(String type, String description, String answer) {
        this.type = type;
        this.description = description;
        this.answer = answer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
