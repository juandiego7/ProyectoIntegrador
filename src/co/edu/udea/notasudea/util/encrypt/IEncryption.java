package co.edu.udea.notasudea.util.encrypt;

public interface IEncryption {

	/**
     * Metodo para cifrar una cadena de texto.
     * Retorna la cadena cifrada
     * @param cadena Cadena a cifrar
     * @return Cadena cifrada
     */
    public String cifrar(String cadena);

    /**
     * Metodo para descifrar una cadena de texto.
     * Retorna la cadena descifrada
     * @param cadena Cadena a descifrar
     * @return cadena descifrada
     */
    public String descifrar(String cadena);
}
