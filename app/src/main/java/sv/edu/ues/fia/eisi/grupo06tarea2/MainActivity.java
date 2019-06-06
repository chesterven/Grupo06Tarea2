package sv.edu.ues.fia.eisi.grupo06tarea2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public  void iniciarPedido(View v)
    {
        Intent ints = new Intent(this,MenuPrincipal.class);
        startActivity(ints);


    }
}
