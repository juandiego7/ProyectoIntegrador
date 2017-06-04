package co.edu.udea.mievaluacion.dto;

public class Ver {
	
	private String materia;
	private String puede;
	/**
	 * @param materia
	 * @param puede
	 */
	public Ver(String materia, String puede) {
		super();
		this.materia = materia;
		this.puede = puede;
	}
	/**
	 * 
	 */
	public Ver() {
		super();
	}
	/**
	 * @return the materia
	 */
	public String getMateria() {
		return materia;
	}
	/**
	 * @param materia the materia to set
	 */
	public void setMateria(String materia) {
		this.materia = materia;
	}
	/**
	 * @return the puede
	 */
	public String getPuede() {
		return puede;
	}
	/**
	 * @param puede the puede to set
	 */
	public void setPuede(String puede) {
		this.puede = puede;
	}
	
	
}
