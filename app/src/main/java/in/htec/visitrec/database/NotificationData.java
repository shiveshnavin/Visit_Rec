package in.htec.visitrec.database;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by shivesh on 25/11/17.
 */

public class NotificationData {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("visit_id")
    @Expose
    public String visitId;
    @SerializedName("house_id")
    @Expose
    public String houseId;
    @SerializedName("notified")
    @Expose
    public String notified;
    @SerializedName("date_time")
    @Expose
    public String dateTime;
    @SerializedName("house_no")
    @Expose
    public String houseNo;
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
    @SerializedName("image")
    @Expose
    public String image;

}
