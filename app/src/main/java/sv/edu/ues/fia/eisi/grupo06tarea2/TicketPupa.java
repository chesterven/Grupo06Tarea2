package sv.edu.ues.fia.eisi.grupo06tarea2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class TicketPupa extends AppCompatActivity {
    ArrayList<String> pedidoPupusa = new ArrayList<String>();
    ArrayList<String> pedidoBebida = new ArrayList<String>();
    String nOrden;
    ArrayAdapter<String> adaptadorListPupas, adaptadorListBebidas;
    ListView listaPupusas,listaBebidas;
    TextView tPupas, tBebidas,tOrden;
    int ipupas=0;
    int ibebidas=0;
    double totalPupusas=0;
    double totalBebidas=0;
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
            totalPupusas = totalPupusas + (Double.valueOf(partPupa[partPupa.length-2])*Double.valueOf(partPupa[partPupa.length-3]));
        }
        tPupas.setText(String.valueOf(totalPupusas));
    }

    public void calcularTotalBebida(){
        for(int i=0;i<pedidoBebida.size();i++){
            String beb = pedidoBebida.get(i);
            String[] partBeb = beb.split(" ");
            totalBebidas = totalBebidas +(Double.valueOf(partBeb[partBeb.length-1])*Double.valueOf(partBeb[partBeb.length-2]));
        }
        tBebidas.setText(String.valueOf(totalBebidas));
    }

    public void totalOrden(){
        totalOrden = Float.valueOf(tBebidas.getText().toString())+Float.valueOf(tPupas.getText().toString());
        tOrden.setText(String.valueOf(totalOrden));
    }
}
