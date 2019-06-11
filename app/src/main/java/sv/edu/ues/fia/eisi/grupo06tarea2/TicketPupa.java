package sv.edu.ues.fia.eisi.grupo06tarea2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class TicketPupa extends AppCompatActivity {
    ArrayList<String> pedidoPupusa = new ArrayList<String>();
    ArrayList<String> pedidoBebida = new ArrayList<String>();
    String nOrden;
    ArrayAdapter<String> adaptadorListPupas, adaptadorListBebidas;
    ListView listaPupusas,listaBebidas;


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

        adaptadorListBebidas = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, pedidoBebida);
        adaptadorListPupas = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pedidoPupusa);
        listaPupusas.setAdapter(adaptadorListPupas);
        listaBebidas.setAdapter(adaptadorListBebidas);
    }
}
