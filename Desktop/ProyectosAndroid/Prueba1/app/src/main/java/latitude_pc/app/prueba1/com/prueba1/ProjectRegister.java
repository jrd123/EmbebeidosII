package latitude_pc.app.prueba1.com.prueba1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class ProjectRegister extends AppCompatActivity {
    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_register);
        progress = new ProgressDialog(ProjectRegister.this);
        progress.setCancelable(false);
        progress.setIndeterminate(true);
        progress.setMessage("Espere, porfavor....");
        final EditText etService = (EditText) findViewById(R.id.etService);
        final EditText etAutor = (EditText) findViewById(R.id.etAutor);
        final EditText etDesc = (EditText) findViewById(R.id.etDesc);
        final Button bRegister = (Button) findViewById(R.id.btnRegister);


        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String service = etService.getText().toString();
                final String autor = etAutor.getText().toString();
                final String desc = etDesc.getText().toString();
                progress.show();
                if (service.isEmpty() || autor.isEmpty() || desc.isEmpty()) { //Validamos los campos para el registro
                    String mensaje = "Ingrese porfavor: \n\n";
                    if (service.isEmpty())
                        mensaje += "- Servicio \n";
                    if (autor.isEmpty())
                        mensaje += "- Autor \n";
                    if (desc.isEmpty())
                        mensaje += "- Descripción \n";
                    progress.dismiss();
                    AlertDialog.Builder builder = new AlertDialog.Builder(ProjectRegister.this);
                    builder.setMessage(mensaje)
                            .setNegativeButton("Aceptar", null)
                            .setCancelable(false)
                            .create()
                            .show();
                }
                else {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("Exito");
                                progress.dismiss();
                                if (success) {
                                    Toast.makeText(ProjectRegister.this,jsonResponse.getString("Mensaje"),Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(ProjectRegister.this, ProjectsView.class);
                                    ProjectRegister.this.startActivity(intent);
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ProjectRegister.this);
                                    builder.setMessage(jsonResponse.getString("Mensaje"))
                                            .setNegativeButton("Aceptar", null)
                                            .create()
                                            .show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    RegisterProyectRequest registerRequest = new RegisterProyectRequest(service, autor, desc, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(ProjectRegister.this);
                    queue.add(registerRequest);
                }
            }
        });
    }
}
