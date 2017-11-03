package latitude_pc.app.prueba1.com.prueba1.Models;

/**
 * Created by Luis Garcia on 30/10/2017.
 */

public class ImagesModel {
    public int IdImage;
    public String Date;

    public ImagesModel(int IdImage, String Date) {
        this.IdImage = IdImage;
        this.Date = Date;
    }

    public int getIdImage(){
        return IdImage;
    }

    public void setIdImage(int IdImage){
        this.IdImage = IdImage;
    }

    public String getUrl(){
        return Date;
    }

    public void setUrl(String Date){
        this.Date = Date;
    }
}
