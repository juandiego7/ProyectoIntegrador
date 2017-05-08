package co.edu.udea.mievaluacion.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Juan Diego
 */
@XmlRootElement
public class Question {
    private String number;
    private String type;
    private String description;
    private String answer;

    public Question(String number, String type, String description, String answer) {
        this.number = number;
        this.type = type;
        this.description = description;
        this.answer = answer;
    }

    public Question(String number, String description) {
        this.number = number;
        this.description = description;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}
