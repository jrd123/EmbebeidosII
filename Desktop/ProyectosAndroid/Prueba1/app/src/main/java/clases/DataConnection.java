package clases;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by Latitude-PC on 29/10/2017.
 */

public class DataConnection extends AppCompatActivity
{
    String funcion, encodedImage, data, image, cargarDatos;
    Activity context;
    ArrayList<DatosImage> listaImage = new ArrayList();
    DatosImage dataImage;

    public DataConnection (Activity context, String funcion, String encodedImage) {
        this.context=context;
        this.funcion = funcion;
        this.encodedImage = encodedImage;

        //Una vez que invoquemos a la clase dataconnection estamos invocando al metodo
        //constructor de esta clase para que inicialice los siguientes objetos y ejecute la clase
        //asincronica, clase que va a ejecutar el metodo filtrardatos
        //el cual va a ejecutar el metodo obtenerdatos que nos retorna los
        //datos de nuestro webservice

        new GetAndSet().execute();
    }
//envia y recibe datos de nuestro webservice
    private String obtenerDatos(){
        StringBuffer response = null;
        //Se manda el POST  al webservice

        try {
            URL obj = new URL("https://jrd123.000webhostapp.com/BlogServicios/WebServiceImg/WebService.php");
            //nombre de la variable de tipo POST que va a capturar el dato que enviemos desde nuestra aplicacion
            if (funcion.equals("setImage")) {
                //Los dos parametros POST que se envian
                data = "funcion=" + URLEncoder.encode(funcion, "UTF-8")
                        + "&image=" + URLEncoder.encode(encodedImage, "UTF-8");
            }
            //abrimos nuestra conexion a nuestro webservice
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("POST");

            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            con.setDoOutput(true);

            //Enviamos datos
            //Longitud previamente conocida
            con.setFixedLengthStreamingMode(data.getBytes().length);

            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            //la clase implementa un flujo de salida con buffer
            //Estamos enviando los datos por POST  a webservice
            OutputStream out = new BufferedOutputStream(con.getOutputStream());
            out.write(data.getBytes());
            out.flush();
            out.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            response = new StringBuffer();
            while ((inputLine = in.readLine()) != null){
                response.append(inputLine);

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (ProtocolException e) {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    return response.toString();

    }
    //Si este retorna tru ejecutamos el siguiente hilo
    //el cual es la actividad() que mostrara un mensaje
    private boolean filtrarDatos(){
        String patron1 = "<>";
        cargarDatos = obtenerDatos();
        if(!cargarDatos.equalsIgnoreCase("")){
            Pattern p1 = Pattern.compile(patron1);
            if (funcion.equals("setImage")) {
                String[] items = p1.split(cargarDatos);
                image = items[1];
                for(int i = 0; i< items.length; i++){
                    items[i] = null;
                }
            }
            return true;
        }
        return false;
    }
    private void actividad(){
        if(funcion.equals("setImage")){
            if(image.equals("insert")){
                Toast.makeText(context, "Imagen insertada al servidor ", Toast.LENGTH_LONG).show();
            }
        }
    }

    class GetAndSet extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params){
            if(filtrarDatos()){
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        actividad();
                    }
                });
            }
            return null;
        }
    }
}
