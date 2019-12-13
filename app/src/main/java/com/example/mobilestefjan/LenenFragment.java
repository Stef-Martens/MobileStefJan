package com.example.mobilestefjan;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.util.Calendar;
import java.util.List;

public class LenenFragment extends Fragment {


    DatabankVrienden myDb;
    DatabankVoorJezelf mydbVoorJezelf;
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
    Button btnKalender;
    private int mYear, mMonth, mDay;
    String Datum;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_lenen, container, false);

        myDb=new DatabankVrienden(getActivity());
        mydbVoorJezelf=new DatabankVoorJezelf(getActivity());


        etOmschrijving=view.findViewById(R.id.txtOmschrijving);
        etBedrag=view.findViewById(R.id.txtBedrag);
        btnKalender=view.findViewById(R.id.btnKalenderLenen);
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

        Kalender();
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

                if(TextUtils.isEmpty(etOmschrijving.getText().toString())){
                    etOmschrijving.setError("Mag niet leeg zijn");
                }
                else if(TextUtils.isEmpty(etBedrag.getText().toString())){
                    etBedrag.setError("Mag niet leeg zijn");
                }
                else  if(TextUtils.isEmpty(Datum)){
                    btnKalender.setError("Mag niet leeg zijn");
                }
                else {

                    try {
                        String voornaam = ddlvrienden.getSelectedItem().toString();
                        int id = ddlvrienden.getSelectedItemPosition();
                        Cursor res = myDb.KrijgVriend(voornaam);
                        //res.moveToPosition(id);
                        String achternaam = res.getString(1);
                        int bedrag = Integer.parseInt(etBedrag.getText().toString());
                        int vorigBedrag = Integer.parseInt(res.getString(3));
                        radioButtonGekozen = checkRadio(getView());
                        int nieuwBedrag = 0;
                        String textRadio = radioButtonGekozen.getText().toString();
                        Toast.makeText(getActivity(), textRadio, Toast.LENGTH_SHORT).show();
                        Cursor c = mydbVoorJezelf.getAllDataCursor();
                        c.moveToPosition(0);
                        if (radioButtonGekozen.getId() == r1.getId()) {
                            nieuwBedrag = vorigBedrag - bedrag;

                            int Saldo = Integer.valueOf(c.getString(2)) + bedrag;
                            mydbVoorJezelf.updateData(c.getString(0), c.getString(1), String.valueOf(Saldo), c.getString(3));
                        } else {
                            nieuwBedrag = vorigBedrag + bedrag;

                            int Saldo = Integer.valueOf(c.getString(2)) - bedrag;
                            mydbVoorJezelf.updateData(c.getString(0), c.getString(1), String.valueOf(Saldo), c.getString(3));
                        }

                        String nieuwGeld = String.valueOf(nieuwBedrag);

                        Boolean update = myDb.updateData(id, achternaam, voornaam, nieuwGeld);

                        replaceFragment(new HomeFragment());
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Voeg eerst een vriend toe", Toast.LENGTH_SHORT).show();
                    }

                }



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

                                Datum=dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;

                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog.show();

            }
        });
    }



}
