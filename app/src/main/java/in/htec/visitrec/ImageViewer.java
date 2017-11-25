package in.htec.visitrec;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

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



        ZoomImageView activity_image_view=(ZoomImageView)findViewById(R.id.activity_image_view);
        try {
            Picasso.with(this).load(getIntent().getStringExtra("img")).placeholder(R.drawable.rounded_black_filled).into(activity_image_view);
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
