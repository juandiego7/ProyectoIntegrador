package co.edu.udea.notasudea.model;

import java.util.ArrayList;

/**
 * 
 * @author Andres Felipe Arrubla Z. <ax.fx.ax@gmail.com>
 * @version 1.0 3/07/2013
 */
public class Programa {
	
	/**
	 * Nombre del programa
	 */
	private String programa;
	/**
	 * Identificador unico del programa
	 */
	private String codPrograma;
	/**
	 * Listado de semestre cursados en ese programa por el estudiante
	 */
	private ArrayList<Semestre> semestres;
	
	public String getPrograma() {
		return programa;
	}
	
	public void setPrograma(String programa) {
		this.programa = programa;
	}
	
	public String getCodPrograma() {
		return codPrograma;
	}
	
	public void setCodPrograma(String codPrograma) {
		this.codPrograma = codPrograma;
	}
	
	public ArrayList<Semestre> getSemestres() {
		return semestres;
	}
	
	public void setSemestres(ArrayList<Semestre> semestres) {
		this.semestres = semestres;
	}
	
}