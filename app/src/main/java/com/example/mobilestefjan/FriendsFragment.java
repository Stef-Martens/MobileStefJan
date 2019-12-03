package com.example.mobilestefjan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
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
    EditText et;

    public FriendsFragment(){
        // moet een lege constructor hebben
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_friends, container, false);
        bt=view.findViewById(R.id.VriendToevoegen);
        et=view.findViewById(R.id.txtNaam);
        lv=view.findViewById(R.id.listFriends);
        arrayList=new ArrayList<>();
        adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,arrayList);
        lv.setAdapter(adapter);
        //lv.setSelector(positie);
        onVriendToevoegen();
        vriendVerwijderen();
        return  view;
    }

    public void onVriendToevoegen(){
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result=et.getText().toString();
                arrayList.add(result);
                adapter.notifyDataSetChanged();
            }
        });
    }


    public void vriendVerwijderen(){
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick (AdapterView<?> parent,View v, int positie, long id) {


                final int which_item=positie;

                new AlertDialog.Builder(getActivity())
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this item?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
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
