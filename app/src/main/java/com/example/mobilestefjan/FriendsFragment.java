package com.example.mobilestefjan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
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
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class FriendsFragment extends Fragment {


    ListView lv;
    ArrayList<Vrienden> arrayList;
    VriendenListAdapter adapter;
    Button bt;
    EditText etAchternaam;
    EditText etVoornaam;
    DatabankVrienden myDb;
    String GeldVereffenen;
    DatabankVoorJezelf myDbVoorJezelf;


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
        VriendenListAdapter adapter=new VriendenListAdapter(getActivity(),arrayList);

        //adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,arrayList);
        lv.setAdapter(adapter);
        myDb=new DatabankVrienden(getActivity());
        myDbVoorJezelf = new DatabankVoorJezelf(getActivity());
        VorigeDataToevoegen();
        AddData();
        vriendVerwijderen();
        ItemKlik();
        return  view;
    }

    public void ItemKlik() {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final int which_item=position;
                myDb.getWritableDatabase();
                myDbVoorJezelf.getWritableDatabase();
                final Cursor res = (Cursor) myDb.getAllDataCursor();
                final Cursor c=(Cursor) myDbVoorJezelf.getAllDataCursor();
                res.moveToPosition(position);
                c.moveToPosition(0);
                String geld=res.getString(3);
                char eersteTeken=geld.charAt(0);

                final EditText input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_TEXT);


                if(eersteTeken!='-' && Integer.valueOf(geld)!=0){
                    new AlertDialog.Builder(getActivity())
                            //.setIcon(android.R.drawable.ic_delete)
                            .setTitle("Geld vereffenen")
                            .setMessage("Hoeveel heeft "+res.getString(2)+" je terugbetaald?")
                            .setNegativeButton("cancel", null)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        GeldVereffenen = input.getText().toString();
                                        int Saldo = Integer.valueOf(res.getString(3)) - Integer.valueOf(GeldVereffenen);
                                        myDb.updateData(res.getInt(0), res.getString(1), res.getString(2), String.valueOf(Saldo));
                                        int Geld = Integer.valueOf(c.getString(2)) + Integer.valueOf(GeldVereffenen);
                                        myDbVoorJezelf.updateData(c.getString(0), c.getString(1), String.valueOf(Geld), c.getString(3));
                                        replaceFragment(new FriendsFragment());
                                    }
                                    catch (Exception e){
                                        Toast.makeText(getContext(),"Ongeldig bedrag ingegeven",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .setView(input)
                            .show();
                }
                else if (eersteTeken=='-'){
                    new AlertDialog.Builder(getActivity())
                            //.setIcon(android.R.drawable.ic_delete)
                            .setTitle("Geld vereffenen")
                            .setMessage("Hoeveel heb je aan "+res.getString(2)+" terugbetaald?")
                            .setNegativeButton("cancel", null)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    try{                                    GeldVereffenen=input.getText().toString();
                                        int Saldo = Integer.valueOf(res.getString(3)) + Integer.valueOf(GeldVereffenen);

                                        myDb.updateData(res.getInt(0), res.getString(1), res.getString(2), String.valueOf(Saldo));

                                        int Geld=Integer.valueOf(c.getString(2)) - Integer.valueOf(GeldVereffenen);
                                        myDbVoorJezelf.updateData(c.getString(0), c.getString(1),String.valueOf( Geld) ,c.getString(3));
                                        replaceFragment(new FriendsFragment());
                                    }
                                    catch (Exception e){
                                        Toast.makeText(getContext(),"Ongeldig bedrag ingegeven",Toast.LENGTH_SHORT).show();
                                    }

                                }
                            })
                            .setView(input)
                            .show();
                }
                else{
                    Toast.makeText(getActivity(),"Er is geen lening te vereffenen",Toast.LENGTH_SHORT).show();
                }


            }
        });
    }




    public  void AddData() {
        bt.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(TextUtils.isEmpty(etAchternaam.getText().toString()))
                        {
                            etAchternaam.setError("Mag niet leeg zijn");
                        }
                        else if(TextUtils.isEmpty(etVoornaam.getText().toString()))
                        {
                            etVoornaam.setError("Mag niet leeg zijn");
                        }
                        else{
                            String achternaam=etAchternaam.getText().toString().substring(0,1).toUpperCase() + etAchternaam.getText().toString().substring(1);
                            String voornaam=etVoornaam.getText().toString().substring(0,1).toUpperCase() + etVoornaam.getText().toString().substring(1);

                            if(myDb.ifExists(achternaam,voornaam)){
                                Toast.makeText(getActivity(),"Deze vriend bestaat al",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                String geld="0";
                                boolean isInserted = myDb.insertData(achternaam, voornaam, geld);
                                if(isInserted == true)
                                    Toast.makeText(getActivity(),"Vriend toegevoegd",Toast.LENGTH_LONG).show();

                                Vrienden vriend=new Vrienden(achternaam,voornaam,geld);
                                arrayList.add(vriend);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                }
        );
    }




    public void VorigeDataToevoegen(){

        arrayList=myDb.getAllDAta();
        adapter=new VriendenListAdapter(getActivity(),arrayList);
        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }


    public void vriendVerwijderen(){
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick (AdapterView<?> parent,View v, int positie, long id) {


                final int which_item=positie;

                myDb.getWritableDatabase();
                final Cursor res = (Cursor) myDb.getAllDataCursor();
                res.moveToPosition(which_item);

                new AlertDialog.Builder(getActivity())
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Are you sure?")
                        //.setMessage("Do you want to delete this item?")
                        .setMessage("Do you want to delete "+res.getString(2)+"?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //String verw= Integer.toString(which_item);
                                myDb.deleteData(res.getString(0));
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

    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
