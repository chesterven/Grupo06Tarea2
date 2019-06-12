package sv.edu.ues.fia.eisi.grupo06tarea2;

import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;
import org.achartengine.GraphicalView;


public class DatosCuriosos extends AppCompatActivity {
    private ImageButton btnPlay;
    private ImageButton btnPause;
    private ImageButton btnStop;
    private VideoView video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_curiosos);
        //Codigo para mostrar la gráfica
        PieGraph pastel = new PieGraph();
        GraphicalView graphicalView = pastel.getGraphicalView(this, 53, 35, 12);
        LinearLayout pieGraph = (LinearLayout) findViewById(R.id.pieGraph);
        pieGraph.addView(graphicalView);

        //***************************Codigo para mostrar el video****************
        video = (VideoView) findViewById(R.id.videoView);
        String path = "android.resource://" + getPackageName() + "/" + R.raw.pupas;
        video.setVideoURI(Uri.parse(path));

        MediaController mediaController = new MediaController(this);
        video.setMediaController(mediaController);
        mediaController.setAnchorView(video);



    }


}
