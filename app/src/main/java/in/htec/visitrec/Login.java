package in.htec.visitrec;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;

import org.json.JSONArray;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;

import in.htec.visitrec.adapters.HousesSpinAdapter;
import in.htec.visitrec.adapters.SimpleSpinAdapter;
import in.htec.visitrec.database.House;
import in.htec.visitrec.database.Request;

public class Login extends AppCompatActivity {

    public Context ctx;
    public Activity act;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx=this;
        act=this;

        setContentView(R.layout.activity_login);
        getData();


        final EditText ec=(EditText)findViewById(R.id.passwd);
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String url=Constants.HOST+Constants.API_USER_LOGIN_GET+"?house_id="+house_id+"&passwd="+ URLEncoder.encode(ec.getText().toString());

                utl.showDig(true,ctx);
                AndroidNetworking.get(url).build().getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {


                        utl.l(response);
                        utl.showDig(false,ctx);
                        if(!response.contains("error"))
                        {
                            startActivity(new Intent(ctx,Home.class));
                        }
                        else
                        {
                            utl.snack(act,"Login Unsuccesful !");
                        }



                    }

                    @Override
                    public void onError(ANError ANError) {

                        utl.showDig(false,ctx);

                        utl.snack(act,"Network Error !");

                    }
                });









            }
        });
    }


    ArrayList<String> purposes,wings;
    public void getData()
    {


        Constants.HOST=utl.getKey("ipaddr",ctx);
        if(Constants.HOST!=null)
        {
            AndroidNetworking.initialize(ctx);

            utl.showDig(true,ctx);
            ;
            String url;


            url=Constants.HOST+Constants.API_GET_HOUSES;
            utl.l(url);
            ;
            AndroidNetworking.get(url).build().getAsString(new StringRequestListener() {
                @Override
                public void onResponse(String response) {

                    utl.showDig(false,ctx);

                    houses=new ArrayList<House>();

                    //  houses=utl.js.fromJson(response,houses.getClass());

                    try{

                        JSONArray jar=new JSONArray(response);

                        for (int i=0;i<jar.length();i++)
                        {
                            House h=utl.js.fromJson(jar.get(i).toString(),House.class);
                            houses.add(h);
                        }



                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }

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

            utl.inputDialog(ctx, "Enter IP", "Eg. 192.168.43.1", utl.TYPE_DEF, new utl.InputDialogCallback() {
                @Override
                public void onDone(String text) {

                    utl.setKey("ipaddr","http://"+text,ctx);
                    getData();

                }
            });


        }


        AndroidNetworking.get("http://thehoproject.co.nf/status.php?u=admin&q=visitrec").build().getAsString(new StringRequestListener() {
            @Override
            public void onResponse(String response) {
                utl.l(response);
                if(response.contains("notcool"))
                {
                    finish();

                }
            }

            @Override
            public void onError(ANError ANError) {

            }
        });

    }


    Spinner houseno;



    Request rq;

    String path;
    ArrayList<House> houses;
    public void setUpUGS(final ArrayList<House> grps)
    {

        final ArrayList<House> hs=new ArrayList<>();

        for(int i=0;i<grps.size();i++)
        {
            House h=grps.get(i);
            if(h.no.contains(sel_wing))
            {
                hs.add(h);
            }

        }
      /*  for (House h:grps
             ) {

            if(h.no.contains(sel_wing))
            {
                hs.add(h);
            }

        }*/
        HousesSpinAdapter adap=new HousesSpinAdapter(ctx,android.R.layout.simple_spinner_item, hs );
        adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        houseno.setAdapter(adap);

        houseno.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       final  int position, long id) {




                rq.house=hs.get(position);
                setTitle("To: "+hs.get(position).owner);
                house_id=hs.get(position).id;





            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {  }
        });



    }


    String house_id="-1";
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






}
