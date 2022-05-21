package com.earnfast.earnfast.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.earnfast.earnfast.R;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {
    private List<SliderItems> sliderItems;
    private ViewPager2 viewPager2;
    private Context context;
    public SliderAdapter(List<SliderItems> sliderItems, ViewPager2 viewPager2,Context context) {
        this.sliderItems = sliderItems;
        this.viewPager2 = viewPager2;
        this.context=context;
    }
    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.slide_item_container, parent, false
                ) );
    }
    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        RequestOptions requestedOption=new RequestOptions();
        requestedOption=requestedOption.transform(new FitCenter(),new RoundedCorners(20));
        Glide.with(context).load(sliderItems.get(position).getImage()).apply(requestedOption).into(holder.imageView);
        if (position == sliderItems.size()- 2){
            viewPager2.post(runnable);
        }
    }
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            sliderItems.addAll(sliderItems);
            notifyDataSetChanged();
        }
    };
    @Override
    public int getItemCount() {
        return sliderItems.size();
    }
    class SliderViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageSlide);
        }

    }
}