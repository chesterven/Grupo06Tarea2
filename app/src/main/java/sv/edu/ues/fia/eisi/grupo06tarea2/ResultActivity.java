package sv.edu.ues.fia.eisi.grupo06tarea2;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ResultActivity extends AppCompatActivity
{
    ImageView imagenreloj,imagentel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        imagenreloj = (ImageView) findViewById(R.id.imagenReloj);
        imagentel =(ImageView) findViewById(R.id.imagentelefono);

        //para el reloj
        String url1="https://cdn.dribbble.com/users/634508/screenshots/3205975/stoptheclocks2.gif";
        //para el telefono
        String url2="https://media3.giphy.com/media/YMugCc0jF59h9AaPPH/giphy.gif?cid=790b76115cfc0bbb366c3269734fd2b8&rid=giphy.gif";

        Glide.with(this)
                .load(url1)
                .into(imagenreloj);

        Glide.with(this)
                .load(url2)
                .into(imagentel);
    }
}
