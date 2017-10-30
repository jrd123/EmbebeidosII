package latitude_pc.app.prueba1.com.prueba1;

import android.app.Service;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import latitude_pc.app.prueba1.com.prueba1.Models.ServicesModel;
import latitude_pc.app.prueba1.com.prueba1.Models.UserModel;

public class ProjectDetail extends AppCompatActivity {
    SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detail);

        int position = getIntent().getIntExtra("position",-1);


        TextView tvDate = (TextView)findViewById(R.id.ProjectDate);
        TextView tvDesc = (TextView)findViewById(R.id.Desc);
        TextView tvAutor = (TextView)findViewById(R.id.txtAutor);
        ArrayList<ServicesModel> services = new ArrayList<ServicesModel>();
        CollapsingToolbarLayout toolbar = (CollapsingToolbarLayout) findViewById(R.id.Collapsable);
        services = getServices();
        toolbar.setTitle(services.get(position).getServiceName());
        tvDate.setText(services.get(position).getLastDateString());
        tvDesc.setText(services.get(position).getDescription());
        tvAutor.setText(services.get(position).getUsername());
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
}
