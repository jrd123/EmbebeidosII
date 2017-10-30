package latitude_pc.app.prueba1.com.prueba1;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import java.io.ByteArrayOutputStream;
import clases.DataConnection;

public class ImageUploadActivity extends AppCompatActivity {
    ImageView img;
    private final int PICKER = 1;
    String encodedImage, foto, funcion;
    DataConnection dc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_upload);

       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
            }
        });*/
        img = (ImageView) findViewById(R.id.imgUpload);
        //img.setOnClickListener(this);
        img.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                PickFile();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.action_send){
            funcion = "setImage";
            //,,imagen convertida en bytes para poder insertar en BD
            dc = new DataConnection(this, funcion, encodedImage);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void PickFile(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try
        {
            startActivityForResult(
                    Intent.createChooser(intent, "Instale un administrador de archivos"),
                    PICKER);
        }catch(android.content.ActivityNotFoundException ex)
        {

        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch(requestCode){
            case PICKER:
                if (resultCode == RESULT_OK) {
                    foto = "foto";
                    Bitmap photobmp;
                    Uri selectedImageUri = data.getData();

                    //Retornara una cadena de texto que es la url exacta donde esta nuestra imagen que hemos seleccionado en nuestro dispositivo
                    String dataFU = getRealPathFromURI(selectedImageUri);
                    photobmp = BitmapFactory.decodeFile(dataFU);
                    img.setImageBitmap(photobmp);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();

                    //Factor de compresion
                    photobmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte[] imageBytes = baos.toByteArray();
                    //Convertir los datos de tipo byte a tipo base
                    encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                }
                break;
        }
    }
    //Uri seleccionada de nuestro dispositivo movil
    public String getRealPathFromURI(Uri contentUri){
        //Obtener la ruta fisica de la imagen que hemos sleccionado
        Cursor cursor = null;
        try
        {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = getApplicationContext().getContentResolver().query(contentUri,proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
