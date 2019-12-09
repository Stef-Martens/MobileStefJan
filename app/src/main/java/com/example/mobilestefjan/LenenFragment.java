package com.example.mobilestefjan;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

public class LenenFragment extends Fragment {


    DatabankVrienden myDb;
    EditText etOmschrijving;
    EditText etBedrag;
    EditText etDatum;
    Spinner ddlvrienden;
    Button btnOpslaan;
    Button btnAnnuleren;
    List<String> vrienden;
    RadioGroup radioGroup;
    RadioButton radioButtonGekozen;
    RadioButton r1;
    RadioButton r2;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_lenen, container, false);

        myDb=new DatabankVrienden(getActivity());


        etOmschrijving=view.findViewById(R.id.txtOmschrijving);
        etBedrag=view.findViewById(R.id.txtBedrag);
        etDatum=view.findViewById(R.id.txtDatum);
        btnAnnuleren=view.findViewById(R.id.btnAnnuleren);
        btnOpslaan=view.findViewById(R.id.btnOpslaanLening);
        ddlvrienden=view.findViewById(R.id.ddlVrienden);
        radioGroup = view.findViewById(R.id.radiogroup);
        r1=view.findViewById(R.id.radioLenen);
        r2=view.findViewById(R.id.radioUitlenen);
        vrienden= myDb.krijgAlleVriendenIndll();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, vrienden);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ddlvrienden.setAdapter(dataAdapter);

        Opslaan();
        Annuleren();
        return view;
    }

    public void Annuleren(){
        btnAnnuleren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new HomeFragment());

            }
        });
    }

    public void Opslaan(){
        btnOpslaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String voornaam=ddlvrienden.getSelectedItem().toString();
                int id=ddlvrienden.getSelectedItemPosition();
                Cursor res= myDb.KrijgVriend(voornaam);
                //res.moveToPosition(id);
                String achternaam=res.getString(1);
                int bedrag=Integer.parseInt(etBedrag.getText().toString());
                int vorigBedrag=Integer.parseInt(res.getString(3));
                radioButtonGekozen =checkRadio(getView());
                int nieuwBedrag=0;
                String textRadio=radioButtonGekozen.getText().toString();
                Toast.makeText(getActivity(),textRadio,Toast.LENGTH_SHORT).show();
                if(radioButtonGekozen.getId()==r1.getId()){
                    nieuwBedrag=vorigBedrag-bedrag;
                }
                else{
                    nieuwBedrag=vorigBedrag+bedrag;
                }


                String nieuwGeld= String.valueOf(nieuwBedrag);

                Boolean update=myDb.updateData(id,achternaam,voornaam,nieuwGeld);

                replaceFragment(new HomeFragment());
            }
        });
    }

    public RadioButton checkRadio(View view){
        int radioid=radioGroup.getCheckedRadioButtonId();
        return radioButtonGekozen=view.findViewById(radioid);
    }



    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
