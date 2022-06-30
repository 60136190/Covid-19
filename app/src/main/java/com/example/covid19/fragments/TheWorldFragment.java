package com.example.covid19.fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.example.covid19.R;
import com.example.covid19.adapters.ItemCountryAdappter;
import com.example.covid19.api.ApiClient;
import com.example.covid19.model.CountryData;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;
import com.github.ybq.android.spinkit.style.FoldingCube;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TheWorldFragment extends Fragment {

    // search bar
    EditText edtSearch;
    CharSequence search = "";

    Button btnBackToTop;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    List<CountryData> list;
    ItemCountryAdappter itemCountryAdappter;
    View view;

    public TheWorldFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_the_world, container, false);
        initUi();
        setProgressBar();
        // add all country in recycle view
        list = new ArrayList<>();


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        itemCountryAdappter = new ItemCountryAdappter(getContext(), list);
        recyclerView.setAdapter(itemCountryAdappter);

        ApiClient.getApiCovid19().getAllCountry().enqueue(new Callback<List<CountryData>>() {
            @Override
            public void onResponse(Call<List<CountryData>> call, Response<List<CountryData>> response) {

                list.addAll(response.body());
                itemCountryAdappter.notifyDataSetChanged();
                if (response.isSuccessful()){
                    progressBar.setVisibility(View.INVISIBLE);
                    // back to top
                    btnBackToTop.setVisibility(View.VISIBLE);
                    btnBackToTop.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            recyclerView.smoothScrollToPosition(0);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<CountryData>> call, Throwable t) {
                t.printStackTrace();
            }
        });


        // filter
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        return view;

    }

    private void initUi() {
        recyclerView = view.findViewById(R.id.rcv_the_world);
        edtSearch = view.findViewById(R.id.edt_search);
        progressBar = view.findViewById(R.id.spin_kit);
        btnBackToTop = view.findViewById(R.id.btn_back_to_top);
    }

    public void setProgressBar() {
        // progress bar
        Circle circle = new Circle();
        progressBar.setIndeterminateDrawable(circle);
        progressBar.setVisibility(View.VISIBLE);
    }

    // filter products
    private void filter(String text) {
        List<CountryData> filteredList = new ArrayList<>();
        for (CountryData item : list) {
            if (item.getCountry().toUpperCase().contains(text.toUpperCase())) {
                filteredList.add(item);
            }
        }
        itemCountryAdappter.filterList(filteredList);
    }

}