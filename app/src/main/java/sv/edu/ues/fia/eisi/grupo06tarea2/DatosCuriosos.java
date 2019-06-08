package sv.edu.ues.fia.eisi.grupo06tarea2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import org.achartengine.GraphicalView;

public class DatosCuriosos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_curiosos);
        PieGraph pastel=new PieGraph();
        GraphicalView graphicalView = pastel.getGraphicalView(this,53,35,12);
        LinearLayout pieGraph = (LinearLayout) findViewById(R.id.pieGraph);
        pieGraph.addView(graphicalView);
    }
}
