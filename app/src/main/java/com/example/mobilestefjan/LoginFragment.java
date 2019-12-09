package com.example.mobilestefjan;

import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
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

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.internal.NavigationMenu;

public class LoginFragment extends Fragment {

    Button btnLogin, btnRegistreer;
    DatabankVoorJezelf myDb;
    EditText etAchternaam, etWachtwoord;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_login, container, false);


        btnLogin=view.findViewById(R.id.btnLogin);
        btnRegistreer=view.findViewById(R.id.btnRegistreer);
        myDb=new DatabankVoorJezelf((getActivity()));
        etAchternaam=view.findViewById(R.id.txtlogAchternaam);
        etWachtwoord=view.findViewById(R.id.txtWachtwoord);
        //myDb.MaakOpnieuwDatabankAan();

        Login();
        Registreer();
        hideBottomNavigationView(new BottomNavigationView(getActivity()));

         return view;
    }

    private void hideBottomNavigationView(BottomNavigationView view) {
        view.animate().translationY(view.getHeight());
    }


    private void Login(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor res= myDb.KrijgEigenGegevens(etAchternaam.getText().toString(),etWachtwoord.getText().toString());

                    if(TextUtils.isEmpty(etAchternaam.getText().toString()))
                    {
                        etAchternaam.setError("Mag niet leeg zijn");
                    }
                    else if(TextUtils.isEmpty(etWachtwoord.getText().toString()))
                    {
                        etWachtwoord.setError("Mag niet leeg zijn");
                    }
                    else{
                        try{
                            String yeet=res.getString(0);
                            replaceFragment(new HomeFragment());

                        }
                        catch (Exception e){
                            Toast.makeText(getActivity(),"Deze gebruiker bestaat niet",Toast.LENGTH_SHORT).show();
                        }
                    }



            }
        });
    }

    private void Registreer(){
        btnRegistreer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            replaceFragment(new RegistratieFragment());


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
