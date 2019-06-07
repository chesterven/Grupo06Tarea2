package sv.edu.ues.fia.eisi.grupo06tarea2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
public class MenuPrincipal extends AppCompatActivity {
private ImageView imagenPedido;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        imagenPedido = (ImageView) findViewById(R.id.imagenPedido);

        String url="https://prodimages.restaurants-sign.com/350/l717865-pupusas-animated-led-sign.gif";

        Glide.with(this)
                .load(url)
                .into(imagenPedido);

    }
}
