package com.example.covid19.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.covid19.R;
import com.example.covid19.activities.MapsActivity;
import com.example.covid19.adapters.ItemStepAdapter;
import com.example.covid19.model.Step;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView rcvHotThisMonth;
    private List<Step> listStep;
    private ConstraintLayout ctHospital;
    private ConstraintLayout ctMedicine;
    private ConstraintLayout ctConsultency;
    private ConstraintLayout ctDiagonist;
    private ImageView imgUser;
    private View view;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_home, container, false);
        initUi();

        ItemStepAdapter hotThisMonthAdapter = new ItemStepAdapter(getContext(), listStep);
        rcvHotThisMonth.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rcvHotThisMonth.setAdapter(hotThisMonthAdapter);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
        if (acct != null) {
            Uri personPhoto = acct.getPhotoUrl();
            Glide.with(getContext()).load(personPhoto).into(imgUser);
        }

        ctHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MapsActivity.class);
                startActivity(intent);
            }
        });
        ctMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MapsActivity.class);
                startActivity(intent);
            }
        });
        ctConsultency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MapsActivity.class);
                startActivity(intent);
            }
        });
        ctDiagonist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MapsActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void initUi() {
        rcvHotThisMonth = view.findViewById(R.id.rcv_steps);
        ctHospital = view.findViewById(R.id.ct_hospital);
        ctMedicine = view.findViewById(R.id.ct_medicine);
        ctConsultency = view.findViewById(R.id.ct_consultency);
        ctDiagonist = view.findViewById(R.id.ct_diagonist);
        imgUser = view.findViewById(R.id.img_user);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listStep = new ArrayList<>();
        listStep.add(new Step(R.drawable.slidea,"Wash your hand"));
        listStep.add(new Step(R.drawable.slideb,"Wear your mask"));
        listStep.add(new Step(R.drawable.slidec,"Eat healthy food"));
        listStep.add(new Step(R.drawable.slided,"Do exercise"));
        listStep.add(new Step(R.drawable.cocktail,"Spider man and superman"));
    }
}