package co.edu.udea.notasudea.util.encrypt;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Esta clase implementa el metodo de cifrado AES para
 * cifrar y descifrar cadenas de texto
 * @author David Rivera Zapata <daverivera90@gmail.com>
 */
public class AESEncryption implements IEncryption{
    byte[] key = "<PONER_CLAVE_PARA_CIFRADO>".getBytes();

    /**
     * * Cifra la cadena ingresada
     * @param cadena
     *        Cadena a cifrar
     * @return
     *        Cadena Cifrada
     */
    public String cifrar(String cadena){
        byte[] dataToSend = cadena.getBytes();
        String salida = "";

        try {
            Cipher c = Cipher.getInstance("AES");
            SecretKeySpec k = new SecretKeySpec(key, "AES");

            c.init(Cipher.ENCRYPT_MODE, k);
            byte[] encryptedData = c.doFinal(dataToSend);

            salida = new String(encryptedData, "ISO-8859-1");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return salida;
    }

    /**
     * Descifra una cadena cifrada
     * @param cadena
     *      Cadena cifrada
     * @return
     *      Cadena descifrada
     */
    public String descifrar(String cadena){
        String salida = "";

        try {
            byte[] encryptedData = cadena.getBytes("ISO-8859-1");
            Cipher c = Cipher.getInstance("AES");
            SecretKeySpec k = new SecretKeySpec(key, "AES");

            c.init(Cipher.DECRYPT_MODE, k);
            byte[] data = c.doFinal(encryptedData);

            salida = new String(data);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return salida;
    }

}
