package com.earnfast.earnfast;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ContentMain extends AppCompatActivity {


    private String name,refer,phone,coin1,coin2,total;
    private boolean stat;
    private ArrayList<String> gamePlayed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_main);
        gamePlayed=new ArrayList<String>();
        BottomNavigationView navView = findViewById(R.id.nav_view);
        Toolbar toolbar=findViewById(R.id.toolbar);
//        SimPleRatting
        TextView payment=findViewById(R.id.paymenttv);
        FirebaseFirestore.getInstance().collection("user").document(FirebaseAuth.getInstance().getCurrentUser()
        .getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.getResult()!=null){
               name=task.getResult().getString("name");
               total=task.getResult().getString("total");
               coin1=task.getResult().getString("coin1");
               coin2=task.getResult().getString("coin2");
               refer=task.getResult().getString("refer");
               phone=task.getResult().getString("phone");
               stat=task.getResult().getBoolean("stat");

               payment.setText(total);
                    SharedPreferences sp=getSharedPreferences("user", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sp.edit();
                    editor.putString("name",name);
                    editor.putString("refer",refer);
                    editor.putString("phone",phone);
                    editor.putString("total",total);
                    editor.putString("coin1",coin1);
                    editor.putString("coin2",coin2);
                    editor.putBoolean("stat",stat);
                    editor.apply();
            }
            }
        });

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ContentMain.this,Payment.class));
            }
        });

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_content_main);
        NavigationUI.setupWithNavController(navView, navController);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if(destination.getId()==R.id.navigation_home){
                    toolbar.setLogo(R.drawable.ic_home_black_24dp2);
                    toolbar.setTitle(getResources().getString(R.string.title_home));

                }else if(destination.getId()==R.id.navigation_dashboard){
                    toolbar.setLogo(R.drawable.ic_baseline_mobile_screen_share_242);
                    toolbar.setTitle(getResources().getString(R.string.title_dashboard));
                }else if(destination.getId()==R.id.navigation_notifications){
                    toolbar.setLogo(R.drawable.ic_baseline_format_list_bulleted_242);
                    toolbar.setTitle(getResources().getString(R.string.title_notifications));
                }else{

                }
            }
        });
    }

}