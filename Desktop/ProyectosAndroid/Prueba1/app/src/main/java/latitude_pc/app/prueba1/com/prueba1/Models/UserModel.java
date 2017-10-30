package latitude_pc.app.prueba1.com.prueba1.Models;

/**
 * Created by Luis Garcia on 30/10/2017.
 */

public class UserModel
{
    private long IdUser;
    private String Username;
    private String Name;
    private String Email;
    private String Password;
    private int Age;

    public UserModel(long IdUser, String Username, String Name, String Email, String Password, int Age) {
        this.IdUser = IdUser;
        this.Username = Username;
        this.Name = Name;
        this.Email = Email;
        this.Password = Password;
        this.Age = Age;
    }

    public long getIdUser(){
        return IdUser;
    }

    public void setIdUser(long IdUser){
        this.IdUser = IdUser;
    }

    public String getUsername(){
        return Username;
    }
    public void setUsername(String Username){
        this.Username = Username;
    }

    public String getName(){
        return Username;
    }
    public void setName(String Name){
        this.Name = Name;
    }
    public String getEmail(){
        return Email;
    }
    public void setEmail(String Email){
        this.Email = Email;
    }

    public int getAge()
    {
        return Age;
    }

    public void setAge(int Age){
        this.Age = Age;
    }

    public String getPassword(){
        return Password;
    }
    public void setPassword(String Password){
        this.Password = Password;
    }
}

