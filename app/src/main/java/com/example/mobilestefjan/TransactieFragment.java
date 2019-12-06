package com.example.mobilestefjan;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.io.ByteArrayOutputStream;

public class TransactieFragment extends Fragment {

    ImageView imageView;
    Button btnFotoNemen;
    EditText etOmschrijving;
    EditText etBedrag;
    EditText etDatum;
    Button btnAnnuleren;
    Button btnOpslaan;
    DatabankTransacties myDb;
    Bitmap bitmap;
    ByteArrayOutputStream bos;
    byte[] bArray;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_transactie, container, false);

        myDb=new DatabankTransacties(getActivity());


        imageView=view.findViewById(R.id.imgTicket);
        btnFotoNemen=view.findViewById(R.id.btnFotoNemen);
        etOmschrijving=view.findViewById(R.id.txtOmschrijving);
        etBedrag=view.findViewById(R.id.txtBedrag);
        etDatum=view.findViewById(R.id.txtDatum);
        btnAnnuleren=view.findViewById(R.id.btnAnnuleren);
        btnOpslaan=view.findViewById(R.id.btnOpslaan);


        CameraOpenen();
        Annuleren();
        Opslaan();

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

    public  void Opslaan() {
        btnOpslaan.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String bedrag= etBedrag.getText().toString();
                        myDb.insertData(Integer.parseInt(bedrag),etDatum.getText().toString(),etOmschrijving.getText().toString(),bArray);
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

    }
