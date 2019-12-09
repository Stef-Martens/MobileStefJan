package com.example.mobilestefjan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class VriendenListAdapter extends BaseAdapter {
    Context context;
    ArrayList<Vrienden> arrayList;

    public VriendenListAdapter(Context context, ArrayList<Vrienden> arrayList){
        this.context=context;
        this.arrayList=arrayList;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(R.layout.vriendenadapter_view_layout,null);
        TextView txtVolNaam= convertView.findViewById(R.id.txtVolNaam);
        TextView txtGeld= convertView.findViewById(R.id.txtGeld);

        Vrienden vrienden=arrayList.get(position);

        txtVolNaam.setText(vrienden.getAchternaam()+" "+vrienden.getVoornaam());
        String geld=vrienden.getGeld();
        int geldinint=Integer.parseInt(geld);
        if(geldinint>=0){
            txtGeld.setText("+"+vrienden.getGeld()+" EUR");
        }
        else{
            txtGeld.setText(vrienden.getGeld()+" EUR");
        }




        return convertView;
    }

    @Override
    public int getCount() {
        return this.arrayList.size();
    }
}


