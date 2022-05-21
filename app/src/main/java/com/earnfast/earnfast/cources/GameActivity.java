package com.earnfast.earnfast.cources;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.earnfast.earnfast.R;
import com.earnfast.earnfast.adapters.QuestionAdapter;
import com.earnfast.earnfast.models.QuestionModel;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;
import java.util.List;

public class GameActivity extends AppCompatActivity implements QuestionAdapter.ItemClickListener {

    RecyclerView recyclerView;
    public static int  position;
    public  static int size;
    QuestionAdapter adapter;
    public static boolean stat=false;
    public static boolean ans=false;
    public static int progress;
    String quizId;
    private TextView questionNo;
    private CircularProgressIndicator progressIndicator;
    public static int correct=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView=getWindow().getDecorView();
        int uiop=View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        decorView.setSystemUiVisibility(uiop);
        setContentView(R.layout.activity_game);
        //init
        position=0;
        size=0;progress=10;
        questionNo=findViewById(R.id.questionN);
        progressIndicator=findViewById(R.id.gameProg);
        recyclerView=findViewById(R.id.recyclerView);
        Button nextButton = findViewById(R.id.nextBtn);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stat){
                    questionNo.setText(String.valueOf(position+1));
                    progressIndicator.setProgress(position*10);
                    if(ans){
                        correct++;
                    }
                    if(position<size-1){
                        recyclerView.getLayoutManager().scrollToPosition(position+1);
                    }else {
                     showCustDialog();
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
                TextView correctT,wrongT,totalT;
                correctT=dialogView.findViewById(R.id.correctq);
                wrongT=dialogView.findViewById(R.id.wrongq);
                totalT=dialogView.findViewById(R.id.totalq);
                if(correct>9){
                    correctT.setText(String.valueOf(correct));
                    wrongT.setText("0"+(size-correct));
                }else{
                    correctT.setText("0"+correct);
                    if((size-correct)>9){
                        wrongT.setText(String.valueOf(size-correct));
                    }else{
                        wrongT.setText("0"+(size-correct));
                    }
                }totalT.setText(String.valueOf(size));

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
        //change from here
        
        quizId=getIntent().getStringExtra("quizId");

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
    public void onItemClick(int p,boolean s,boolean tf) {
        position=p;
        stat=s;
         ans=tf;

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