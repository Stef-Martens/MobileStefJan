package com.example.mobilestefjan;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin, btnRegistreer;
    DatabankVoorJezelf myDb;
    EditText etAchternaam, etWachtwoord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        btnLogin=this.findViewById(R.id.btnLogin);
        btnRegistreer=this.findViewById(R.id.btnRegistreer);
        myDb=new DatabankVoorJezelf(this);
        etAchternaam=this.findViewById(R.id.txtlogAchternaam);
        etWachtwoord=this.findViewById(R.id.txtWachtwoord);

        Login();
        Registreer();

    }

    private void Login(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


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
                        Cursor res= myDb.KrijgEigenGegevens(etAchternaam.getText().toString(),etWachtwoord.getText().toString());
                        String naam=res.getString(0);
                        Intent myIntent = new Intent(v.getContext() , MainActivity.class);
                        v.getContext().startActivity(myIntent);
                    }
                    catch (Exception e){
                        Toast.makeText(getApplicationContext(),"Deze gebruiker bestaat niet",Toast.LENGTH_SHORT).show();
                    }
                }



            }
        });
    }

    private void Registreer(){
        btnRegistreer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), RegistreerActivity.class);
                v.getContext().startActivity(intent);

            }

        });
    }

}
