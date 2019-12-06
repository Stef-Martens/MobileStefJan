package com.example.mobilestefjan;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

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

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_lenen, container, false);

        myDb=new DatabankVrienden(getActivity());


        etOmschrijving=view.findViewById(R.id.txtOmschrijving);
        etBedrag=view.findViewById(R.id.txtBedrag);
        etDatum=view.findViewById(R.id.txtDatum);
        btnAnnuleren=view.findViewById(R.id.btnAnnuleren);
        btnOpslaan=view.findViewById(R.id.btnOpslaan);
        ddlvrienden=view.findViewById(R.id.ddlVrienden);
        vrienden= myDb.krijgAlleVriendenIndll();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, vrienden);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ddlvrienden.setAdapter(dataAdapter);


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

    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
