package latitude_pc.app.prueba1.com.prueba1.Models;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Luis Garcia on 29/10/2017.
 */

public class ServicesModel {
    private long IdService;
    private String ServiceName;
    private String Username;
    private String Description;
    private Date LastDate;
    SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy");

    public ServicesModel(long IdService, String ServiceName, String Username, String Description, Date LastDate) {
        this.IdService = IdService;
        this.ServiceName = ServiceName;
        this.Username = Username;
        this.Description = Description;
        this.LastDate = LastDate;
    }

    public long getIdService() {
        return IdService;
    }

    public void setIdService(long IdService) {
        this.IdService = IdService;
    }

    public String getServiceName() {
        return ServiceName;
    }

    public void setServiceName(String ServiceName) {
        this.ServiceName = ServiceName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String ServiceName) {
        this.Description = Description;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public Date getLastDate() {
        return LastDate;
    }
    public String getLastDateString() {
        return format.format(LastDate);
    }
    public void setLastDate(Date LastDate) {
        this.LastDate = LastDate;
    }

}
