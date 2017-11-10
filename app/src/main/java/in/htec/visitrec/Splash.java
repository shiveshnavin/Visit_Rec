package in.htec.visitrec;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.ArrayList;

import in.htec.visitrec.adapters.SpinAdapter;
import in.htec.visitrec.database.House;

public class Splash extends AppCompatActivity {

    public Context ctx;
    public Activity act;

    Spinner houseno;

    ArrayList<House> houses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx=this;
        act=this;
        setContentView(R.layout.activity_splash);

        houseno =(Spinner)findViewById(R.id.houseno);

        houses=new ArrayList<>();

        for(int i=0;i<10;i++)
        {
            houses.add(new House(i,"House No. "+i));
        }

        setUpUGS(houses);




    }



    public void setUpUGS(final ArrayList<House> grps)
    {

        SpinAdapter adap=new SpinAdapter(ctx,android.R.layout.simple_spinner_item, grps );
        adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        houseno.setAdapter(adap);
        houseno.postDelayed(new Runnable() {
            @Override
            public void run() {

                houseno.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view,
                                               final  int position, long id) {



                        utl.snack(act,houses.get(position).name);





                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapter) {  }
                });
            }
        },700);


    }






}
