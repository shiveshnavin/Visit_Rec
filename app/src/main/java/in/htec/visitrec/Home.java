package in.htec.visitrec;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import in.htec.visitrec.adapters.GRecyclerAdapter;
import in.htec.visitrec.database.Visit;
import in.htec.visitrec.utils.DateTimePicker;
import in.htec.visitrec.utils.GenricCallback;

public class Home extends AppCompatActivity {



    DateTimePicker dt;



    public Context ctx;
    public Activity act;

    ArrayList<GRecyclerAdapter.Dummy> dummies=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx=this;
        act=this;
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        rec =(RecyclerView)findViewById(R.id.rec);



        dt=new DateTimePicker(act, DateTimePicker.DATE_ONLY, new DateTimePicker.DateTimeCallback() {
            @Override
            public void picked(String dateTim) {



                getVisitByDate(dateTim,dummies);

            }
        });



        FloatingActionButton date = (FloatingActionButton) findViewById(R.id.date);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dt.pick(false);

            }
        });



        FloatingActionButton recent = (FloatingActionButton) findViewById(R.id.recent);
        recent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                onPostResume();
               // setUpList(dummies);
            }
        });



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                startActivity(new Intent(ctx,Click.class));


            }
        });


        startService(new Intent(ctx,VisitCheck.class));


    }


    @Override
    protected void onPostResume() {

        super.onPostResume();

        String url=Constants.HOST+Constants.API_GET_VISITS;
        utl.l(url);

        load(LOADING);
        AndroidNetworking.get(url).build().getAsJSONArray(new JSONArrayRequestListener() {
            @Override
            public void onResponse(JSONArray response) {
                dummies=new ArrayList<>();

                fac=false;
                try{

                    for(int i=0;i<response.length();i++)
                    {


                        GRecyclerAdapter.Dummy vt=utl.js.fromJson(response.get(i).toString(), GRecyclerAdapter.Dummy.class);
                        dummies.add(vt);


                    }


                }catch (Exception e)
                {
                    e.printStackTrace();
                }






                setUpList(dummies);



                load(LOADED);

            }

            @Override
            public void onError(ANError ANError) {



                load(EMPTY);
                utl.l(ANError.getErrorDetail());
            }
        });



    }

    boolean fac=false;
    public void setUpList(final ArrayList<GRecyclerAdapter.Dummy> dumm)
    {

        utl.l(dumm.size());
        if(dumm.size()==0)
        {

            utl.l("EMPTY");
             load(EMPTY);

       utl.snack(act,"No Records !");



        }
        else {
            load(LOADED);
        }





        Collections.sort(dumm, new Comparator<GRecyclerAdapter.Dummy>() {
            @Override
            public int compare(GRecyclerAdapter.Dummy dm, GRecyclerAdapter.Dummy t1) {

                return dm.dateTime.compareTo(t1.dateTime);

            }
        });

        Collections.reverse(dumm);



        GRecyclerAdapter adapter=new GRecyclerAdapter(ctx,dumm);
        rec.setLayoutManager(new LinearLayoutManager(act));
        rec.setAdapter(adapter);


    }


    RecyclerView rec;
    final int LOADING=12,EMPTY=13,LOADED=14;
    AVLoadingIndicatorView loading;
    LinearLayout placeholder;
    public void load(int state)
    {

        loading=(AVLoadingIndicatorView)findViewById(R.id.loading);
        rec =(RecyclerView)findViewById(R.id.rec);
        placeholder =(LinearLayout) findViewById(R.id.placeholder);



        if(state==LOADING)
        {
            loading.setVisibility(View.VISIBLE);
            rec.setVisibility(View.GONE);
            placeholder.setVisibility(View.GONE);
        }


        if(state==EMPTY)
        {
            loading.setVisibility(View.GONE);
            rec.setVisibility(View.GONE);
            placeholder.setVisibility(View.VISIBLE);
            placeholder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                }
            });



        }


        if(state==LOADED)
        {
            loading.setVisibility(View.GONE);
            rec.setVisibility(View.VISIBLE);
            placeholder.setVisibility(View.GONE);
        }


    }



    public void getVisitByDate(String date,ArrayList<GRecyclerAdapter.Dummy> list)
    {
        //24 January 2019 - 01:30 PM

        ArrayList<GRecyclerAdapter.Dummy> n=new ArrayList<>();

        for (GRecyclerAdapter.Dummy l:list) {



            if(l.dateTime.contains(date))
            {
                n.add(l);
            }
            setUpList(n);



        }



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.reset)
        {
           utl.snack(act, "Reset IP and Logout ?", "RESET", new GenricCallback() {
                @Override
                public void onStart() {

                    utl.setKey("ipaddr",null,ctx);
                    utl.setKey("loggein",null,ctx);

                    startActivity(new Intent(ctx,Login.class));
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
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.home,menu);

        return true;
    }
}
