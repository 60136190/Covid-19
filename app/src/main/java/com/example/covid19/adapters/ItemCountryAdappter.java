package com.example.covid19.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.covid19.R;
import com.example.covid19.activities.DetailActivity;
import com.example.covid19.model.CountryData;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemCountryAdappter extends RecyclerView.Adapter<ItemCountryAdappter.ItemCountryViewHolder> {

    private Context mContext;
    List<CountryData> mcountryData;


    public ItemCountryAdappter(Context mContext, List<CountryData> mcountryData) {
        this.mContext = mContext;
        this.mcountryData = mcountryData;
    }

    @NonNull
    @Override
    public ItemCountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_country, parent, false);
        return new ItemCountryViewHolder(view);
    }

    public void filterList(List<CountryData> filteredList) {
        mcountryData = filteredList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ItemCountryViewHolder holder, int position) {
        CountryData countryData = mcountryData.get(position);
        holder.itemname.setText(countryData.getCountry());

        //formatNumber
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        holder.itemCase.setText(formatter.format(countryData.getCases()));


        Map<String, String> img = countryData.getCountryInfo();
        Glide.with(mContext).load(img.get("flag")).into(holder.imgFlag);
        Map<String, String> id = countryData.getCountryInfo();
        String idCountry = id.get("_id");

        holder.imgShowDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra("someKey", idCountry);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (mcountryData != null) {
            return mcountryData.size();
        }
        return 0;
    }

    public class ItemCountryViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgFlag;
        private ImageView imgShowDetail;
        private TextView itemname;
        private TextView itemCase;
        private ProgressBar progressBar;

        public ItemCountryViewHolder(View itemView) {
            super(itemView);
            imgFlag = itemView.findViewById(R.id.img_flag);
            imgShowDetail = itemView.findViewById(R.id.img_show_detal);
            itemname = itemView.findViewById(R.id.tv_name_of_country);
            itemCase = itemView.findViewById(R.id.tv_number_of_case);

        }
    }

}
