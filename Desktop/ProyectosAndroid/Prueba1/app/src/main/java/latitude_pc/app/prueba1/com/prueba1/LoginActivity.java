package latitude_pc.app.prueba1.com.prueba1;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


//Clae base para las funciones que utilizan la ActionBar de la biblioteca
public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //Se define la interfaz de usuario
        setContentView(R.layout.activity_login);

        //Se obtienen los datos del edittext de login
        final EditText etUserName = (EditText) findViewById(R.id.etUserName);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);


        final Button btnLogin = (Button) findViewById(R.id.btnLogin);
        final TextView registerLink = (TextView) findViewById((R.id.tvRegister));

        //Se inicializa el evento para detectar clics en el link de registro
        registerLink.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //Se crea el intent para redireccionarnos a la actividad de registro
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });
        //Se inicia el evento para detectar clics en el botn de login
        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Se obtienen los valores de los editText
                final String username = etUserName.getText().toString();
                final String password = etPassword.getText().toString();

                //Se instancia el ResponseListener para detectar respuestas
                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    //Se detecta la respuesta
                    public void onResponse(String response){
                        try{

                            JSONObject jsonResponse = new JSONObject(response);
                            //Traigo el valor del campo success del objeto JSON
                            boolean success = jsonResponse.getBoolean("success");

                            //si el campo success es true
                            if(success){
                                 //Se obtienen los valores de la respuesta
                                String name = jsonResponse.getString("name");
                                int age = jsonResponse.getInt("age");

                                //Se crea un nuevo intent de UserArea y se envian las variables nombre, usuario y edad

                                /*
                                intent.putExtra("name", name);
                                intent.putExtra("username", username);
                                intent.putExtra("age", age);
                                */
                                Intent intent = new Intent(LoginActivity.this, ProjectsView.class);
                                LoginActivity.this.startActivity(intent);

                            }else{
                                //En caso de que el campo sea falso se muestra un error
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Login Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                                }
                        }catch(JSONException e){
                                e.printStackTrace();
                        }

                    }
                };

                //Se crea un objeto de tipo LoginRequest con los parametros usuario, contraseña y responseListener
                LoginRequest loginRequest = new LoginRequest(username, password, responseListener);

                //Enviar a la cola la peticion
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);

            }
        });
    }
}
