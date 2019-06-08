package sv.edu.ues.fia.eisi.grupo06tarea2;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity
{
    //Variable utilizada en la libreria GLIDE
    private ImageView fotoImagenView;
    //Variables para la notificacion PUSH
    NotificationCompat.Builder mBuilder;
    int mNotificationId=001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}
