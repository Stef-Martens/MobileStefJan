package com.example.mobilestefjan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
    public void onCreate(Bundle savedInstanceState) {
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

    public void Registreer(){
        btnRegistreren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(etVoornaam.getText().toString()))
                {
                    etVoornaam.setError("Mag niet leeg zijn");
                }
                else if(TextUtils.isEmpty(etAchternaam.getText().toString()))
                {
                    etAchternaam.setError("Mag niet leeg zijn");
                }
                else if(TextUtils.isEmpty(etWachtwoord.getText().toString()))
                {
                    etWachtwoord.setError("Mag niet leeg zijn");
                }
                else{
                    Jezelf jezelf=new Jezelf(etAchternaam.getText().toString(),etVoornaam.getText().toString(),"0", etWachtwoord.getText().toString());

                    if(myDb.ifExists(jezelf)){
                        Toast.makeText(getApplicationContext(),"Deze gebruiker bestaat al",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        new AlertDialog.Builder(v.getContext())
                                .setIcon(android.R.drawable.ic_delete)
                                .setTitle("Ben je zeker?")
                                //.setMessage("Do you want to delete this item?")
                                .setMessage("Je kan maar 1 account hebben, dit wilt zeggen dat je andere account verwijdert wordt")
                                .setPositiveButton("ja", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        JezelfToevoegen();
                                    }
                                })
                                .setNegativeButton("no", null)
                                .show();
                    }
                }







//                else{
//                    myDb.insertData(jezelf);
//                    Toast.makeText(getApplicationContext(),"Gebruiker toegevoegd",Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(v.getContext(), LoginActivity.class);
//                    v.getContext().startActivity(intent);
//                }

            }
        });
    }


    public void JezelfToevoegen(){
        String achternaam=etAchternaam.getText().toString().substring(0,1).toUpperCase() + etAchternaam.getText().toString().substring(1);
        String voornaam=etVoornaam.getText().toString().substring(0,1).toUpperCase() + etVoornaam.getText().toString().substring(1);

        Jezelf jezelf=new Jezelf(achternaam,voornaam,"0", etWachtwoord.getText().toString());
        myDb.MaakOpnieuwDatabankAan();
        DatabankVrienden myDbVrienden;
        myDbVrienden=new DatabankVrienden(this);
        myDbVrienden.MaakOpnieuwDatabankAan();
        DatabankTransacties myDbTransacties=new DatabankTransacties(this);
        myDbTransacties.MaakOpnieuwDatabankAan();
        myDb.insertData(jezelf);
        Toast.makeText(getApplicationContext(),"Gebruiker toegevoegd",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        getApplicationContext().startActivity(intent);
    }
}
