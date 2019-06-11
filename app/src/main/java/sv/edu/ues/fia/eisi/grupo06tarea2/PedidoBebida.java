package sv.edu.ues.fia.eisi.grupo06tarea2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PedidoBebida extends AppCompatActivity {
    Spinner bebidasSpinner;
    ListView listaBebidas;
    ArrayList<String> pedidoLista = new ArrayList<String>();
    ArrayList<String> bebidasLista = new ArrayList<String>();
    ArrayList<String> pedidoBebida = new ArrayList<String>();
    String URL="http://192.168.1.12:80/pupasWeb/mostrarBebidas.php/";
    ArrayAdapter<String> adaptadorList;
    RequestQueue requestQueue;
    int i=0;
    String idOrden;
    EditText bebidaCantidad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_bebida);
        Intent recibir = getIntent();
        pedidoLista = recibir.getStringArrayListExtra("pedido");
        idOrden = recibir.getStringExtra("idOrden");
        bebidaCantidad = (EditText) findViewById(R.id.bebidaCantidad);
        listaBebidas = (ListView) findViewById(R.id.listaBebida);
        bebidasSpinner = (Spinner) findViewById(R.id.bebida);

        loadBebidas();
    }

    public void agregarBebida(View view) {
        if ((bebidaCantidad.getText().toString().equals("") | bebidasSpinner.getSelectedItem().toString().equals("Seleccione bebida"))) {
            Toast.makeText(this, "Seleccione bebida o ingrese cantidad", Toast.LENGTH_SHORT).show();
        } else {
            String eleccion = bebidasSpinner.getSelectedItem().toString();
            String[] partEleccion = eleccion.split(" ");
            pedidoBebida.add(eleccion + " " + bebidaCantidad.getText().toString());

            adaptadorList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pedidoBebida);
            listaBebidas.setAdapter(adaptadorList);
            bebidaCantidad.setText("");
            bebidasSpinner.setSelection(0);


        }
    }

    public void guardarBebidas(View view) {
        for(int x=0; x<pedidoBebida.size();x++) {
            ejecutarServicio("http://192.168.1.12:80/pupasWeb/insertarBebida.php/");
        }


        Toast.makeText(this, "Guardado", Toast.LENGTH_SHORT).show();
        Intent ticket = new Intent(this,TicketPupa.class);
        ticket.putExtra("pupusa",pedidoLista);
        ticket.putExtra("bebida", pedidoBebida);
        ticket.putExtra("idOrden", idOrden);
        startActivity(ticket);
    }

    private void loadBebidas(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray bebidas = new JSONArray(response);

                    for(int i=0; i< bebidas.length();i++){
                        JSONObject bebidasObject = bebidas.getJSONObject(i);

                        int id = bebidasObject.getInt("id");
                        String nombre = bebidasObject.getString("nombre");
                        double precio = bebidasObject.getDouble("precioUni");

                        bebidasLista.add(id+" "+nombre+" "+precio);



                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PedidoBebida.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }


        });
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        bebidasLista.add(0,"Seleccione bebida");
        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter(this,android.R.layout.simple_spinner_item,bebidasLista);
        bebidasSpinner.setAdapter(adaptador);

    }

    private void ejecutarServicio(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Toast.makeText(PedidoBebida.this, "OPERACION EXITOSA", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PedidoBebida.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("order_id", idOrden);
                String pedido = pedidoBebida.get(i);
                String[] partPedido = pedido.split(" ");
                parametros.put("drink_id",partPedido[partPedido.length-partPedido.length]);
                parametros.put("cantidad", partPedido[partPedido.length-1]);
                i++;

                return parametros;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
