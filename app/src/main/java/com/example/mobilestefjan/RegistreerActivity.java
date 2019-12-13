package com.example.mobilestefjan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



public class RegistreerActivity extends AppCompatActivity {

    Button btnAnnuleren, btnRegistreren;
    DatabankVoorJezelf myDb;
    EditText etAchternaam, etVoornaam, etWachtwoord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registreren);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        btnAnnuleren=this.findViewById(R.id.btnAnnulatie);
        btnRegistreren=this.findViewById(R.id.btnRegistratie);
        myDb=new DatabankVoorJezelf(this);
        etAchternaam=this.findViewById(R.id.txtAchternaamBijRegistratie);
        etVoornaam=this.findViewById(R.id.txtVoornaamBijRegistratie);
        etWachtwoord=this.findViewById(R.id.txtWachtwoordBijRegistratie);

        Registreer();
        Annuleren();


    }

    private void Annuleren(){
        btnAnnuleren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), LoginActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }

    private void Registreer(){
        btnRegistreren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Jezelf jezelf=new Jezelf(etAchternaam.getText().toString(),etVoornaam.getText().toString(),"0", etWachtwoord.getText().toString());

                if(myDb.ifExists(jezelf)){
                    Toast.makeText(getApplicationContext(),"Deze gebruiker bestaat al",Toast.LENGTH_SHORT).show();
                }
                else{
                    myDb.insertData(jezelf);
                    Toast.makeText(getApplicationContext(),"Gebruiker toegevoegd",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(v.getContext(), LoginActivity.class);
                    v.getContext().startActivity(intent);
                }

            }
        });

    }
}
