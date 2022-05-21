package com.earnfast.earnfast.ui.home;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.earnfast.earnfast.R;
import com.earnfast.earnfast.adapters.QuizAdapter;
import com.earnfast.earnfast.cources.DailyQuiz;
import com.earnfast.earnfast.cources.DemoClasses;
import com.earnfast.earnfast.cources.FreeCource;
import com.earnfast.earnfast.cources.GameActivity;
import com.earnfast.earnfast.cources.PaidCource;
import com.earnfast.earnfast.models.QuizModel;
import com.earnfast.earnfast.utils.SliderAdapter;
import com.earnfast.earnfast.utils.SliderItems;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment{


    private ViewPager2 viewPager2;
    private final Handler sliderHandler = new Handler();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView free,demo,paid,quiz;
        free=view.findViewById(R.id.tvfree);
        demo=view.findViewById(R.id.tvdemo);
        paid=view.findViewById(R.id.tvpaid);
        quiz=view.findViewById(R.id.tvquiz);
        free.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           startActivity(new Intent(getContext(), FreeCource.class));
            }
        });
        demo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), DemoClasses.class));
            }
        });
        quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),DailyQuiz.class));
            }
        });
        paid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), PaidCource.class));
            }
        });
        //for view pager

        viewPager2 = view.findViewById(R.id.viewPagerImageSlider);

        Query query = FirebaseFirestore.getInstance()
                .collection("slider")
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

                List<SliderItems> chats = snapshot.toObjects(SliderItems.class);
                viewPager2.setAdapter(new SliderAdapter(chats,viewPager2,getContext()));

                viewPager2.setClipToPadding(false);
                viewPager2.setClipChildren(false);
                viewPager2.setOffscreenPageLimit(3);
                viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

                CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
                compositePageTransformer.addTransformer(new MarginPageTransformer(40));
                compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
                    @Override
                    public void transformPage(@NonNull View page, float position) {
                        float r = 1 - Math.abs(position);
                        page.setScaleY(0.85f + r * 0.15f);
                    }
                });

                viewPager2.setPageTransformer(compositePageTransformer);

                viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageSelected(int position) {
                        super.onPageSelected(position);
                        sliderHandler.removeCallbacks(sliderRunnable);
                        sliderHandler.postDelayed(sliderRunnable, 2000); // slide duration 2 seconds
                    }
                });
            }
        });




    }

    private final Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };

}