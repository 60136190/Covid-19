package com.example.covid19.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.covid19.R;
import com.example.covid19.model.Step;
import java.util.List;

public class ItemStepAdapter extends RecyclerView.Adapter<ItemStepAdapter.ItemStepViewHolder>{

    private Context mContext;
    List<Step> mStep;

    public ItemStepAdapter(Context mContext, List<Step> mStep) {
        this.mContext = mContext;
        this.mStep = mStep;
    }

    @NonNull
    @Override
    public ItemStepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_step, parent, false);
        return new ItemStepAdapter.ItemStepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemStepViewHolder holder, int position) {
        Step step = mStep.get(position);

        holder.imgStep.setImageResource(step.getImgStep());
        holder.tvTitle.setText(step.getTvTitle());
    }

    @Override
    public int getItemCount() {
       if (mStep != null){
           return mStep.size();
       }
       return 0;
    }

    public class ItemStepViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgStep;
        private TextView tvTitle;
        public ItemStepViewHolder(@NonNull View itemView) {
            super(itemView);
            imgStep = itemView.findViewById(R.id.img_step);
            tvTitle = itemView.findViewById(R.id.tv_title_step);
        }
    }
}
