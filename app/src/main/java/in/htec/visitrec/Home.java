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
import com.androidnetworking.interfaces.StringRequestListener;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.text.DateFormat;
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


              start();


            }
        });


        if(utl.isAdmin)
        {
            fab.setVisibility(View.VISIBLE);

        }
        else {

            fab.setVisibility(View.GONE);

            if(!utl.isMyServiceRunning(VisitCheck.class,ctx))
                startService(new Intent(ctx,VisitCheck.class));
            else {
                utl.l("VISITCHECK ALREADY STARTED ");
            }
        }






    }

    private void start()
    {


        utl.inputDialog(ctx, "Enter Phone", "Enter Phone No. of visitor !", utl.TYPE_PHONE, new utl.InputDialogCallback() {
            @Override
            public void onDone(String text) {


                final Intent newc=new Intent(ctx,Click.class);
                newc.putExtra("field1",text);

                String url=Constants.HOST+Constants.API_GET_CKECK_VISITOR+"?field1="+ URLEncoder.encode(text);
                utl.l("visit",url);
                AndroidNetworking.get(url).build().getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            utl.l("visit"+response);
                            JSONArray j=new JSONArray(response);
                            if(response.length()>0){
                                JSONObject vi=j.getJSONObject(0);

                                Visit vs=utl.js.fromJson(vi.toString(),Visit.class);
                                if(vs!=null){

                                    final Intent it=new Intent(ctx,AddVisit.class);

                                    it.putExtra("img",vi.getString("image"));
                                    it.putExtra("field1",vi.getString("field1"));
                                    it.putExtra("visit",vi.toString());


                                    startActivity(it);
                                    return;
                                }





                            }



                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        startActivity(newc);
                    }

                    @Override
                    public void onError(ANError ANError) {
                        startActivity(newc);

                    }
                });



            }
        });

    }

    @Override
    protected void onPostResume() {

        super.onPostResume();

        String url=Constants.HOST+Constants.API_GET_VISITS;
        utl.l(url);
        if(!utl.isAdmin)
        {
            url=url+"?id="+utl.getKey("house_id",ctx);
        }
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








                String dateTim="";

                try {
                    Calendar cal = Calendar.getInstance();
                    Date currentLocalTime = cal.getTime();

                    DateFormat date = new SimpleDateFormat("yyyy-MM-dd");

                    dateTim = date.format(currentLocalTime);

                    System.out.println(dateTim);



                    getVisitByDate(dateTim,dummies);
                } catch (Exception e) {

                    setUpList(dummies);
                    e.printStackTrace();
                }

                ////  setUpList(dummies);


                //   setUpList(dummies);



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

      // utl.snack(act,"No Records !");



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



        GRecyclerAdapter adapter=new GRecyclerAdapter(ctx,dumm){
            @Override
            public void exit(Visit cat) {
                super.exit(cat);

                    utl.l("Exiting!");
                    String url=Constants.HOST+Constants.API_EXIT_VISITOR+"?visit_id="+cat.id;
                    AndroidNetworking.get(url).build().getAsString(new StringRequestListener() {
                        @Override
                        public void onResponse(String response) {
                            utl.l("EXit succ");
                            onPostResume();
                        }

                        @Override
                        public void onError(ANError ANError) {
                            utl.l("EXit fail "+ANError.getErrorDetail());

                        }
                    });
            }
        };
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

                    stopService(new Intent(ctx,VisitCheck.class));
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
