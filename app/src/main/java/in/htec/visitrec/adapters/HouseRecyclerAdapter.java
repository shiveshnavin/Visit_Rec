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

import java.util.List;

import in.htec.visitrec.ImageViewer;
import in.htec.visitrec.R;
import in.htec.visitrec.database.House;
import in.htec.visitrec.database.Visit;
import in.htec.visitrec.utl;

public class HouseRecyclerAdapter extends RecyclerView.Adapter<HouseRecyclerAdapter.CustomViewHolder> {
    private List<House> feedItemList;
    private Context mContext;

    public HouseRecyclerAdapter(Context context, List<House> feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_house, null);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder customViewHolder, final int i) {

        final House item=feedItemList.get(customViewHolder.getAdapterPosition());

         customViewHolder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remove(customViewHolder.getAdapterPosition(),item);
            }
        });

        customViewHolder.textView.setText(item.getData());



       // Picasso.with(mContext).load(item.image).placeholder(R.drawable.ic_camera_white_36dp).into(customViewHolder.img);
    }

    public void remove(int id,House cat){

    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder
    {

        TextView textView;
        View base;
        ImageView img;


        public CustomViewHolder(View v) {
            super(v);

            base=v;
            textView=(TextView) base.findViewById(R.id.textView);
            img=(ImageView)base.findViewById(R.id.img);




        }
    }








}
