package sv.edu.ues.fia.eisi.grupo06tarea2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
public class MenuPrincipal extends AppCompatActivity {
private ImageView imagenPedido;
private EditText nombre;
private EditText apellido;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        imagenPedido = (ImageView) findViewById(R.id.imagenPedido);
        nombre = (EditText) findViewById(R.id.nombreUsuario);
        apellido=(EditText) findViewById(R.id.apellidoUsuario);

        String url="https://prodimages.restaurants-sign.com/350/l717865-pupusas-animated-led-sign.gif";

        Glide.with(this)
                .load(url)
                .into(imagenPedido);

    }
    public void empezar (View v)
    {
        if (apellido.getText().toString().equals("") | nombre.getText().equals(""))
        {
            Toast.makeText(this, "Debe ingresar su nombre y apellido", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent inte1 = new Intent(this, PupusasMaiz.class);
            startActivity(inte1);
        }
    }
}
