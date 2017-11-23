package in.htec.visitrec;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;

import java.util.ArrayList;

import in.htec.visitrec.adapters.GRecyclerAdapter;
import in.htec.visitrec.database.Visit;
import in.htec.visitrec.utils.DateTimePicker;

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



        dt=new DateTimePicker(act, DateTimePicker.DATE_TIME, new DateTimePicker.DateTimeCallback() {
            @Override
            public void picked(String dateTime) {
                utl.snack(act,dateTime);
            }
        });



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dt.pick(false);

            }
        });

        String url=Constants.HOST+Constants.API_GET_VISITS;
        utl.l(url);

        load(LOADING);
        AndroidNetworking.get(url).build().getAsJSONArray(new JSONArrayRequestListener() {
            @Override
            public void onResponse(JSONArray response) {
                dummies=new ArrayList<>();

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



    public void setUpList(ArrayList<GRecyclerAdapter.Dummy> dummies)
    {


        GRecyclerAdapter adapter=new GRecyclerAdapter(ctx,dummies);
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





}