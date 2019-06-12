package sv.edu.ues.fia.eisi.grupo06tarea2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TicketPupa extends AppCompatActivity {
    ArrayList<String> pedidoPupusa = new ArrayList<String>();
    ArrayList<String> pedidoBebida = new ArrayList<String>();
    String nOrden;
    RequestQueue requestQueue;
    ArrayAdapter<String> adaptadorListPupas, adaptadorListBebidas;
    ListView listaPupusas,listaBebidas;
    TextView tPupas, tBebidas,tOrden,ticketNombre,ticketOrden;
    float totalPupusas=0;
    float totalBebidas=0;
    float totalOrden=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_pupa);
        Intent recibir = getIntent();
        pedidoPupusa = recibir.getStringArrayListExtra("pupusa");
        pedidoBebida = recibir.getStringArrayListExtra("bebida");
        nOrden = recibir.getStringExtra("idOrden");



        listaPupusas = (ListView) findViewById(R.id.listaTicketPupusas);
        listaBebidas = (ListView) findViewById(R.id.listaTicketBebidas);
        ticketNombre = (TextView) findViewById(R.id.ticketNombre);
        ticketOrden = (TextView)  findViewById(R.id.ticketOrden);

        ticketOrden.setText(nOrden);
        buscarOrden("http://192.168.1.12:80/pupasWeb/buscarOrden.php?id="+nOrden+"");


        adaptadorListBebidas = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, pedidoBebida);
        adaptadorListPupas = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pedidoPupusa);
        tPupas = (TextView) findViewById(R.id.totalPupusa);
        tBebidas = (TextView) findViewById(R.id.totalBebida);
        tOrden = (TextView) findViewById(R.id.totalPedido);

        listaPupusas.setAdapter(adaptadorListPupas);
        listaBebidas.setAdapter(adaptadorListBebidas);

        calcularTotalPupusas();
        calcularTotalBebida();
        totalOrden();
    }
    public void calcularTotalPupusas(){
        for(int i=0;i<pedidoPupusa.size();i++){
            String pupa = pedidoPupusa.get(i);
            String[] partPupa = pupa.split(" ");
            totalPupusas = totalPupusas + (Float.valueOf(partPupa[partPupa.length-2])*Float.valueOf(partPupa[partPupa.length-3]));
        }
        tPupas.setText(String.valueOf(totalPupusas));
    }

    public void calcularTotalBebida(){
        for(int i=0;i<pedidoBebida.size();i++){
            String beb = pedidoBebida.get(i);
            String[] partBeb = beb.split(" ");
            totalBebidas = totalBebidas +(Float.valueOf(partBeb[partBeb.length-1])*Float.valueOf(partBeb[partBeb.length-2]));
        }
        tBebidas.setText(String.valueOf(totalBebidas));
    }

    public void totalOrden(){
        totalOrden = Float.valueOf(tBebidas.getText().toString())+Float.valueOf(tPupas.getText().toString());
        tOrden.setText(String.valueOf(totalOrden));
    }

    public void buscarOrden(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        ticketNombre.setText(jsonObject.getString("nombre"));

                    } catch (JSONException e) {
                        Toast.makeText(TicketPupa.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(TicketPupa.this, "Error de conexion", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }


}
