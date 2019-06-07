package sv.edu.ues.fia.eisi.grupo06tarea2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {
    private ImageView fotoImagenView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fotoImagenView = (ImageView) findViewById(R.id.imagenInicio);

        String url="https://media1.tenor.com/images/1bc8809ba3844ca9638f37e11d2fe172/tenor.gif?itemid=9138694";

        Glide.with(this)
                .load(url)
                .into(fotoImagenView);
    }

    public  void iniciarPedido(View v)
    {
        Intent ints = new Intent(this,MenuPrincipal.class);
        startActivity(ints);


    }
}
