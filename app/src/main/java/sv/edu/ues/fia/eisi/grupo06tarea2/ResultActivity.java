package sv.edu.ues.fia.eisi.grupo06tarea2;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ResultActivity extends AppCompatActivity
{
    ImageView imagenreloj,imagentel,imagencelebra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        imagenreloj = (ImageView) findViewById(R.id.imagenReloj);
        imagentel =(ImageView) findViewById(R.id.imagentelefono);
        imagencelebra=(ImageView) findViewById(R.id.imagencelebra);

        //para el reloj
        String url1="https://cdn.dribbble.com/users/634508/screenshots/3205975/stoptheclocks2.gif";
        //para el telefono
        String url2="https://media.tenor.com/images/3dd1f33058dd133efc57881471f36d8e/tenor.gif";
        //para los globos
        String url3="https://www.gifsanimados.org/data/media/563/globo-y-bomba-imagen-animada-0042.gif";

        Glide.with(this)
                .load(url1)
                .into(imagenreloj);

        Glide.with(this)
                .load(url2)
                .into(imagentel);

        Glide.with(this)
                .load(url3)
                .into(imagencelebra);
    }
}
