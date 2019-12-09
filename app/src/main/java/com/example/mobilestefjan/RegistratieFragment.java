package com.example.mobilestefjan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class RegistratieFragment extends Fragment {

    Button btnAnnuleren, btnRegistreren;
    DatabankVoorJezelf myDb;
    EditText etAchternaam, etVoornaam, etWachtwoord;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_registreer, container, false);

        btnAnnuleren=view.findViewById(R.id.btnAnnulatie);
        btnRegistreren=view.findViewById(R.id.btnRegistratie);
        myDb=new DatabankVoorJezelf(getActivity());
        etAchternaam=view.findViewById(R.id.txtAchternaamBijRegistratie);
        etVoornaam=view.findViewById(R.id.txtVoornaamBijRegistratie);
        etWachtwoord=view.findViewById(R.id.txtWachtwoordBijRegistratie);

        Registreer();
        Annuleren();
        return view;
    }

    private void Annuleren(){
        btnAnnuleren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new LoginFragment());
            }
        });
    }

    private void Registreer(){
        btnRegistreren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Jezelf jezelf=new Jezelf(etAchternaam.getText().toString(),etVoornaam.getText().toString(),"0", etWachtwoord.getText().toString());

                if(myDb.ifExists(jezelf)){
                    Toast.makeText(getActivity(),"Deze gebruiker bestaat al",Toast.LENGTH_SHORT).show();
                }
                else{
                    myDb.insertData(jezelf);
                    Toast.makeText(getActivity(),"Gebruiker toegevoegd",Toast.LENGTH_SHORT).show();
                    replaceFragment(new LoginFragment());
                }

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
