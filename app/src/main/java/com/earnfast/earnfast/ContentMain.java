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


    private String name;
    private String refer;
    private String phone;
    private String total;
    private boolean stat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        Toolbar toolbar=findViewById(R.id.toolbar);
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
                    toolbar.setLogo(R.drawable.ic_baseline_notifications_24);
                    toolbar.setTitle(getResources().getString(R.string.title_notifications));
                }else if(destination.getId()==R.id.navigation_more){
                    toolbar.setLogo(R.drawable.ic_baseline_format_list_bulleted_24);
                    toolbar.setTitle(getResources().getString(R.string.title_more));
                }
            }
        });
    }

}