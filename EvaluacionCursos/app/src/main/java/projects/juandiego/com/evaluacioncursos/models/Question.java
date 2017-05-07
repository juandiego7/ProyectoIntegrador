package projects.juandiego.com.evaluacioncursos.models;

/**
 * Created by Juan Diego on 12/04/2017.
 */

public class Question {
    private String type;
    private String description;
    private String question3;

    public Question(String type, String description, String question3) {
        this.type = type;
        this.description = description;
        this.question3 = question3;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQuestion3() {
        return question3;
    }

    public void setQuestion3(String question3) {
        this.question3 = question3;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
