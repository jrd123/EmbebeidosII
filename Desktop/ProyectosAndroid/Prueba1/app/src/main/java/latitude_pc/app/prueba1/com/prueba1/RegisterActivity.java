package latitude_pc.app.prueba1.com.prueba1;

        import android.app.ProgressDialog;
        import android.content.Intent;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.Button;

        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.toolbox.Volley;

        import org.json.JSONException;
        import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        progress = new ProgressDialog(RegisterActivity.this);
        progress.setCancelable(false);
        progress.setIndeterminate(true);
        progress.setMessage("Espere, porfavor....");
        final EditText etAge = (EditText) findViewById(R.id.etAge);
        final EditText etName = (EditText) findViewById(R.id.etName);
        final EditText etUsername = (EditText) findViewById(R.id.etUserName);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final Button bRegister = (Button) findViewById(R.id.btnRegister);




        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                progress.show();
                final String name = etName.getText().toString();
                final String username = etUsername.getText().toString();
                final String ageTxt = etAge.getText().toString();

                final String password = etPassword.getText().toString();
                if (name.isEmpty() || username.isEmpty() || password.isEmpty() || ageTxt.isEmpty()) { //Validamos los campos para el acceso
                    String mensaje = "Ingrese porfavor: \n\n";
                    if (name.isEmpty())
                        mensaje += "- Nombre del usuario \n";
                    if (username.isEmpty())
                        mensaje += "- Usuario \n";
                    if (ageTxt.isEmpty() ||Integer.parseInt(etAge.getText().toString())<=0 )
                        mensaje += "- Edad \n";
                    if (password.isEmpty())
                        mensaje += "- Contraseña \n";
                    progress.dismiss();
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage(mensaje)
                            .setNegativeButton("Aceptar", null)
                            .create()
                            .show();
                }
                else {

                    final int age = Integer.parseInt(etAge.getText().toString());
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                progress.dismiss();
                                if (success) {
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    RegisterActivity.this.startActivity(intent);
                                }
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage(jsonResponse.getString("Mensaje"))
                                        .setNegativeButton("Aceptar", null)
                                        .setCancelable(false)
                                        .create()
                                        .show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    RegisterRequest registerRequest = new RegisterRequest(name, username, age, password, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                    queue.add(registerRequest);
                }
            }
        });
    }
}