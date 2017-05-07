package co.edu.udea.notasudea.util;

import java.net.SocketTimeoutException;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

/**
 * Clase encargada de proveer un unico cliente http.
 * @author Andres Felipe Arrubla Z. <ax.fx.ax@gmail.com>
 * @version 1.0 4/01/2014
 */
public class HttpClientFactory {

    private final static int TIME_OUT_CONEXION = 20000;
    private final static int TIME_OUT_SOCKET = 25000;
    private static DefaultHttpClient client;
    
    public synchronized static DefaultHttpClient getThreadSafeClient() throws SocketTimeoutException {
    	  
        if (client != null)
            return client;
         
        client = new DefaultHttpClient();
        
        ClientConnectionManager mgr = client.getConnectionManager();
        
        //HttpParams params = client.getParams();
        HttpParams params = getParametros();
        
        client = new DefaultHttpClient(
                new ThreadSafeClientConnManager(params, mgr.getSchemeRegistry()),
                params);
  
        return client;
    }
    
    public static HttpParams getParametros() {
        HttpParams httpParametros = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParametros, TIME_OUT_CONEXION);
        HttpConnectionParams.setSoTimeout(httpParametros, TIME_OUT_SOCKET);
        return httpParametros;
    }

}    