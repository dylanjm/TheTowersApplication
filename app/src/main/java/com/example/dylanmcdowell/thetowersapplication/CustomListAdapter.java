package com.example.dylanmcdowell.thetowersapplication;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomListAdapter extends ArrayAdapter<Request> {

    public CustomListAdapter(Context context, int resource, List<Request> requests) {
        super(context, resource, requests);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if (v == null) {
            System.out.println("V == null");
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.maitenence_layout, parent, false);
        }

        Request p = getItem(position);
        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.textView9);
            if (tt1 != null) {
                if(p.getIsEmergency()){
                    Drawable emergency = getContext().getResources().getDrawable(R.drawable.emergency);
                    emergency.setBounds(0, 0,(emergency.getIntrinsicWidth()*3),(emergency.getIntrinsicHeight()*2));
                    tt1.setCompoundDrawables(null, null, emergency, null);
                    tt1.setText(Html.fromHtml(p.toString().replace("\n","<br />")));
                    tt1.setBackground(getContext().getResources().getDrawable(R.drawable.border));
                } else {
                    tt1.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    tt1.setText(Html.fromHtml(p.toString().replace("\n","<br />")));
                    tt1.setBackground(getContext().getResources().getDrawable(R.drawable.border2));
                }
            }
        }
        return v;
    }
}

