package com.earnfast.earnfast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.earnfast.earnfast.adapters.VpPayAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class Payment extends AppCompatActivity implements TabLayoutMediator.TabConfigurationStrategy {
    TextView total,reward,cashback,toolbarTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        TabLayout tabLayout=findViewById(R.id.tablayoutpayment);
        ViewPager2 viewPager2=findViewById(R.id.vppayment);
        total=findViewById(R.id.total);
        reward=findViewById(R.id.reward);
        cashback=findViewById(R.id.cashback);
        toolbarTotal=findViewById(R.id.totaltoolbar);
        SharedPreferences sp=getSharedPreferences("user", Context.MODE_PRIVATE);

        total.setText(sp.getString("total"," "));
        reward.setText(sp.getString("coin1"," "));
        cashback.setText(sp.getString("coin2"," "));
        toolbarTotal.setText(sp.getString("total",""));



        VpPayAdapter vpPayAdapter=new VpPayAdapter(getSupportFragmentManager(),getLifecycle());
        viewPager2.setAdapter(vpPayAdapter);
        new  TabLayoutMediator(tabLayout,viewPager2,this).attach();
    }

    @Override
    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
        if (position==0)tab.setText("Payment Request");
        else tab.setText("Transactions");
    }
}