package in.htec.visitrec.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import in.htec.visitrec.R;
import in.htec.visitrec.utl;

/**
 * Created by shivesh on 23/11/17.
 */

public class DateTimePicker {


    public static interface DateTimeCallback{

        public void picked(String dateTime);

    }


    public Context ctx;
    public Activity act;
    public static int DATE_ONLY =101, TIME_ONLY =105,DATE_TIME=106;

    public int PICK_TYPE=DATE_TIME;

    public String format="dd MMMM yyyy - hh:mm aa";


    public DateTimeCallback cb;

    public DateTimePicker(Activity at,int pick,DateTimeCallback callback){


        act=at;
        ctx=act;
        cb=callback;
        PICK_TYPE=pick;
    }


    public DateTimePicker(Activity at,int pick,String frm,DateTimeCallback callback){


        act=at;
        ctx=act;
        cb=callback;
        format=frm;
        PICK_TYPE=pick;


    }



    View v=null;
    Dialog dig;
    public void pick( boolean datePicked){

        AlertDialog.Builder di;
        if(!datePicked||v==null){

            if(dig!=null)
            {
                if(dig.isShowing())
                    dig.dismiss();
            }
            v=act.getLayoutInflater().inflate(R.layout.date_time_picker,null);
            di = new AlertDialog.Builder(ctx);
            di.setView(v);
            dig=di.create();
            dig.show();

        }


        if(v!=null) {
            final DatePicker date = (DatePicker) v.findViewById(R.id.datePicker1);
            final TimePicker time = (TimePicker) v.findViewById(R.id.timePicker1);
            final Button login = (Button) v.findViewById(R.id.login);


            if(PICK_TYPE== TIME_ONLY)
            {
                date.setVisibility(View.GONE);
                time.setVisibility(View.VISIBLE);
                login.setText("DONE");
                format="hh:mm aa";



            }
            else if(PICK_TYPE== DATE_ONLY)
            {
                time.setVisibility(View.GONE);
                date.setVisibility(View.VISIBLE);
                login.setText("DONE");
                format="dd MMMM yyyy";

            }
            else
            {
                if(datePicked)
                {
                    date.setVisibility(View.GONE);
                    time.setVisibility(View.VISIBLE);
                }else{
                    time.setVisibility(View.GONE);
                    date.setVisibility(View.VISIBLE);

                }

            }


            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    //dd MM yyyy - HH:ii p
                    Calendar cal=Calendar.getInstance();
                    SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
                    int monthnum=date.getMonth();
                    cal.set(Calendar.MONTH,monthnum);
                    String month_name = month_date.format(cal.getTime());

                    //   utl.snack(act,"min "+time.getCurrentMinute());
                    Integer selMin;
                    if(time.getCurrentMinute()>=30)
                    {
                        selMin=30;
                    }
                    else
                    {
                        selMin=0;
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        time.setMinute(selMin);
                    }
                    else
                        time.setCurrentMinute(selMin);

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(date.getYear(), date.getDayOfMonth(), date.getDayOfMonth(),
                            time.getCurrentHour(),selMin);

                    SimpleDateFormat formatter = new SimpleDateFormat(format);
                    String output = formatter.format(calendar.getTime()); //eg: "Tue May"


                    utl.e("DateTimePicker","Selected :  "+" -> "+output);



                    if(login.getText().toString().contains("NEXT")&&PICK_TYPE==DATE_TIME)
                    {
                        login.setText("DONE");
                        pick(true);

                    }
                    else {

                        dig.dismiss();;
                        cb.picked(output);
                     }



                }
            });

        }

    }








}
