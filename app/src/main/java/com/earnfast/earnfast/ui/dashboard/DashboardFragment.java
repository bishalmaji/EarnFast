package com.earnfast.earnfast.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.earnfast.earnfast.R;
import com.earnfast.earnfast.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView whatsapp,fb,insta,telegram,other;
        whatsapp=view.findViewById(R.id.imgwhatsapp);
        fb=view.findViewById(R.id.imgfb);
        insta=view.findViewById(R.id.imginsta);
        telegram=view.findViewById(R.id.imgtg);
        other=view.findViewById(R.id.imgmore);
        Toast.makeText(getContext(), ""+fb.getId(), Toast.LENGTH_LONG).show();
        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToApp("whatsapp");
            }
        });
        insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToApp("instagram");
            }
        });

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToApp("facebook");
            }
        });

        telegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToApp("telegram");
            }
        });

        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             sendToApp("other");
            }
        });



    }

    private void sendToApp(String appName) {
        switch (appName){
            case "whatsapp":
                break;
            case "facebook":
                break;
            case "telegram":
                break;
            case "other":
                break;
            case "instagram":
                break;
            default:

        }
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.setPackage("set the application pacage ");
        intent.putExtra(Intent.EXTRA_TEXT,"Share this app with your friends and earn up to 500 rupees per month");
        try{
            getContext().startActivity(intent);
        }catch(Exception ignored){

        }
    }
}