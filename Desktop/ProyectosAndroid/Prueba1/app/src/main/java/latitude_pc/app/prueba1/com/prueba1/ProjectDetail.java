package latitude_pc.app.prueba1.com.prueba1;

import android.app.ProgressDialog;
import android.app.Service;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;

import latitude_pc.app.prueba1.com.prueba1.Adapters.ImagesViewPagerAdapter;
import latitude_pc.app.prueba1.com.prueba1.Adapters.ServiceCardViewAdapter;
import latitude_pc.app.prueba1.com.prueba1.Models.ImagesModel;
import latitude_pc.app.prueba1.com.prueba1.Models.ServicesModel;
import latitude_pc.app.prueba1.com.prueba1.Models.UserModel;



public class ProjectDetail extends AppCompatActivity {
    ProgressDialog progress;
    SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy");
    ArrayList<ImagesModel> images = new ArrayList<ImagesModel>();
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detail);

        position = getIntent().getIntExtra("position",-1);



        ViewPager vpImages = (ViewPager)findViewById(R.id.vpImages);
        progress = new ProgressDialog(ProjectDetail.this);
        progress.setCancelable(false);
        progress.setIndeterminate(true);
        progress.setMessage("Espere, porfavor....");
        ArrayList<ServicesModel> services = new ArrayList<ServicesModel>();
        getServices();


        images = getImages();
        ImagesViewPagerAdapter adapter = new ImagesViewPagerAdapter(images,this);
        vpImages.setAdapter(adapter);

    }

    public void getServices(){
        final ArrayList<ServicesModel> services = new ArrayList<>();
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            //Se detecta la respuesta
            public void onResponse(String response) {
                try {
                    progress.show();
                    JSONObject jsonResponse = new JSONObject(response);
                    Iterator x = jsonResponse.keys();
                    JSONArray jsonArray = new JSONArray();

                    while (x.hasNext()){
                        String key = (String) x.next();
                        jsonArray.put(jsonResponse.get(key));
                    }
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-mm-dd");

                    for(int i=0; i < jsonArray.length() ; i++) {
                        JSONObject json_data = jsonArray.getJSONObject(i);
                        try {
                            Date convertedDate = new Date();
                            convertedDate = dateFormat.parse(json_data.getString("fecha"));
                            ServicesModel service = new ServicesModel(json_data.getInt("service_id"),json_data.getString("service"),json_data.getString("autor"),json_data.getString("Descripcion"),convertedDate);
                            services.add(service);
                        }
                        catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    CollapsingToolbarLayout toolbar = (CollapsingToolbarLayout) findViewById(R.id.Collapsable);

                    toolbar.setTitle(services.get(position).getServiceName());
                    TextView tvDate = (TextView)findViewById(R.id.ProjectDate);
                    TextView tvDesc = (TextView)findViewById(R.id.Desc);
                    TextView tvAutor = (TextView)findViewById(R.id.txtAutor);
                    tvDate.setText(services.get(position).getDateString());
                    tvDesc.setText(services.get(position).getDescription());
                    tvAutor.setText(services.get(position).getUsername());
                    progress.dismiss();
                    //si el campo success es true
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        //Se crea un objeto de tipo LoginRequest con los parametros usuario, contraseña y responseListener
        ProjectsRequest ProjectsRequest = new ProjectsRequest(responseListener);

        //Enviar a la cola la peticion
        RequestQueue queue = Volley.newRequestQueue(ProjectDetail.this);
        queue.add(ProjectsRequest);
    }

    public ArrayList<ImagesModel> getImages(){
        images.add(new ImagesModel(R.drawable.logo,"27/10/2017"));
        images.add(new ImagesModel(R.drawable.icon_plus_big,"28/10/2017"));
        images.add(new ImagesModel(R.drawable.turn_off,"29/10/2017"));

        return images;
    }
}