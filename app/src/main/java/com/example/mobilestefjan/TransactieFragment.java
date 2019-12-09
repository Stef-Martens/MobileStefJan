package com.example.mobilestefjan;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class TransactieFragment extends Fragment {

    ImageView imageView;
    Button btnFotoNemen;
    Button btnKalender;
    EditText etOmschrijving;
    EditText etBedrag;
    EditText etDatum;
    Button btnAnnuleren;
    Button btnOpslaan;
    DatabankTransacties myDb;
    Bitmap bitmap;
    ByteArrayOutputStream bos;
    byte[] bArray;
    RadioGroup radioGroup;
    RadioButton radioButtonGekozen;
    RadioButton r1;
    RadioButton r2;
    private int mYear, mMonth, mDay;
    String datum;
    DatabankVoorJezelf myDbJezelf;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_transactie, container, false);

        myDb=new DatabankTransacties(getActivity());
        myDbJezelf=new DatabankVoorJezelf(getActivity());

        imageView=view.findViewById(R.id.imgTicket);
        btnFotoNemen=view.findViewById(R.id.btnFotoNemen);
        etOmschrijving=view.findViewById(R.id.txtOmschrijving);
        etBedrag=view.findViewById(R.id.txtBedrag);
        etDatum=view.findViewById(R.id.txtDatum);
        btnAnnuleren=view.findViewById(R.id.btnAnnuleren);
        btnOpslaan=view.findViewById(R.id.btnOpslaan);
        radioGroup=view.findViewById(R.id.radioTransactie);
        btnKalender=view.findViewById(R.id.btnKalender);
        r1=view.findViewById(R.id.radiobetalen);
        r2=view.findViewById(R.id.radioontvangen);


        CameraOpenen();
        Annuleren();
        Opslaan();
        Kalender();

        return view;
    }


    public  void CameraOpenen() {
        btnFotoNemen.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent,0);
                    }
                }
        );
    }



    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        bitmap = (Bitmap)data.getExtras().get("data");
        imageView.setImageBitmap(bitmap);
        bos= new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        bArray= bos.toByteArray();
    }

    public  void Annuleren() {
        btnAnnuleren.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        replaceFragment(new HomeFragment());
                    }
                }
        );
    }

    public RadioButton checkRadio(View view){
        int radioid=radioGroup.getCheckedRadioButtonId();
        return radioButtonGekozen=view.findViewById(radioid);
    }

    public  void Opslaan() {
        btnOpslaan.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String bedrag= etBedrag.getText().toString();
                        radioButtonGekozen=checkRadio(getView());
                        Cursor c=myDbJezelf.getAllDataCursor();
                        c.moveToPosition(0);
                        if(radioButtonGekozen.getId()==r2.getId()){
                            myDb.insertData("+ "+etBedrag.getText().toString(),datum,etOmschrijving.getText().toString(),bArray);
                            int Saldo= Integer.valueOf(c.getString(2))+Integer.valueOf(bedrag);
                            myDbJezelf.updateData(c.getString(0),c.getString(1),String.valueOf(Saldo),c.getString(3));
                        }
                        else{
                            myDb.insertData("- "+etBedrag.getText().toString(),datum,etOmschrijving.getText().toString(),bArray);
                            int Saldo= Integer.valueOf(c.getString(2))-Integer.valueOf(bedrag);
                            myDbJezelf.updateData(c.getString(0),c.getString(1),String.valueOf(Saldo),c.getString(3));
                        }
                        replaceFragment(new HomeFragment());
                    }
                }
        );
    }

    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void Kalender(){
        btnKalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                datum=dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;

                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog.show();

            }
        });
    }

    }
