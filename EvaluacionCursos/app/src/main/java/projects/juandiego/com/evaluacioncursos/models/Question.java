package projects.juandiego.com.evaluacioncursos.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Juan Diego on 12/04/2017.
 */
@DatabaseTable
public class Question {
    @DatabaseField(id = true)
    private String number;
    @DatabaseField
    private String type;
    @DatabaseField
    private String description;

    private String answer;

    public Question(){}

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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
