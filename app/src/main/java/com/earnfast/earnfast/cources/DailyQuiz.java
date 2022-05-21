package com.earnfast.earnfast.cources;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.earnfast.earnfast.R;

import com.earnfast.earnfast.adapters.QuizAdapter;

import com.earnfast.earnfast.models.QuizModel;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.List;

public class DailyQuiz extends AppCompatActivity implements QuizAdapter.QuizClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_quiz);

        RecyclerView recyclerView=findViewById(R.id.quizRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        Query query = FirebaseFirestore.getInstance()
                .collection("quiz")
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

                List<QuizModel> chats = snapshot.toObjects(QuizModel.class);
                QuizAdapter adapter=new QuizAdapter(chats,DailyQuiz.this);

                recyclerView.setAdapter(adapter);
                adapter.addQuizClickListener(DailyQuiz.this);
                //this adapter will work in th

            }
        });
}



    @Override
    public void onItemClick(String qid, String quizName) {
        Toast.makeText(DailyQuiz.this, "on item click", Toast.LENGTH_SHORT).show();
        Intent i=new Intent(DailyQuiz.this,GameActivity.class);
        i.putExtra("quizId",qid);
        startActivity(i);
    }
}