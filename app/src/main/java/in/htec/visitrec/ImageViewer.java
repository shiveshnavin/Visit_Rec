package in.htec.visitrec;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.squareup.picasso.Picasso;

import in.htec.visitrec.adapters.GRecyclerAdapter;
import in.htec.visitrec.utils.ZoomImageView;


public class ImageViewer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        try {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } catch (Exception e) {
            e.printStackTrace();
        }

        setContentView(R.layout.activity_image_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_48dp);

        String jstr=getIntent().getStringExtra("jstr");
        String igmg=getIntent().getStringExtra("img");

        if(jstr==null)
        {
            finish();

        }
        GRecyclerAdapter.Dummy dm=utl.js.fromJson(jstr, GRecyclerAdapter.Dummy.class);


        TextView data = (TextView) findViewById(R.id.data);
        data.setText(Html.fromHtml(dm.getData() ));


        utl.showDig(true,ImageViewer.this);
        final ZoomImageView activity_image_view=(ZoomImageView)findViewById(R.id.activity_image_view);
        try {
            //Picasso.with(this).load(getIntent().getStringExtra("img"))
            Glide.with(this).load(getIntent().getStringExtra("img")).listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {

                    Picasso.with(ImageViewer.this).load(R.drawable.rounded_black_filled).into(activity_image_view);

                    utl.showDig(false,ImageViewer.this);

                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {



                    utl.showDig(false,ImageViewer.this);





                    return false;
                }
            }).placeholder(R.drawable.rounded_black_filled).into(activity_image_view);







        } catch (Exception e) {
            e.printStackTrace();
            Picasso.with(this).load(R.drawable.rounded_black_filled).into(activity_image_view);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //TODO
        int id=item.getItemId();
        if(id==android.R.id.home)
        {
            finish();

        }

        return super.onOptionsItemSelected(item);
    }




}
