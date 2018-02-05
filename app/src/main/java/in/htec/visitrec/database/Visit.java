package in.htec.visitrec.database;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by shivesh on 23/11/17.
 */

public class Visit {


    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("house_no")
    @Expose
    public String houseNo;
    @SerializedName("house_id")
    @Expose
    public String houseId;
    @SerializedName("field0")
    @Expose
    public String field0; //name
    @SerializedName("field1")
    @Expose
    public String field1; //phone
    @SerializedName("field2")
    @Expose
    public String field2; //work or personal
    @SerializedName("field3")
    @Expose
    public String field3; //resson of work
    @SerializedName("field4")
    @Expose
    public String field4; //purpose
    @SerializedName("field5")
    @Expose
    public String field5; //visiting from
    @SerializedName("date_time")
    @Expose
    public String dateTime;
    @SerializedName("image")
    @Expose
    public String image;
    @SerializedName("exit")
    @Expose
    public String exit;



    public String getExit(){


        if(exit.contains("0000")){
            return "NOT_EXITED";
        }

        return exit;

    }




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
        data=data+"\n<br><b>Exited : </b>"+getExit();

        return data;
    }



}
