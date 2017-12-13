package latitude_pc.app.prueba1.com.prueba1.Models;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Luis Garcia on 29/10/2017.
 */

public class ServicesModel {
    private int service_id;
    private String service;
    private String autor;
    private String Descripcion;
    private Date fecha;
    SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy");

    public ServicesModel(int service_id, String service, String autor, String Descripcion, Date fecha) {
        this.service_id = service_id;
        this.service = service;
        this.autor = autor;
        this.Descripcion = Descripcion;
        this.fecha = fecha;
    }

    public long getIdService() {
        return service_id;
    }

    public void setIdService(int service_id) {
        this.service_id = service_id;
    }

    public String getServiceName() {
        return service;
    }

    public void setServiceName(String service) {
        this.service = service;
    }

    public String getDescription() {
        return Descripcion;
    }

    public void setDescription(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public String getUsername() {
        return autor;
    }

    public void setUsername(String autor) {
        this.autor = autor;
    }

    public Date getDate() {
        return fecha;
    }
    public String getDateString() {
        return format.format(fecha);
    }
    public void setDate(Date fecha) {
        this.fecha = fecha;
    }

}
