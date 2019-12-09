package com.example.mobilestefjan;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class HistoryFragment extends Fragment {

    DatabankTransacties myDb;
    ListView lv;
    ArrayList<Transacties> arrayList;
    TransactieListAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_history, container, false);

        myDb=new DatabankTransacties(getActivity());
        lv=view.findViewById(R.id.listTransacties);
        arrayList=new ArrayList<>();

        TransactieListAdapter adapter=new TransactieListAdapter(getActivity(),arrayList);

        //adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,arrayList);
        AddData();
        ItemKlik();
        return view;
    }



    public void ItemKlik(){
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent,View v, int positie, long id) {


                final int which_item=positie;
                myDb.getWritableDatabase();
                Cursor res = (Cursor) myDb.getAllDataCursor();
                res.moveToPosition(positie);
                //byte[] data = res.getBlob(4);
                //ByteArrayInputStream imageStream = new ByteArrayInputStream(data);
                //Bitmap theImage = BitmapFactory.decodeStream(imageStream);
                //ImageView image= new ImageView(getActivity());
                //image.setImageBitmap(theImage);

                //Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                //ImageView image = new ImageView(getActivity());
                //image.setImageBitmap(Bitmap.createScaledBitmap(bmp, image.getWidth(),
                 //       image.getHeight(), false));

                byte[] accImage = res.getBlob(4);
                ImageView image = new ImageView(getActivity());
                image.setImageBitmap(BitmapFactory.decodeByteArray( accImage,
                        0,accImage.length));

                String titel=res.getString(3);

                new AlertDialog.Builder(getActivity())
                        //.setIcon(android.R.drawable.ic_delete)
                        .setTitle(titel)
                        .setMessage("info transactie")
                        .setMessage("Bedrag: "+res.getString(1)+" EUR"+"\n"+"Datum: "+res.getString(2)+"\n"+"Foto: ")
                        .setPositiveButton("cancel", null)
                        .setView(image)
                        .show();
            }
        });
    }

    public  void AddData() {

    arrayList=myDb.getAllDAta();
    adapter=new TransactieListAdapter(getActivity(),arrayList);
        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

//        Cursor res = myDb.getAllData();
//        if (res.getCount() == 0) {
//            Toast.makeText(getActivity(),"yeet",Toast.LENGTH_LONG).show();
//            //return ;
//        }
//        StringBuffer buffer = new StringBuffer();
//        while (res.moveToNext()) {
//
//            String VolLijn=res.getString(3)+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+res.getString(1);
//            arrayList.add(VolLijn);
//            adapter.notifyDataSetChanged();
//                    }
    }

}

