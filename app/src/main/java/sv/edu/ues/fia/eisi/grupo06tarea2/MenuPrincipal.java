package sv.edu.ues.fia.eisi.grupo06tarea2;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MenuPrincipal extends AppCompatActivity {
    private static final int REQUEST_CODE_SPEECH_INPUT=1000;
private ImageView imagenPedido;
private ImageButton mic;
private EditText nombreyApellido;
private CheckBox llevar;
private Button boton,botonGuardar;
    String id="";

//Consulta en cola para webService
RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        imagenPedido = (ImageView) findViewById(R.id.imagenPedido);
        nombreyApellido = (EditText) findViewById(R.id.nombreyApellidoUsuario);
        llevar = (CheckBox) findViewById(R.id.checkBoxLlevar);
        boton = (Button) findViewById(R.id.botonIniciar);
        botonGuardar = (Button) findViewById(R.id.botonGuardar);
        mic=(ImageButton)findViewById(R.id.imgButtonMic);


        String url="https://prodimages.restaurants-sign.com/350/l717865-pupusas-animated-led-sign.gif";

        Glide.with(this)
                .load(url)
                .into(imagenPedido);

        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });
    }

    private void speak() {
        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        getIntent().putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Diga su nombre");
        try{
            startActivityForResult(intent,REQUEST_CODE_SPEECH_INPUT);
        } catch(Exception e) {
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case REQUEST_CODE_SPEECH_INPUT:{
                if(resultCode==RESULT_OK && null!=data){
                    ArrayList<String> result= data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    nombreyApellido.setText(result.get(0));
                }
                break;
            }
        }
    }

    public void empezar (View v)
    {
        if (nombreyApellido.getText().toString().equals(""))
        {
            Toast.makeText(this, "Debe ingresar su nombre y apellido", Toast.LENGTH_SHORT).show();
        }
        else {

            ejecutarServicio("https://eisi.fia.ues.edu.sv/GPO06/pupasWeb/insertarOrden.php/");
            boton.setVisibility(View.VISIBLE);
            botonGuardar.setVisibility(View.INVISIBLE);
        }


    }
    
    //Metodo para ingresar la orden 
    private void ejecutarServicio(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MenuPrincipal.this, "Orden: "+response, Toast.LENGTH_SHORT).show();
                id=response;
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MenuPrincipal.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("nombre", nombreyApellido.getText().toString());
                if(llevar.isChecked()){
                    parametros.put("delivery", "1");
                }else{
                    parametros.put("delivery", "0");
                }
                return parametros;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    public void pasarOtra(View view) {
        Intent inte1 = new Intent(this, CrearPedidos.class);
        inte1.putExtra("idOrden",id);
        startActivity(inte1);
    }
}
