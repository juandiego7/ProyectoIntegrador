package projects.juandiego.com.evaluacioncursos.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by rantonio.martinez on 02/06/2017.
 */

@DatabaseTable
public class Ver {
    @DatabaseField(id = true)
    private String materia;
    @DatabaseField
    private String puede;

    public Ver() {    }

    public Ver(String materia, String puede) {
        this.materia = materia;
        this.puede = puede;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getPuede() {
        return puede;
    }

    public void setPuede(String puede) {
        this.puede = puede;
    }
}
