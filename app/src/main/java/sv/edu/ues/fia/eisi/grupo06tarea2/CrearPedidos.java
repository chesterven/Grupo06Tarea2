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

public class CrearPedidos extends AppCompatActivity {
    Spinner pupas,masa;
    EditText cantidad;
    ListView lista;
    ImageView imagenAnotar;

    //Almacenar el id de la pupusa
    ArrayList<Integer> idPupusa = new ArrayList<Integer>();
    //Almacenar el id de la orden de la pantalla anterior
    String idOrden;


    ArrayList<String> stringEspecialidades  = new ArrayList<String>();
    ArrayList<String> stringEspecialidadesRespaldo  = new ArrayList<String>();

    ArrayList<String> masas = new ArrayList<String>();
    ArrayList<String> pedidoLista = new ArrayList<String>();
    RequestQueue requestQueue;
    String URL="http://192.168.1.12:80/pupasWeb/mostrarEspecialidades.php/";
    ArrayAdapter<String> adaptadorList;
    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_pedidos);
        Intent recibir = getIntent();
        idOrden = recibir.getStringExtra("idOrden");

        pupas = (Spinner) findViewById(R.id.pupa);
        masa = (Spinner) findViewById(R.id.masa);
        cantidad = (EditText) findViewById(R.id.editCantidad);
        lista = (ListView) findViewById(R.id.listaPedido);


        //Para cargar la imagen al activity

        imagenAnotar=(ImageView)  findViewById(R.id.imagenAnotar);
        String url="https://cdn.icon-icons.com/icons2/46/PNG/128/Write_editnote_writ_9515.png";

        Glide.with(this)
                .load(url)
                .into(imagenAnotar);

        masas.add("Seleccione tipo de masa");
        masas.add("Maiz");
        masas.add("Arroz");
        ArrayAdapter<CharSequence> adaptadorMasas = new ArrayAdapter(this,android.R.layout.simple_spinner_item,masas);
        masa.setAdapter(adaptadorMasas);

        loadEspecialidades();


        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int posicion=i;

                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(CrearPedidos.this);
                dialogo1.setTitle("Importante");
                dialogo1.setMessage("¿ Eliminara esta pupusa de su pedido?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        pedidoLista.remove(posicion);
                        idPupusa.remove(posicion);
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

    public void guardarPedido(View view) {
        for(int x=0; x<pedidoLista.size();x++) {
            ejecutarServicio("http://192.168.1.12:80/pupasWeb/insertarPedido.php/");
        }


        Toast.makeText(this, "Guardado", Toast.LENGTH_SHORT).show();
        Intent bebida = new Intent(this,PedidoBebida.class);
        bebida.putExtra("pedido",pedidoLista);
        bebida.putExtra("idOrden", idOrden);
        startActivity(bebida);
    }

    public void agregarPedido(View view) {
        if ((cantidad.getText().toString().equals("") | masa.getSelectedItem().toString().equals("Seleccione tipo de masa") | pupas.getSelectedItem().toString().equals("Seleccione pupusa"))) {
            Toast.makeText(this, "Seleccione masa,pupusa o ingrese cantidad", Toast.LENGTH_SHORT).show();
        } else {
            String eleccion = pupas.getSelectedItem().toString();
            idPupusa.add(pupas.getSelectedItemPosition());
            String[] partEleccion = eleccion.split(" ");
            pedidoLista.add(eleccion + " " + cantidad.getText().toString() + " " +masa.getSelectedItem().toString());

            adaptadorList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pedidoLista);
            lista.setAdapter(adaptadorList);
            cantidad.setText("");
            pupas.setSelection(0);
            masa.setSelection(0);

        }

    }
    private void loadEspecialidades(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray especialidades = new JSONArray(response);

                    for(int i=0; i< especialidades.length();i++){
                        JSONObject especialidadObject = especialidades.getJSONObject(i);

                        int id = especialidadObject.getInt("id");
                        String nombre = especialidadObject.getString("nombre");
                        String especialidad = especialidadObject.getString("especialidad");
                        double precio = especialidadObject.getDouble("precioUni");
                        String photo = especialidadObject.getString("photo");
                        stringEspecialidades.add(especialidad+" "+precio);
                        ;



                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CrearPedidos.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }


        });
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

        stringEspecialidades.add(0,"Seleccione pupusa");
        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter(this,android.R.layout.simple_spinner_item,stringEspecialidades);
        pupas.setAdapter(adaptador);

    }

    private void ejecutarServicio(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Toast.makeText(CrearPedidos.this, "OPERACION EXITOSA", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CrearPedidos.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("order_id", idOrden);
                String pedido = pedidoLista.get(i);
                String[] partPedido = pedido.split(" ");


                parametros.put("specialty_id",idPupusa.get(i).toString());
                parametros.put("cantidad", partPedido[partPedido.length-2]);
                parametros.put("masa", partPedido[partPedido.length-1]);
                i++;

                return parametros;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
