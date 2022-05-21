package com.earnfast.earnfast.cources;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.earnfast.earnfast.R;
import com.earnfast.earnfast.adapters.CourseAdapter;
import com.earnfast.earnfast.models.CourseModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class PaidCource extends AppCompatActivity implements CourseAdapter.ItemClickListenersa {
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paid_cource);

         RecyclerView recyclerView=findViewById(R.id.courseReccycler);
        progressBar=findViewById(R.id.paid_course_progress);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(PaidCource.this);
        recyclerView.setLayoutManager(linearLayoutManager);


        Query query = FirebaseFirestore.getInstance()
                .collection("PaidCourse")
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
                List<CourseModel> chats = snapshot.toObjects(CourseModel.class);
                CourseAdapter adapter = new CourseAdapter(chats, PaidCource.this);
                recyclerView.setAdapter(adapter);
                adapter.addItemClickListenersa(PaidCource.this);

            }
        });

    }

    @Override
    public void onItemClick(String courseID, String courseAmount) {
        //open razor pay form here
        progressBar.setVisibility(View.VISIBLE);
        FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance()
        .getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.getException()==null){
                   ArrayList<String> arrayList=new ArrayList<String>();


                }
            }
        });
        Intent i=new Intent(PaidCource.this, PaidCourseVideo.class);
        i.putExtra("uid",courseID);

    startActivity(i);
    }
}