package latitude_pc.app.prueba1.com.prueba1;

//import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by HP Compaq on 02/12/2017.
 */

public class RegisterProyectRequest extends StringRequest {
    private static final String REGISTER_REQUEST_PROJECT_URL = "http://jrd123.000webhostapp.com/Register_Services.php";
    private Map<String, String> params;



    public RegisterProyectRequest(String service, String autor, String desc, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_PROJECT_URL, listener, null);
        params = new HashMap<>();
        params.put("service", service);
        params.put("autor", autor);
        params.put("desc", desc);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
