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

import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

import in.htec.visitrec.adapters.GRecyclerAdapter;
import in.htec.visitrec.utils.DateTimePicker;

public class Home extends AppCompatActivity {



    DateTimePicker dt;



    public Context ctx;
    public Activity act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx=this;
        act=this;
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        rec =(RecyclerView)findViewById(R.id.rec);


        ArrayList<GRecyclerAdapter.Dummy> dummies=new ArrayList<>();
        dummies.add(new GRecyclerAdapter.Dummy());
        dummies.add(new GRecyclerAdapter.Dummy());
        dummies.add(new GRecyclerAdapter.Dummy());
        dummies.add(new GRecyclerAdapter.Dummy());
        dummies.add(new GRecyclerAdapter.Dummy());
        dummies.add(new GRecyclerAdapter.Dummy());
        dummies.add(new GRecyclerAdapter.Dummy());
        dummies.add(new GRecyclerAdapter.Dummy());
        dummies.add(new GRecyclerAdapter.Dummy());
        dummies.add(new GRecyclerAdapter.Dummy());

        GRecyclerAdapter adapter=new GRecyclerAdapter(ctx,dummies);
        rec.setLayoutManager(new LinearLayoutManager(act));
        rec.setAdapter(adapter);




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dt.pick(false);

            }
        });








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
