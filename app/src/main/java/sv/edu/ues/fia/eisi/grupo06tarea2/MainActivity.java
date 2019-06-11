package sv.edu.ues.fia.eisi.grupo06tarea2;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.bumptech.glide.Glide;

import java.io.IOException;

public class MainActivity extends AppCompatActivity
{
    //Variables para el audio
    MediaPlayer Media;
    Button Play;
    Button Stop;
    //Variable utilizada en la libreria GLIDE
    private ImageView fotoImagenView;
    //Variables para la notificacion PUSH
    NotificationCompat.Builder mBuilder;
    int mNotificationId=001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Para reproducir audio
        Play=(Button) findViewById(R.id.play);
        Stop=(Button) findViewById(R.id.stop);
        Play.setOnClickListener(onClick);
        Stop.setOnClickListener(onClick);
        Media=MediaPlayer.create(getApplicationContext(), R.raw.music);

        //***************Codigo para utilizar la libreria GLIDE******************
        fotoImagenView = (ImageView) findViewById(R.id.imagenInicio);

        String url="https://media1.tenor.com/images/1bc8809ba3844ca9638f37e11d2fe172/tenor.gif?itemid=9138694";

        Glide.with(this)
                .load(url)
                .into(fotoImagenView);

        //**************Codigo para utilizar la notificacion PUSH************************
        mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.informacion)
                        .setContentTitle("INFORMACIÓN")
                        .setContentText("Todo lo que debes saber sobre nosotros");
        // Activity que se lanza al hacer click a la notificacion
        Intent resultIntent=new Intent(this, ResultActivity.class);
        PendingIntent resultPendingIntent=
                PendingIntent.getActivity(
                        this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager mNotifyMgr=
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        mNotifyMgr.notify(mNotificationId,mBuilder.build());
    }

    //Boton para iniciar el pedido
    public  void iniciarPedido(View v)
    {
        Intent ints = new Intent(this,MenuPrincipal.class);
        startActivity(ints);

    }
    //Boton para datos curiosos sobre las pupusas
    public  void datosPupas(View v)
    {
        Intent inte = new Intent(this,DatosCuriosos.class);
        startActivity(inte);


    }
    //Metodo para reproducir el sonido
    View.OnClickListener onClick=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
// TODO Auto-generated method stub
            if (v.getId()==R.id.play){
                if (Media.isPlaying()){
                    Media.pause();
                    Play.setText("Play");
                }
                else{
                    Media.start();
                    Play.setText("Pause");
                }
            }
            else{
                Media.stop();
                Play.setText("Play");
                try{
                    Media.prepare();
                }
                catch(IllegalStateException e){
                    e.printStackTrace();
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
    };

}
