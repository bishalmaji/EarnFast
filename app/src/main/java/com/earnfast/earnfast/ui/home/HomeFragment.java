package com.earnfast.earnfast.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.earnfast.earnfast.GameActivity;
import com.earnfast.earnfast.R;
import com.earnfast.earnfast.adapters.SurveyAdapter;
import com.earnfast.earnfast.models.SurveyModel;

import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
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
import java.util.Objects;

public class HomeFragment extends Fragment implements SurveyAdapter.ItemClickListenersa{


    private RewardedAd mRewardedAd;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MobileAds.initialize(requireContext());
        AdLoader adLoader = new AdLoader.Builder(requireContext(), "ca-app-pub-3940256099942544/2247696110")
                .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                    @Override
                    public void onNativeAdLoaded(NativeAd nativeAd) {
                        NativeTemplateStyle styles = new
                                NativeTemplateStyle.Builder().build();
                        TemplateView template =view.findViewById(R.id.my_template);
                        template.setStyles(styles);
                        template.setNativeAd(nativeAd);
                    }
                }).build();
// this view is for the native libray
        adLoader.loadAd(new AdRequest.Builder().build());

        RecyclerView recyclerView=view.findViewById(R.id.surveyRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        SharedPreferences sp= requireContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        boolean stat= sp.getBoolean("stat",true);

        Query query = FirebaseFirestore.getInstance()
                .collection("quiz")
                .limit(50);
if (stat){
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    // Handle error
                    //...
                    return;
                }

                List<SurveyModel> chats = snapshot.toObjects(SurveyModel.class);
                SurveyAdapter adapter=new SurveyAdapter(chats,getContext());
                recyclerView.setAdapter(adapter);


            }
        });
}
        mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
            @Override
            public void onAdShowedFullScreenContent() {
                // Called when ad is shown.
//                Log.d(TAG, "Ad was shown.");
            }

            @Override
            public void onAdFailedToShowFullScreenContent(AdError adError) {
                // Called when ad fails to show.
//                Log.d(TAG, "Ad failed to show.");
            }

            @Override
            public void onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                // Set the ad reference to null so you don't show the ad a second time.
//                Log.d(TAG, "Ad was dismissed.");
                mRewardedAd = null;
            }
        });
    }


    @Override
    public void onItemClick(String qid, String amnt) {

        AdRequest adRequest = new AdRequest.Builder().build();

        RewardedAd.load(getContext(),
                "ca-app-pub-3940256099942544/5224354917",
                adRequest,
                new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.

                        mRewardedAd = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                        if (mRewardedAd != null) {
                            Activity activityContext = getActivity();
                            mRewardedAd.show(getActivity(), new OnUserEarnedRewardListener() {
                                @Override
                                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                    // Handle the reward.
//                                    Log.d(TAG, "The user earned the reward.");
                                    int rewardAmount = rewardItem.getAmount();
                                    String rewardType = rewardItem.getType();



                                    Intent intent=new Intent(getContext(), GameActivity.class);
                                    intent.putExtra("quizId",qid);

                                    FirebaseFirestore.getInstance().collection("user").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.getResult()!=null){
                                                ArrayList<String> arrayList= (ArrayList<String>) task.getResult().get("quizSolved");
                                                if (arrayList.contains(qid)){
                                                    Toast.makeText(getContext(), "Already done, you won't get any reward for this", Toast.LENGTH_SHORT).show();
                                                    intent.putExtra("price","0");

                                                }else {
                                                    intent.putExtra("price",amnt);
                                                }
                                                startActivity(intent);
                                            }

                                        }
                                    });

                                }
                            });
                        } else {
//                            Log.d(TAG, "The rewarded ad wasn't ready yet.");
                        }
                    }
                });



    }
}