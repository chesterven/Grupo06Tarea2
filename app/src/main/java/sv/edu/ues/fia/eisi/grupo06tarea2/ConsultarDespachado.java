package sv.edu.ues.fia.eisi.grupo06tarea2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ConsultarDespachado extends AppCompatActivity {

    TextView nombre, despachado, total;
    EditText idOrden;
    RequestQueue requestQueue;
    ImageView imagen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_despachado);
        nombre = (TextView) findViewById(R.id.mostrarNombre);
        despachado = (TextView) findViewById(R.id.mostrarEstado);
        total = (TextView) findViewById(R.id.mostrarTotal);
        idOrden = (EditText) findViewById(R.id.idOrden);
        //***************Codigo para utilizar la libreria GLIDE******************
        imagen = (ImageView) findViewById(R.id.imagenConsulta);

        String url="https://cdn.icon-icons.com/icons2/1918/PNG/512/iconfinder-documents07-1622836_121949.png";

        Glide.with(this)
                .load(url)
                .into(imagen);

    }

    public void consultar(View view)
    {
        buscarOrden("https://eisi.fia.ues.edu.sv/GPO06/pupasWeb/buscarOrden.php?id="+idOrden.getText().toString()+"");
    }

    public void buscarOrden(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        nombre.setText(jsonObject.getString("nombre"));
                        total.setText(jsonObject.getString("total"));
                        if(jsonObject.getString("estado").equals("1")){
                            despachado.setText("Ya fue despachado");
                        }else{
                            despachado.setText("Aun no ha sido despachado");
                        }

                    } catch (JSONException e) {
                        Toast.makeText(ConsultarDespachado.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ConsultarDespachado.this, "No existe el número de orden.", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
}
