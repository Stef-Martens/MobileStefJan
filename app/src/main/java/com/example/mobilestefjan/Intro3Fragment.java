package com.example.mobilestefjan;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class Intro3Fragment extends Fragment {


    TextView done, back;
    ViewPager viewPager;

    public Intro3Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_intro3, container, false);

        done=view.findViewById(R.id.DerdeNext);
        back=view.findViewById(R.id.TweedeBack);
        viewPager=getActivity().findViewById(R.id.viewPager);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext() , LoginActivity.class);
                v.getContext().startActivity(myIntent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
            }
        });
        return view;    }

}
