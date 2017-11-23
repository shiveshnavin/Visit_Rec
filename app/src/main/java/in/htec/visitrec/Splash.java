package in.htec.visitrec;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;

import in.htec.visitrec.adapters.SpinAdapter;
import in.htec.visitrec.database.House;
import in.htec.visitrec.database.Request;
import in.htec.visitrec.utils.GenricCallback;

public class Splash extends AppCompatActivity {

    public Context ctx;
    public Activity act;

    Spinner houseno;



    Request rq;

    String path;
    ArrayList<House> houses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx=this;
        act=this;
        setContentView(R.layout.activity_splash);

        path=getIntent().getStringExtra("img");
        utl.l(path);

        rq=new Request();
       final ImageView imageView=(ImageView)findViewById(R.id.img);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utl.snack(view, "Change Image ?", "CHANGE", new GenricCallback() {
                    @Override
                    public void onStart() {
                        finish();
                    }

                    @Override
                    public void onDo(Object obj) {

                    }

                    @Override
                    public void onDo(Object obj, Object obj2) {

                    }

                    @Override
                    public void onDone(Object obj) {

                    }
                });
            }
        });
        Uri ur=Uri.fromFile(new File(path));

        //imageView.setImageURI(ur);

       //Glide.with(ctx).load(path).into(imageView);
//       Picasso.with(ctx).load(ur).resize(400,400)
//               .centerCrop().placeholder(R.drawable.rounded_black_filled)
//               .error(R.drawable.rounded_black_filled).into(imageView);

        imageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                Glide.with(ctx).load(getIntent().getStringExtra("img")).centerCrop().into(imageView);

            }
        },1500);

//        imageView.setImageURI(ur);
        houseno =(Spinner)findViewById(R.id.houseno);

        houses=new ArrayList<>();

        for(int i=0;i<10;i++)
        {
            houses.add(new House(i,"House No. "+i,"Mr. Abhinav "+i));
        }

        setUpUGS(houses);


        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               send();


            }
        });



    }


    public void send()
    {
        TextView email=(TextView)findViewById(R.id.email);
        TextView field2=(TextView)findViewById(R.id.field2);
        TextView field3=(TextView)findViewById(R.id.field3);
        TextView field4=(TextView)findViewById(R.id.field4);
        TextView field5=(TextView)findViewById(R.id.field5);

        rq.field2=field2.getText().toString();
        rq.field3=field3.getText().toString();
        rq.field4=field4.getText().toString();
        rq.field5=field5.getText().toString();

        rq.email=email.getText().toString();

      final  String body="Visitor Details :- \n"+
                "House : "+rq.house.name+"\n"+
                "Field 1 : "+rq.email+"\n"+
                "Field 2 : "+rq.field2+"\n"+
                "Field 3 : "+rq.field3+"\n"+
                "Field 4 : "+rq.field4+"\n"+
                "Field 5 : "+rq.field5+"\n";

        String sub="Visitor Details";


        Constants.HOST=utl.getKey("ip",ctx);
        if(Constants.HOST!=null)
        {
            AndroidNetworking.initialize(ctx);
            String url=Constants.HOST+"/mail.php?recipient="+rq.email+"&subject="+ URLEncoder.encode("New Visitor")
                    +"&body="+ URLEncoder.encode(body)
                    ;

            utl.l(url);
            utl.showDig(true,ctx);
            ;                AndroidNetworking.get(url).build().getAsString(new StringRequestListener() {
            @Override
            public void onResponse(String response) {
                utl.showDig(false,ctx);

                utl.diag(act,"DONE",response);
            }

            @Override
            public void onError(ANError ANError) {                        utl.showDig(false,ctx);

                utl.diag(act,"ERROR : " ,ANError.getErrorBody());

            }
        });


        }
        else{

            utl.inputDialog(ctx, "Enter IP", "Eg. 192.168.43.1", utl.TYPE_PHONE, new utl.InputDialogCallback() {
                @Override
                public void onDone(String text) {

                 utl.setKey("ip","http://"+text,ctx);

                }
            });

        }


    }

    public void setUpUGS(final ArrayList<House> grps)
    {

        SpinAdapter adap=new SpinAdapter(ctx,android.R.layout.simple_spinner_item, grps );
        adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        houseno.setAdapter(adap);

                houseno.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view,
                                               final  int position, long id) {



                        utl.snack(act,houses.get(position).name);

                        rq.house=houses.get(position);
                        setTitle("To: "+houses.get(position).owner);




                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapter) {  }
                });



    }






}
