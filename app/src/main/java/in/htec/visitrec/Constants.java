package in.htec.visitrec;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by shivesh on 28/6/17.
 */

public class Constants {



    public static String HOST= "http://tranquille.fairuse.org/cms";

    //@ID (ID of house)
    public static String API_GET_VISITS="/api/get_tours.php";
    public static String API_GET_PURPOSES="/api/get_purposes.php";
    public static String API_GET_HOUSES="/api/get_places.php";
    public static String API_ADD_VISIT="/api/add_tour_v2.php";
    public static String API_USER_LOGIN_GET="/api/login_house.php";
    public static String API_ADMIN_LOGIN_GET="/api/login_admin.php";
    public static String API_GET_NOTIFICATIONS="/api/get_notifications.php";
    public static String API_POST_IMAGE_UPLOAD="/api/upload_image.php";
    public static String API_GET_CKECK_VISITOR="/api/check_visitor.php";
    public static String API_EXIT_VISITOR="/api/mark_exit.php";



    public static boolean IS_ANIMATED_BG_SPLASH=false;
    public static boolean isPdCancelable=true;

    public static String folder;
    public static String datafile;

    public static Context ctx;

    public static void init(Context context)
    {
        utl.init(ctx);
        ctx=context;
        File file=new File(getFolder());
        if(!file.exists())
        {
            file.mkdir();
        }
    }

    private static String FIRE_BASE="https://test-a0930.firebaseio.com/";

    public static String fireURL()
    {
        return Constants.FIRE_BASE+ utl.refineString(ctx.getResources().getString(R.string.app_name),"");
    }


    public static String getFolder()
    {
        folder = Environment.getExternalStorageDirectory().getPath().toString()+"/."+ utl.refineString(ctx.getResources().getString(R.string.app_name),"");
        return folder;
    }


    public static String userDataFile()
    {
        folder = Environment.getExternalStorageDirectory().getPath().toString()+"/."+ utl.refineString(ctx.getResources().getString(R.string.app_name),"");

        File file=new File(folder);
        if(!file.exists())
        {
            file.mkdir();
        }
        datafile=folder+"/firebaseUser.json";
        return datafile;
    }



    public static String localDataFile()
    {
        folder = Environment.getExternalStorageDirectory().getPath().toString()+"/."+ utl.refineString(ctx.getResources().getString(R.string.app_name),"");

        File file=new File(folder);
        if(!file.exists())
        {
            file.mkdir();
        }
        datafile=folder+"/data.json";
        return datafile;
    }



    public static String getApp()
    {
        return utl.refineString(ctx.getResources().getString(R.string.app_name),"");
    }











}
