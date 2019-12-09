package com.example.mobilestefjan;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class HomeFragment extends Fragment {

    CardView btnBetaling, btnOntvangen, btnLenen, btnUitlenen;
    DatabankVoorJezelf db;
    TextView naam, saldo;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_home, container, false);

        btnBetaling=view.findViewById(R.id.btnBetalen);
        btnOntvangen=view.findViewById(R.id.btnOntvangen);
        btnLenen=view.findViewById(R.id.btnLenen);
        btnUitlenen=view.findViewById(R.id.btnUitlenen);
        db=new DatabankVoorJezelf(getActivity());
        naam=view.findViewById(R.id.txtNaamEnAchternaam);
        saldo=view.findViewById(R.id.txtSaldo);
        Cursor c=db.getAllDataCursor();
        c.moveToPosition(0);
        String VolNaam=c.getString(0).toString()+" "+c.getString(1);

        naam.setText(VolNaam);
        int saldoInInt=Integer.valueOf(c.getString(2));
        if(saldoInInt>=0){
            saldo.setText("+"+c.getString(2)+" EUR");
        }


        VeranderenNaarTransactie();
        VeranderNaarLenen();

        return view;
    }

    public void VeranderenNaarTransactie(){
        btnBetaling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new TransactieFragment());
            }
        });
        btnOntvangen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new TransactieFragment());
            }
        });
    }

    public void VeranderNaarLenen(){
        btnLenen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new LenenFragment());
            }
        });
        btnUitlenen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new LenenFragment());
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
