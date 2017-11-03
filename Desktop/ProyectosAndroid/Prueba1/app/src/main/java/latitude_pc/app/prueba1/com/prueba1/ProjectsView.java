package latitude_pc.app.prueba1.com.prueba1;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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

import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import latitude_pc.app.prueba1.com.prueba1.Adapters.ServiceCardViewAdapter;
import latitude_pc.app.prueba1.com.prueba1.Models.ServicesModel;
import latitude_pc.app.prueba1.com.prueba1.Models.UserModel;

public class ProjectsView extends AppCompatActivity {
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
        User = getIntent().getStringExtra("User");
        getSupportActionBar().setTitle("Servicios");
        RecyclerView servicesRecycler = (RecyclerView) findViewById(R.id.servicesRecyclerView);
        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        addDrawerItems();
        setupDrawer();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        servicesRecycler.setLayoutManager(linearLayoutManager);
        ArrayList<ServicesModel> services = getServices();

        ServiceCardViewAdapter serviceAdapter = new ServiceCardViewAdapter(services, R.layout.cardviewservices, this);

        servicesRecycler.setAdapter(serviceAdapter);

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

    public ArrayList<ServicesModel> getServices(){
        ArrayList<ServicesModel> services = new ArrayList<>();
        try{
            services.add(new ServicesModel(1, "Reparación 1", "Luis García", "Empezando reparacion",format.parse("29/10/2017")));
            services.add(new ServicesModel(2, "Afinacion", "Luis García", "Segunda afinacion del mes" ,format.parse("25/10/2017")));
            services.add(new ServicesModel(3 ,"Otro", "Jonathan Rdz", "Servicio de emergencia" ,format.parse("29/10/2017")));
            services.add(new ServicesModel(4, "Mantenimiento", "Jared A.", "Valoracion y reparaciones mensuales",format.parse("16/10/2017")));
            services.add(new ServicesModel(5, "General", "Campos", "Chequeo general",format.parse("3/10/2017")));
            services.add(new ServicesModel(5, "General", "Campos", "Chequeo general",format.parse("3/09/2017")));
            services.add(new ServicesModel(5, "General", "Jonathan Rdx", "Chequeo general",format.parse("3/09/2017")));
            Collections.sort(services, new Comparator<ServicesModel>() {
                public int compare(ServicesModel m1, ServicesModel m2) {
                    return m1.getLastDate().compareTo(m2.getLastDate());
                }
            });
        }
        catch (ParseException e)
        {
            Toast.makeText(this,e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return services;
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
