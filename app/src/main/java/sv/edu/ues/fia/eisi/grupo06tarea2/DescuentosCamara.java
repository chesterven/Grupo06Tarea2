package sv.edu.ues.fia.eisi.grupo06tarea2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DescuentosCamara extends AppCompatActivity {
    ImageView mImageViewPhoto;
    Button mButtonTakePhoto;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    final int PHOTO_CONST = 1;
    String mAbsolutePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descuentos_camara);
        mImageViewPhoto = (ImageView) findViewById(R.id.imageViewPhoto);
        mButtonTakePhoto = (Button) findViewById(R.id.buttonTakePhoto);

        mButtonTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });
    }

    private void takePhoto(){
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (checkSelfPermission(Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA},
                    MY_CAMERA_REQUEST_CODE);
        }
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(takePhotoIntent.resolveActivity(getPackageManager()) != null){
            File photoFile = null;
            try{
                photoFile = createPhotoFile();
            }catch (Exception e){
                e.printStackTrace();

            }
            if (photoFile != null){
                Uri photoUri = FileProvider.getUriForFile(DescuentosCamara.this,"sv.edu.ues.fia.eisi.grupo06tarea2",photoFile);
                takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
                startActivityForResult(takePhotoIntent,PHOTO_CONST);
            }
        }
    }
    private File createPhotoFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "image_"+ timeStamp;

        File storageFile = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File photoFile = File.createTempFile(
          imageFileName,
          ".jpg",
          storageFile
        );
        mAbsolutePath = photoFile.getAbsolutePath();
        return photoFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==PHOTO_CONST && resultCode == RESULT_OK){
            Uri uri =Uri.parse(mAbsolutePath);
            Log.d("Aqui",mAbsolutePath);

            mImageViewPhoto.setImageURI(uri);
            shareImage(mAbsolutePath);

        }
    }
    private void shareImage(String mAbsolutePath) {
        Intent share = new Intent(Intent.ACTION_SEND);

        // If you want to share a png image only, you can do:
        // setType("image/png"); OR for jpeg: setType("image/jpeg");
        share.setType("image/*");

        // Make sure you put example png image named myImage.png in your
        // directory
        String imagePath = mAbsolutePath;

        File imageFileToShare = new File(imagePath);

        Uri uri = Uri.fromFile(imageFileToShare);
        share.putExtra(Intent.EXTRA_STREAM, uri);
        share.putExtra(Intent.EXTRA_TEXT,"Con PUPASYA me la paso rico!!");
        startActivity(Intent.createChooser(share, "Comparte en redes!"));
    }
}
