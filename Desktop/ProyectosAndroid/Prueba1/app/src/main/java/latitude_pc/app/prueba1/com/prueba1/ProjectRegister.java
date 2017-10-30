package latitude_pc.app.prueba1.com.prueba1;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ProjectRegister extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_register);
        final Button bRegister = (Button) findViewById(R.id.btnRegister);
        final EditText etService = (EditText) findViewById(R.id.etService);
        final EditText etAutor = (EditText) findViewById(R.id.etAutor);
        final EditText etDesc = (EditText) findViewById(R.id.etDesc);
        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String service = etService.getText().toString();
                final String autor = etAutor.getText().toString();
                final String Desc = etDesc.getText().toString();
                final Date LastDate = Calendar.getInstance().getTime();


            }
        });
    }
}
