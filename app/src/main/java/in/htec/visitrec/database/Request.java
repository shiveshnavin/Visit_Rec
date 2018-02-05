package in.htec.visitrec.database;

import java.util.ArrayList;

/**
 * Created by shivesh on 10/11/17.
 */

public class Request {

    public ArrayList<House> houses;
    public String house(){
        String hss="";
        for (House h:houses
             ) {

            hss=h.id+":"+hss;

        }
        return hss;
    }
    public String field0;
    public String field1;
    public String field2;
    public String field3;
    public String field4;
    public String field5;
    public String field6;

    public Request(){

    }




}
