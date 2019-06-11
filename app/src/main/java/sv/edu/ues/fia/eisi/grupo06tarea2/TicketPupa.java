package sv.edu.ues.fia.eisi.grupo06tarea2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class TicketPupa extends AppCompatActivity {
    ArrayList<String> pedidoLista = new ArrayList<String>();
    ArrayAdapter<String> adaptadorList;
    ListView listaPedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_pupa);
        Intent recibir = getIntent();
        pedidoLista = recibir.getStringArrayListExtra("pupusa");

        listaPedido = (ListView) findViewById(R.id.listaTicket);

        adaptadorList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pedidoLista);
        listaPedido.setAdapter(adaptadorList);
    }
}
