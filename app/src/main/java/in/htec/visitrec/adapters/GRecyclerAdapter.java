package in.htec.visitrec.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import in.htec.visitrec.R;
import in.htec.visitrec.database.Visit;

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

        final Dummy item=feedItemList.get(i);

        customViewHolder.textView.setText(Html.fromHtml(item.getData(i)));
        Picasso.with(mContext).load(item.image).placeholder(R.drawable.ic_camera_white_36dp).into(customViewHolder.img);
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


    public static class Dummy extends Visit
    {
        String data="TEST";

        public String getData(int i)
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
