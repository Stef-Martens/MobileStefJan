package com.example.mobilestefjan;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {

    DatabankTransacties myDb;
    ListView lv;
    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_history, container, false);

        myDb=new DatabankTransacties(getActivity());
        lv=view.findViewById(R.id.listTransacties);
        arrayList=new ArrayList<>();
        adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,arrayList);
        lv.setAdapter(adapter);
        AddData();
        return view;
    }

    public  void AddData() {
        Cursor res = myDb.getAllData();
        if (res.getCount() == 0) {
            Toast.makeText(getActivity(),"yeet",Toast.LENGTH_LONG).show();
            //return ;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {

            String VolLijn=res.getString(3)+"             "+res.getString(1);
            arrayList.add(VolLijn);
            adapter.notifyDataSetChanged();
                    }

    }

}

