package in.htec.visitrec.database;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by shivesh on 10/11/17.
 */

public class House {


    public String passwd="Houses Name";
    public String intercom="Houses Name";



    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("no")
    @Expose
    public String no;
    @SerializedName("add")
    @Expose
    public String add;
    @SerializedName("owner")
    @Expose
    public String owner;
    @SerializedName("phone")
    @Expose
    public String phone;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("extra0")
    @Expose
    public String extra0;

    public String  getIntercom()
    {
        try {
            JSONObject jsonObject=new JSONObject(extra0);
            return jsonObject.getString("extra0");
        } catch (JSONException e) {

            e.printStackTrace();
            return "0";
        }

    }



    public String  getPasswd()
    {
        try {
            JSONObject jsonObject=new JSONObject(extra0);
            return jsonObject.getString("passwd");
        } catch (JSONException e) {

            e.printStackTrace();
            return "";
        }

    }



    public House()
    {



    }


}
