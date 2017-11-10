package in.htec.visitrec;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import net.bozho.easycamera.DefaultEasyCamera;
import net.bozho.easycamera.EasyCamera;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import io.fotoapparat.Fotoapparat;
import io.fotoapparat.view.CameraView;

public class Click extends AppCompatActivity {

    public Context ctx;
    public Activity act;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        utl.fullScreen(this);
        setContentView(R.layout.activity_click);

        ctx=this;
        act=this;

        Constants.init(ctx);
        try {
            requestPermission();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            fotoapparat.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        fotoapparat.stop();
    }

    Fotoapparat fotoapparat;
    public void setUpCam()
    {
        try {
            CameraView cameraView = (CameraView) findViewById(R.id.cam);
                fotoapparat = Fotoapparat
                    .with(ctx)
                    .into(cameraView)
                    .build();

            fotoapparat.start();

            findViewById(R.id.click).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String file=Constants.getFolder()+"/"+ SystemClock.uptimeMillis()+"bmp.jpg";
                    fotoapparat
                            .takePicture()
                            .saveToFile(new File(file));
                   // utl.snack(act,"SAVED !");

                    Intent it=new Intent(ctx,Splash.class);
                    it.putExtra("img",file);
                    startActivity(it);



                }
            });

            //  actions.takePicture(EasyCamera.Callbacks.create().withJpegCallback(callback));
        } catch (Exception e) {
            e.printStackTrace();
        }



    }
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ) {
            ActivityCompat
                    .requestPermissions(act, new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_PERMISSIONS);
        }
        else {
            setUpCam();

        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted

                    setUpCam();
                } else {
                    // Permission Denied
                    Toast.makeText(ctx, "Permission Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    private void saveBitmap(Bitmap bitmap,String path){
        if(bitmap!=null){
            try {
                FileOutputStream outputStream = null;
                try {
                    outputStream = new FileOutputStream(path); //here is set your file path where you want to save or also here you can set file object directly

                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream); // bitmap is your Bitmap instance, if you want to compress it you can compress reduce percentage
                    // PNG is a lossless format, the compression factor (100) is ignored
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (outputStream != null) {
                            outputStream.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
