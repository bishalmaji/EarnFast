package com.earnfast.earnfast.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.earnfast.earnfast.GameActivity;
import com.earnfast.earnfast.R;
import com.earnfast.earnfast.models.SurveyModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class SurveyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<SurveyModel> itemList;
    private Context context;

    private ItemClickListenersa itemClickListenersa;
    // Constructor of the class
    public SurveyAdapter(List<SurveyModel> itemList,Context context) {
        this.itemList = itemList;
        this.context=context;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.survey_layout, parent, false);
            return new SurveyAdapter.ViewHolderOne(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        initLayoutOne((SurveyAdapter.ViewHolderOne)holder, position);
        String quizId=itemList.get(position).getUid();
        ((ViewHolderOne) holder).getItBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClickListenersa!=null)
                    itemClickListenersa.onItemClick(quizId,itemList.get(position).getRupees());


            }
        });

    }


    private void initLayoutOne(SurveyAdapter.ViewHolderOne holder, int listPosition) {
holder.name.setText(itemList.get(listPosition).getName());
holder.rupee.setText(itemList.get(listPosition).getRupees());
Glide.with(context).load(itemList.get(listPosition).getIcon()).into(holder.icon);
        Glide.with(context).load(itemList.get(listPosition).getBackground()).into(holder.background);
        holder.ratingBar.setRating(Float.parseFloat(itemList.get(listPosition).getRatting()));
    }


    // Static inner class to initialize the views of rows
    static class ViewHolderOne extends RecyclerView.ViewHolder {
         TextView name,rupee;
        ImageView background,icon;
        RatingBar ratingBar;

        Button getItBtn;
        public ViewHolderOne(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.surveyname);
            rupee=itemView.findViewById(R.id.rupeetv);
            background=itemView.findViewById(R.id.backgroundimg);
            icon=itemView.findViewById(R.id.icon);
            getItBtn=itemView.findViewById(R.id.getitbutton);
            ratingBar=itemView.findViewById(R.id.rattingbar);


        }
    }

    // get the size of the list
    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }
    public void addItemClickListenersa(ItemClickListenersa listener){
        itemClickListenersa=listener;
    }
    public interface ItemClickListenersa  {
        void onItemClick(String qid,String amnt);
    }
}
