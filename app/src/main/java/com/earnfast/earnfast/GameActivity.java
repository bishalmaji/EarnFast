package com.earnfast.earnfast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.earnfast.earnfast.adapters.QuestionAdapter;
import com.earnfast.earnfast.models.QuestionModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GameActivity extends AppCompatActivity implements QuestionAdapter.ItemClickListener {

    RecyclerView recyclerView;
    public static int  position=0;
    public  static int size=0;
    QuestionAdapter adapter;
    public static boolean stat=false;
    String quizId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView=getWindow().getDecorView();
        int uiop=View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        decorView.setSystemUiVisibility(uiop);
        setContentView(R.layout.activity_game);

        recyclerView=findViewById(R.id.recyclerView);
        Button nextButton = findViewById(R.id.nextBtn);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stat){
                    if(position<size-1){
                        recyclerView.getLayoutManager().scrollToPosition(position+1);
                    }else {
                        String price=getIntent().getStringExtra("price");
                        SharedPreferences sp=getSharedPreferences("user", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor=sp.edit();
                        String coin2=sp.getString("coin2","0");
                        int newCoin2=Integer.parseInt(price)+Integer.parseInt(coin2);
                        if(newCoin2<45){
                            showCustDialog();
                            editor.putString("coin2",String.valueOf(newCoin2)).apply();
                            FirebaseFirestore.getInstance().collection("user").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .update("coin2",String.valueOf(newCoin2),"quizSolved",Arrays.asList(quizId));

                        }else{

                            FirebaseFirestore.getInstance().collection("user").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .update("stat",false,"quizSolved",Arrays.asList(quizId));
                            editor.putBoolean("stat",false).apply();

                            finish();
                        }
                    }
                    stat=false;
                }else
                {
                    Toast.makeText(GameActivity.this, "Chose one Option", Toast.LENGTH_SHORT).show();

                }



            }

            private void showCustDialog() {
                AlertDialog.Builder builder=new AlertDialog.Builder(GameActivity.this);
                ViewGroup viewGroup=findViewById(android.R.id.content);
                View dialogView=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.game_finish_dialog,viewGroup,false);

                builder.setView(dialogView).setCancelable(false);

                builder.create().show();
                Button btn=dialogView.findViewById(R.id.finishgamebtn);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        GameActivity.super.onBackPressed();
                    }
                });
            }
        });
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(GameActivity.this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerView.setLayoutManager(linearLayoutManager);

        quizId=getIntent().getStringExtra("quizId");
        Toast.makeText(GameActivity.this, quizId, Toast.LENGTH_SHORT).show();

        Query query = FirebaseFirestore.getInstance()
                .collection(quizId)
                .limit(50);

        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    // Handle error
                    //...
                    return;
                }

                // Convert query snapshot to a list of chats
                List<QuestionModel> chats = snapshot.toObjects(QuestionModel.class);
                adapter=new QuestionAdapter(chats,GameActivity.this);
                recyclerView.setAdapter(adapter);
                adapter.addItemClickListener(GameActivity.this);
                // Update UI
                // ...
                size=snapshot.getDocuments().size();
            }
        });

    }

    @Override
    public void onItemClick(int p,boolean s) {
        position=p;
        stat=s;

    }

    public class CustomGridLayoutManager extends LinearLayoutManager {
        private boolean isScrollEnabled = true;

        public CustomGridLayoutManager(Context context) {
            super(context);
        }

        public void setScrollEnabled(boolean flag) {
            this.isScrollEnabled = flag;
        }

        @Override
        public boolean canScrollVertically() {
            //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
            return isScrollEnabled && super.canScrollVertically();
        }
    }

}