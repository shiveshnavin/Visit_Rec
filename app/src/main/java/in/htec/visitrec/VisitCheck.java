package in.htec.visitrec;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;

public class VisitCheck extends Service {
    Context ctx;
    final Runnable rm=new Runnable() {
        @Override
        public void run() {
            check();
            if(running)
            hd.postDelayed(rm,5000);
        }
    };
      Handler hd;
    boolean running=true;


    public Context context = this;
    public Handler handler = null;
    public static Runnable runnable = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
       // Toast.makeText(this, "Service created!", Toast.LENGTH_LONG).show();


        utl.l("VISITCHECK Check Started");

        running=true;

        ctx=context;

        hd=new Handler();


        hd.postDelayed(rm,5000);

        AndroidNetworking.initialize(ctx);
    }


    @Override
    public void onStart(Intent intent, int startid) {
        Toast.makeText(this, "Notification Service Started !", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onDestroy() {
        running=false;
        super.onDestroy();
    }

    public VisitCheck() {



    }

    public void check()
    {

        String url=Constants.HOST+Constants.API_GET_NOTIFICATIONS+"?house_id="+utl.getKey("house_id",ctx);


        utl.l("VISITCHECK : "+url);
        AndroidNetworking.get(url).build().getAsString(new StringRequestListener() {
            @Override
            public void onResponse(String response) {
                utl.l("VISITCHECK   "+response);

            }

            @Override
            public void onError(ANError ANError) {
                utl.l("VISITCHECK   "+ANError.getErrorDetail());

            }
        });

    }

}
