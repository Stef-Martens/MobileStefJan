package com.example.mobilestefjan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class FriendsFragment extends Fragment {


    ListView lv;
    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;
    Button bt;
    EditText etAchternaam;
    EditText etVoornaam;
    DatabankVrienden myDb;


    public FriendsFragment(){
        // moet een lege constructor hebben
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        View view= inflater.inflate(R.layout.fragment_friends, container, false);
        bt=view.findViewById(R.id.VriendToevoegen);
        etAchternaam=view.findViewById(R.id.txtAchternaamaam);
        etVoornaam=view.findViewById(R.id.txtVoornaam);
        lv=view.findViewById(R.id.listFriends);
        arrayList=new ArrayList<>();
        adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,arrayList);
        lv.setAdapter(adapter);
        myDb=new DatabankVrienden(getActivity());
        VorigeDataToevoegen();
        AddData();
        //onVriendToevoegen();
        vriendVerwijderen();
        return  view;
    }




    public  void AddData() {
        bt.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String geld="0";
                        boolean isInserted = myDb.insertData(etAchternaam.getText().toString(),
                                etVoornaam.getText().toString(),
                                 geld);
                        if(isInserted == true)
                            Toast.makeText(getActivity(),"Data Inserted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(getActivity(),"Data not Inserted",Toast.LENGTH_LONG).show();

                        String VolLijn=etAchternaam.getText().toString()+" "+etVoornaam.getText().toString()+"            "+geld;
                        arrayList.add(VolLijn);
                        adapter.notifyDataSetChanged();
                    }
                }
        );
    }



    public void onVriendToevoegen(){
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String achternaam=etAchternaam.getText().toString();
                String voornaam=etVoornaam.getText().toString();
                String VolledigeNaam=voornaam+" "+achternaam;
                arrayList.add(VolledigeNaam);
                adapter.notifyDataSetChanged();
            }

        });
    }

    public void VorigeDataToevoegen(){

        Cursor res = myDb.getAllData();
        if (res.getCount() == 0) {
            Toast.makeText(getActivity(),"yeet",Toast.LENGTH_LONG).show();
            //return ;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {

            String VolLijn=res.getString(2)+" "+res.getString(1)+"            "+res.getString(3);
            arrayList.add(VolLijn);
            adapter.notifyDataSetChanged();
//            buffer.append("Id :" + res.getString(0) + "\n");
//            buffer.append("Name :" + res.getString(1) + "\n");
//            buffer.append("Surname :" + res.getString(2) + "\n");
//            buffer.append("Marks :" + res.getString(3) + "\n\n");
        }

        // Show all data
//        showMessage("Data", buffer.toString());
    }


    public void vriendVerwijderen(){
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick (AdapterView<?> parent,View v, int positie, long id) {


                final int which_item=positie;

                new AlertDialog.Builder(getActivity())
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Are you sure?")
                        //.setMessage("Do you want to delete this item?")
                        .setMessage("Do you want to delete "+arrayList.get(which_item)+"?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String verw= Integer.toString(which_item);
                                myDb.deleteData(verw);
                                arrayList.remove(which_item);
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("no", null)
                        .show();

                return true;
            }
        });
    }

}
