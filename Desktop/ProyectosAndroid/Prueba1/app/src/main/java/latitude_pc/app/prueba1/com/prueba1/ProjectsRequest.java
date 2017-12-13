package latitude_pc.app.prueba1.com.prueba1;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Luis Garcia on 05/12/2017.
 */

public class ProjectsRequest extends StringRequest {
    private static final String REGISTER_REQUEST_PROJECT_URL = "http://jrd123.000webhostapp.com/ConsultaServicios.php";
    private Map<String, String> params;



    public ProjectsRequest(Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_PROJECT_URL, listener, null);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
