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
    public String field0;
    @SerializedName("field1")
    @Expose
    public String field1;
    @SerializedName("field2")
    @Expose
    public String field2;
    @SerializedName("field3")
    @Expose
    public String field3;
    @SerializedName("field4")
    @Expose
    public String field4;
    @SerializedName("field5")
    @Expose
    public String field5;
    @SerializedName("date_time")
    @Expose
    public String dateTime;
    @SerializedName("image")
    @Expose
    public String image;

}
