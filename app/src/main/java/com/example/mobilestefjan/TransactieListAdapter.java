package com.example.mobilestefjan;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TransactieListAdapter extends BaseAdapter {

    Context context;
    ArrayList<Transacties> arrayList;

    public TransactieListAdapter(Context context, ArrayList<Transacties> arrayList){
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
        convertView=inflater.inflate(R.layout.adapter_view_layout,null);
        TextView txtOmschrijving= convertView.findViewById(R.id.txtOmschr);
        TextView txtBedrag= convertView.findViewById(R.id.txtBedr);
        TextView txtDatum= convertView.findViewById(R.id.txtDat);

        Transacties transacties=arrayList.get(position);

        txtOmschrijving.setText(transacties.getOmschrijving());
        txtBedrag.setText(transacties.getBedrag());
        txtDatum.setText(transacties.getDatum());




        return convertView;
    }

    @Override
    public int getCount() {
        return this.arrayList.size();
    }
}
