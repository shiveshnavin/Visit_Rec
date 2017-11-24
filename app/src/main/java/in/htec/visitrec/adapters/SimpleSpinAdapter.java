package in.htec.visitrec.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import in.htec.visitrec.R;

/**
 * Created by shivesh on 29/6/17.
 */

public class SimpleSpinAdapter extends ArrayAdapter<String> {

    // Your sent context
    private Context context;
    // Your custom values for the spinner (Group)
    private ArrayList<String> values;

    public SimpleSpinAdapter(Context context, int textViewResourceId,
                             ArrayList<String> values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
    }

    public int getCount(){
        return values.size();
    }

    public String getItem(int position){
        return values.get(position);
    }

    public long getItemId(int position){
        return position;
    }


    // And the "magic" goes here
    // This is for the "passive" state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setGravity(Gravity.CENTER);
        // Then you can get the current item using the values array (Groups array) and the current position
        // You can NOW reference each method you has created in your bean object (Group class)
        label.setText(values.get(position) );

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,100);
        p.setMargins(0,2,0,2);
         label.setBackground(context.getResources().getDrawable(R.drawable.rounded_selector));
        label.setLayoutParams(p);



        // And finally return your dynamic (or custom) view for each spinner item
        return label;
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setGravity(Gravity.CENTER);
        label.setText(values.get(position) );

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,100);
        p.setMargins(0,2,0,2);

        label.setBackground(context.getResources().getDrawable(R.drawable.rounded_selector));

        label.setLayoutParams(p);


        return label;
    }
}
