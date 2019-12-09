package com.example.mobilestefjan;

import android.database.Cursor;
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

public class LoginFragment extends Fragment {

    Button btnLogin, btnRegistreer;
    DatabankVoorJezelf myDb;
    EditText etAchternaam, etVoornaam;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_login, container, false);

        btnLogin=view.findViewById(R.id.btnLogin);
        btnRegistreer=view.findViewById(R.id.btnRegistreer);
        myDb=new DatabankVoorJezelf((getActivity()));
        etAchternaam=view.findViewById(R.id.txtlogAchternaam);
        etVoornaam=view.findViewById(R.id.txtlogVoornaam);

        Login();
        Registreer();

         return view;
    }


    public void Login(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor res= myDb.KrijgEigenGegevens(etVoornaam.getText().toString());


                try{
                    String yeet=res.getString(0);
                    replaceFragment(new HomeFragment());

                }
                catch (Exception e){
                    Toast.makeText(getActivity(),"Deze gebruiker bestaat niet",Toast.LENGTH_SHORT).show();
                }



            }
        });
    }

    public void Registreer(){
        btnRegistreer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myDb.insertData(etAchternaam.getText().toString(),etVoornaam.getText().toString(),"0");
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
