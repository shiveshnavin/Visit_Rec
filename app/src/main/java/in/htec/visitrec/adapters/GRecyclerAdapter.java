package in.htec.visitrec.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

import in.htec.visitrec.ImageViewer;
import in.htec.visitrec.R;
import in.htec.visitrec.database.House;
import in.htec.visitrec.database.Visit;
import in.htec.visitrec.utl;

public class GRecyclerAdapter extends RecyclerView.Adapter<GRecyclerAdapter.CustomViewHolder> {
    private List<Dummy> feedItemList;
    private Context mContext;

    public GRecyclerAdapter(Context context, List<Dummy> feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, null);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder customViewHolder, final int i) {

        final Dummy item=feedItemList.get(customViewHolder.getAdapterPosition());

        customViewHolder.textView.setText(Html.fromHtml(item.getData(customViewHolder.getAdapterPosition())));
        customViewHolder.img.setVisibility(View.GONE);
        customViewHolder.img.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    Glide.with(mContext).load(item.image).placeholder(R.drawable.ic_camera_white_36dp).centerCrop().into(customViewHolder.img);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        },400);

        utl.l("Image Is :" +item.image);
        customViewHolder.base.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent(mContext, ImageViewer.class);
                it.putExtra("img",item.image);
                it.putExtra("jstr",utl.js.toJson(item));
                mContext.startActivity(it);
            }
        });

        if(!item.getExit().contains("NOT")){
            customViewHolder.exit.setVisibility(View.GONE);
        }
        customViewHolder.exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exit(item);
            }
        });


       // Picasso.with(mContext).load(item.image).placeholder(R.drawable.ic_camera_white_36dp).into(customViewHolder.img);
    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder
    {

        TextView textView;
        View base;
        ImageView img,exit;


        public CustomViewHolder(View v) {
            super(v);

            base=v;
            textView=(TextView) base.findViewById(R.id.textView);
            img=(ImageView)base.findViewById(R.id.img);
            exit=(ImageView)base.findViewById(R.id.exit);




        }
    }

    public void exit(Visit cat){



    }

    public static class Dummy extends Visit
    {

        public String getData(int i)
        {
            String data="";
            data=data+"<b>Visit ID #"+id+"</b>";
            data=data+"\n<br><b>Name : </b>"+field0;
            data=data+"\n<br><b>House No : </b>"+houseNo;
            data=data+"\n<br><b>Time : </b>"+dateTime;

            return data;
        }

        public String getData( )
        {
            String data="";
            data=data+"<b>Visit ID #"+id+"</b>";
            data=data+"\n<br><b>Name : </b>"+field0;
            data=data+"\n<br><b>Phone : </b>"+field1;
            data=data+"\n<br><b>House No : </b>"+houseNo;
            data=data+"\n<br><b>Purpose : </b>"+field2;
            data=data+"\n<br><b>Purpose 2 : </b>"+field3;
            data=data+"\n<br><b>Purpose 3 : </b>"+field4;
            data=data+"\n<br><b>Time : </b>"+dateTime;
            data=data+"\n<br><b>Visiting From : </b>"+field5;

            return data;
        }
    }






}
