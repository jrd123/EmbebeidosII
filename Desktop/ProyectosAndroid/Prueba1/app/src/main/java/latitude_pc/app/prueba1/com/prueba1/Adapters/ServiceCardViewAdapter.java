package latitude_pc.app.prueba1.com.prueba1.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import latitude_pc.app.prueba1.com.prueba1.Models.ServicesModel;
import latitude_pc.app.prueba1.com.prueba1.ProjectDetail;
import latitude_pc.app.prueba1.com.prueba1.R;

/**
 * Created by Luis Garcia on 29/10/2017.
 */

public class ServiceCardViewAdapter extends RecyclerView.Adapter<ServiceCardViewAdapter.ServiceViewHolder>{
    private ArrayList<ServicesModel> services;        // Albums a mostrar en la actividad
    private int resourse;                   // Vista donde de desplegara la lista de bandas (cards)
    private Activity activity;

    public ServiceCardViewAdapter(ArrayList<ServicesModel> services, int resourse, Activity activity) {
        this.services   = services;
        this.resourse   = resourse;
        this.activity   = activity;
    }
    @Override
    public ServiceCardViewAdapter.ServiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View card = LayoutInflater.from(parent.getContext()).inflate(resourse, parent, false);
        return new ServiceCardViewAdapter.ServiceViewHolder(card);
    }

    @Override
    public void onBindViewHolder(ServiceCardViewAdapter.ServiceViewHolder holder, int position) {
        ServicesModel service = services.get(position);
        holder.LastDate.setText(service.getLastDateString());
        holder.ServiceName.setText(service.getServiceName());
        holder.Username.setText(service.getUsername());
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    class ServiceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView LastDate;
        private TextView ServiceName;
        private TextView Username;

        public ServiceViewHolder(View card) { // Recibe la tarjeta (CardView)
            super(card);
            card.setOnClickListener(this);
            this.LastDate = (TextView) card.findViewById(R.id.LastDate);
            this.ServiceName   = (TextView)card.findViewById(R.id.ServiceName);
            this.Username   = (TextView)card.findViewById(R.id.UserAut);
        }
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(activity,ProjectDetail.class);
            intent.putExtra("position",getPosition());
            activity.startActivity(intent);
        }
    }
}
