package com.earnfast.earnfast.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.earnfast.earnfast.Payment;
import com.earnfast.earnfast.R;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Objects;


public class PayRequest extends Fragment {


    public PayRequest() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pay_request, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView reqReward,reqCash;
        Button rewardBtn,cashBtn;
        reqReward=view.findViewById(R.id.rewardreq);
        reqCash=view.findViewById(R.id.cashbackreq);
        rewardBtn=view.findViewById(R.id.rewardreqbtn);
        cashBtn=view.findViewById(R.id.cashreqbtn);
        SharedPreferences sp=requireContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        reqCash.setText(sp.getString("coin1"," "));
        reqReward.setText(sp.getString("coin2"," "));
        rewardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "You need minimum rs 200 to withdraw", Toast.LENGTH_SHORT).show();
            }
        });
        cashBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "You need minimum rs 50 to withdraw", Toast.LENGTH_SHORT).show();

            }
        });


    }
}