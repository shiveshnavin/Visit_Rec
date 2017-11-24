package in.htec.visitrec;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.bumptech.glide.Glide;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;

import in.htec.visitrec.adapters.HousesSpinAdapter;
import in.htec.visitrec.adapters.SimpleSpinAdapter;
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

        wing=(Spinner)findViewById(R.id.wing);

        RadioButton work_visit=(RadioButton)findViewById(R.id.field2);

        work_visit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    rq.field2="Work";
                    field3.setVisibility(View.VISIBLE);

                }
                else {
                    rq.field2="Personal";
                    field3.setVisibility(View.GONE);
                    rq.field3="-";
                }
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

     /*   for(int i=0;i<10;i++)
        {
            houses.add(new House(i,"House No. "+i,"Mr. Abhinav "+i));
        }

        setUpUGS(houses);
*/

        getData();;

        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               send();


            }
        });



    }

    ArrayList<String> purposes,wings;
    public void getData()
    {


        Constants.HOST=utl.getKey("ip",ctx);
        if(Constants.HOST!=null)
        {
            AndroidNetworking.initialize(ctx);

            String url=Constants.HOST+Constants.API_GET_PURPOSES;
            utl.l(url);
            utl.showDig(true,ctx);
            ;
            AndroidNetworking.get(url).build().getAsString(new StringRequestListener() {
            @Override
            public void onResponse(String response) {

                utl.showDig(false,ctx);

                purposes=new ArrayList<String>();

                purposes=utl.js.fromJson(response,purposes.getClass());
                setUpPurposes(purposes);


            }

            @Override
            public void onError(ANError ANError) {

                utl.showDig(false,ctx);

                utl.diag(act,"ERROR : " ,ANError.getErrorBody());

            }
        });

              url=Constants.HOST+Constants.API_GET_HOUSES;
            utl.l(url);
             ;
            AndroidNetworking.get(url).build().getAsString(new StringRequestListener() {
                @Override
                public void onResponse(String response) {

                    utl.showDig(false,ctx);

                    houses=new ArrayList<House>();

                    houses=utl.js.fromJson(response,houses.getClass());
                    wing.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            setUpUGS(houses);

                        }
                    },1000);



                    setUpWings(wings);


                }

                @Override
                public void onError(ANError ANError) {

                    utl.showDig(false,ctx);

                    utl.diag(act,"ERROR : " ,ANError.getErrorBody());

                }
            });



            wings=new ArrayList<String>();
            wings.add("A");
            wings.add("B");
            wings.add("C");


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


    public void send()
    {
        TextView email=(TextView)findViewById(R.id.email);
        RadioButton field2=(RadioButton)findViewById(R.id.field2);
        Spinner field3=(Spinner)findViewById(R.id.field3);
        TextView field4=(TextView)findViewById(R.id.field4);
        TextView field5=(TextView)findViewById(R.id.field5);

        rq.field2=field2.getText().toString();




        rq.field4=field4.getText().toString();
        rq.field5=field5.getText().toString();

        rq.field0=email.getText().toString();

      final  String body="Visitor Details :- \n"+
                "House : "+rq.house.no+"\n"+
                "Field 1 : "+rq.field0+"\n"+
                "Field 2 : "+rq.field2+"\n"+
                "Field 3 : "+rq.field3+"\n"+
                "Field 4 : "+rq.field4+"\n"+
                "Field 5 : "+rq.field5+"\n";

        String sub="Visitor Details";


        Constants.HOST=utl.getKey("ip",ctx);
        if(Constants.HOST!=null)
        {
            AndroidNetworking.initialize(ctx);
            String url=Constants.HOST+"/mail.php?recipient="+rq.field0+"&subject="+ URLEncoder.encode("New Visitor")
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

        ArrayList<House> hs=new ArrayList<>();
        for (House h:grps
             ) {

            if(h.no.contains(sel_wing))
            {
                hs.add(h);
            }

        }
        HousesSpinAdapter adap=new HousesSpinAdapter(ctx,android.R.layout.simple_spinner_item, hs );
        adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        houseno.setAdapter(adap);

                houseno.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view,
                                               final  int position, long id) {




                        rq.house=houses.get(position);
                        setTitle("To: "+houses.get(position).owner);




                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapter) {  }
                });



    }


    Spinner wing;
    String  sel_wing="A";

    public void setUpWings(final ArrayList<String> grps)
    {

        SimpleSpinAdapter adap=new SimpleSpinAdapter(ctx,android.R.layout.simple_spinner_item, grps );
        adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        wing.setAdapter(adap);

        wing.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       final  int position, long id) {



                sel_wing=grps.get(position);
                setUpUGS(houses);


            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {  }
        });



    }






    Spinner field3;
    public void setUpPurposes(final ArrayList<String> grps)
    {


          field3=(Spinner)findViewById(R.id.field3);


        SimpleSpinAdapter adap=new SimpleSpinAdapter(ctx,android.R.layout.simple_spinner_item, grps );
        adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        field3.setAdapter(adap);

        field3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       final  int position, long id) {



                rq.field3=grps.get(position);



            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {  }
        });



    }







}
