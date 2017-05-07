package co.edu.udea.notasudea.model;

import java.util.List;

/** 
 * DTO para el transporte de información sobre una materia.
 * @author Andres Felipe Arrubla Z. <ax.fx.ax@gmail.com>
 * @version 1.0 30/10/2013
 */
public class Materia {
	
	/**
	 * Codigo de la materia.
	 */
	private String codigo;
	/**
	 * Nombre de la materia.
	 */
	private String nombre;
	/**
	 * Nota obtenida por el estudiante en la materia.
	 */
	private String nota;
	/**
	 * Cantidad de creditos que posee la materia.
	 */
	private String creditos;
	/**
	 * Listado de las notas obtenidas en un materia por el estudiante
	 */
	private List<Evaluacion> notas;
	
	public Materia(String nombre, String nota, String creditos) {
		this.nombre = nombre;
		this.nota = nota;
		this.creditos = creditos;
	}
	
	public Materia() {}
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

	public String getCreditos() {
		return creditos;
	}

	public void setCreditos(String creditos) {
		this.creditos = creditos;
	}	
	
	public List<Evaluacion> getNotas() {
		return notas;
	}
	
	public void setNotas(List<Evaluacion> notas) {
		this.notas = notas;
	}	
	
}