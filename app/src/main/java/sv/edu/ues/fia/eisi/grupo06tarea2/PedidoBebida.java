package sv.edu.ues.fia.eisi.grupo06tarea2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PedidoBebida extends AppCompatActivity {
    Spinner bebidasSpinner;
    ListView listaBebidas;
    ImageView bebida;
    ArrayList<String> pedidoLista = new ArrayList<String>();
    ArrayList<String> bebidasLista = new ArrayList<String>();
    ArrayList<String> pedidoBebida = new ArrayList<String>();
    String URL="https://eisi.fia.ues.edu.sv/GPO06/pupasWeb/mostrarBebidas.php/";
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
        bebida = (ImageView) findViewById(R.id.imagenBebida);
        loadBebidas();

        //Para cargar la imagen de la bebida
        String url="https://media.tenor.com/images/a5be16d84506481a0706a3cc31cf67d2/tenor.gif";

        Glide.with(this)
                .load(url)
                .into(bebida);

        listaBebidas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int posicion=i;

                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(PedidoBebida.this);
                dialogo1.setTitle("Importante");
                dialogo1.setMessage("¿ Eliminara esta bebida de su pedido?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        pedidoBebida.remove(posicion);

                        adaptadorList.notifyDataSetChanged();
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                    }
                });
                dialogo1.show();

                return false;
            }
        });
    }

    public void agregarBebida(View view) {
        if ((bebidaCantidad.getText().toString().equals("") | bebidasSpinner.getSelectedItem().toString().equals("Seleccione bebida"))) {
            Toast.makeText(this, "Seleccione bebida o ingrese cantidad", Toast.LENGTH_SHORT).show();
        } else {
            String eleccion = bebidasSpinner.getSelectedItem().toString();

            String[] partEleccion = eleccion.split(" ");
            pedidoBebida.add(eleccion + "        " + bebidaCantidad.getText().toString());

            adaptadorList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pedidoBebida);
            listaBebidas.setAdapter(adaptadorList);
            bebidaCantidad.setText("");
            bebidasSpinner.setSelection(0);


        }
    }

    public void guardarBebidas(View view) {
        for(int x=0; x<pedidoBebida.size();x++) {
            ejecutarServicio("https://eisi.fia.ues.edu.sv/GPO06/pupasWeb/insertarBebida.php/");
        }



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

                        bebidasLista.add(id+" "+nombre+"        "+precio);



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
                Toast.makeText(PedidoBebida.this, "Bebida guardada", Toast.LENGTH_SHORT).show();
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
                String[] partPedidoIndex = pedido.split(" ");
                String [] partPedidoCuerpo = pedido.split("        ");
                parametros.put("drink_id",partPedidoIndex[0]);
                parametros.put("cantidad", partPedidoCuerpo[partPedidoCuerpo.length-1]);
                i++;

                return parametros;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
