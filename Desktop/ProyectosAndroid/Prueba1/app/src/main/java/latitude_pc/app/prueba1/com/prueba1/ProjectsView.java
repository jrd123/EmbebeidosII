package latitude_pc.app.prueba1.com.prueba1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import latitude_pc.app.prueba1.com.prueba1.Adapters.ServiceCardViewAdapter;
import latitude_pc.app.prueba1.com.prueba1.Models.ServicesModel;
import latitude_pc.app.prueba1.com.prueba1.Models.UserModel;

public class ProjectsView extends AppCompatActivity {
    ProgressDialog progress;
    private boolean Close = false;
    SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy");
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    public String User;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects_view);
        getSupportActionBar().setTitle("Servicios");
        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        progress = new ProgressDialog(ProjectsView.this);
        progress.setCancelable(false);
        progress.setIndeterminate(true);
        progress.setMessage("Espere, porfavor....");
        addDrawerItems();
        setupDrawer();


        getServices();



    }

    @Override
    public void onBackPressed() {
        if(Close)
            moveTaskToBack(true);
        this.Close=true;
        Toast.makeText(this, "Pulse una vez más para salir", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Close=false;
            }
        }, 2000);
    }
    public void newService(View view){
        Intent intent = new Intent(this, ProjectRegister.class);
        startActivity(intent);
    }

    public void logout(){
        super.onBackPressed();
        ProjectsView.this.finish();
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
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ProjectsView.this);
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    RecyclerView servicesRecycler = (RecyclerView) findViewById(R.id.servicesRecyclerView);

                    servicesRecycler.setLayoutManager(linearLayoutManager);
                    ServiceCardViewAdapter serviceAdapter = new ServiceCardViewAdapter(services, R.layout.cardviewservices, ProjectsView.this);

                    servicesRecycler.setAdapter(serviceAdapter);
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
        RequestQueue queue = Volley.newRequestQueue(ProjectsView.this);
        queue.add(ProjectsRequest);
    }

    private void addDrawerItems() {
        String[] osArray = { "Perfil", "Cerrar sesión" };
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    Intent registerIntent = new Intent(ProjectsView.this, RegisterActivity.class);
                    registerIntent.putExtra("User",User);
                    ProjectsView.this.startActivity(registerIntent);
                }
                else
                {
                    logout();
                }
            }
        });
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Opciones");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle("Proyecto embebidos");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


}
