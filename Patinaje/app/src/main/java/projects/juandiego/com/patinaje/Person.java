package projects.juandiego.com.patinaje;

/**
 * Created by Juan Diego on 21/12/2016.
 */

public class Person {
    private String name;
    private String lastName;
    private String document;
    private String birthday;
    private String dateStart;
    private String status;
    private String age;
    private String months;

    public Person(String name, String lastName, String document, String birthday, String dateStart, String status, String age, String months) {
        this.name = name;
        this.lastName = lastName;
        this.document = document;
        this.birthday = birthday;
        this.dateStart = dateStart;
        this.status = status;
        this.age = age;
        this.months = months;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getMonths() {
        return months;
    }

    public void setMonths(String months) {
        this.months = months;
    }
}
