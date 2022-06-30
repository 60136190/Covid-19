package com.example.covid19.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid19.R;
import com.example.covid19.activities.DetailLineChartActivity;
import com.example.covid19.api.ApiClient;
import com.example.covid19.model.CountryData;
import com.example.covid19.model.Step;
import com.example.covid19.utils.Contants;
import com.github.ybq.android.spinkit.style.Circle;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.PieModel;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VietNamFragment extends Fragment {
    private View view;
    private PieChart mPieChart;
    private ValueLineChart mCubicValueLineChart;
    private ValueLineSeries series;
    private TextView tvDate;
    private TextView tvTotalCases;
    private TextView tvTotalDeaths;
    private TextView tvTotalRecovered;
    private TextView tvTotalActive;
    private TextView tvTotalCritical;
    private TextView tvTotalTest;
    private TextView tvTodayCases;
    private TextView tvTodayDeaths;
    private TextView tvTodayRecovered;
    private TextView tvPopulation;
    private TextView tvShowDetailLineChart;
    ProgressBar progressBar;


    public VietNamFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_viet_nam, container, false);
        initUi();
        setProgressBar();
        getCurrentDay();
        getDataVietNam();

        tvShowDetailLineChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetailLineChartActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void initUi() {
        mPieChart = view.findViewById(R.id.chart);
        mCubicValueLineChart= view.findViewById(R.id.chart_line);
        tvDate = view.findViewById(R.id.tv_date);
        tvTodayCases = view.findViewById(R.id.tv_today_cases);
        tvTodayDeaths = view.findViewById(R.id.tv_today_deaths);
        tvTodayRecovered = view.findViewById(R.id.tv_today_recovered);

        tvTotalCases = view.findViewById(R.id.tv_total_cases);
        tvTotalDeaths = view.findViewById(R.id.tv_total_deaths);
        tvTotalRecovered = view.findViewById(R.id.tv_total_recovered);
        tvTotalActive = view.findViewById(R.id.tv_active);
        tvTotalCritical = view.findViewById(R.id.tv_total_critical);
        tvTotalTest = view.findViewById(R.id.tv_test);

        tvPopulation = view.findViewById(R.id.tv_population);
        tvShowDetailLineChart = view.findViewById(R.id.tv_show_detail_lineChart);
        progressBar =view.findViewById(R.id.spin_kit);


    }

    private void getCurrentDay(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        tvDate.setText(formatter.format(date));
    }


    private void getDataVietNam() {
        Call<CountryData> responseDTOCall = ApiClient.getApiCovid19().getDataVietNam();
        responseDTOCall.enqueue(new Callback<CountryData>() {
            @Override
            public void onResponse(Call<CountryData> call, Response<CountryData> response) {
                if (response.isSuccessful()){
                    progressBar.setVisibility(View.INVISIBLE);
                }

                Integer death = response.body().getDeaths();
                Integer recovered = response.body().getRecovered();
                Integer active = response.body().getActive();
                Integer critical = response.body().getCritical();
                Integer cases = response.body().getCases();
                Integer test = response.body().getTests();


                //formatNumber
                DecimalFormat formatter = new DecimalFormat("#,###,###");

                Integer todayCase = response.body().getTodayCases();
                Integer todayDeaths = response.body().getTodayDeaths();
                Integer todayRecovered = response.body().getTodayRecovered();
                Integer population = response.body().getPopulation();
                Integer oneCasePerPeople = response.body().getOneCasePerPeople();
                Integer oneDeathPerPeople = response.body().getOneDeathPerPeople();
                Integer oneTestPerPeople = response.body().getOneTestPerPeople();
                Double activePerOneMillion = response.body().getActivePerOneMillion();
                Double recoveredPerOneMillion = response.body().getRecoveredPerOneMillion();
                Double criticalPerOneMillion = response.body().getCriticalPerOneMillion();


                mPieChart.addPieSlice(new PieModel("Deaths",
                        (float) Double.parseDouble(String.valueOf(death))
                        ,getResources().getColor(R.color.live_update)));

                mPieChart.addPieSlice(new PieModel("Recovered",
                        (float) Double.parseDouble(String.valueOf(recovered))
                        ,getResources().getColor(R.color.recovered)));

                mPieChart.addPieSlice(new PieModel("Active",
                        (float) Double.parseDouble(String.valueOf(active))
                        ,getResources().getColor(R.color.active)));

                mPieChart.addPieSlice(new PieModel("Critical",
                        (float) Double.parseDouble(String.valueOf(critical))
                        ,getResources().getColor(R.color.critical)));
                mPieChart.startAnimation();

                tvTotalCases.setText(formatter.format(cases));
                tvTotalDeaths.setText(formatter.format(death));
                tvTotalRecovered.setText(formatter.format(recovered));
                tvTotalActive.setText(formatter.format(active));
                tvTotalCritical.setText(formatter.format(critical));
                tvTotalTest.setText(formatter.format(test));

                tvTodayCases.setText(formatter.format(todayCase));
                tvTodayDeaths.setText(formatter.format(todayDeaths));
                tvTodayRecovered.setText(formatter.format(todayRecovered));

                tvPopulation.setText(formatter.format(population));

                // Line chart
                series = new ValueLineSeries();
                series.setColor(0xFF56B7F1);

                series.addPoint(new ValueLinePoint("0", 0));
                series.addPoint(new ValueLinePoint("Case", (float) Long.parseLong(String.valueOf(oneCasePerPeople))));
                series.addPoint(new ValueLinePoint("Dea", (float) Double.parseDouble(String.valueOf(oneDeathPerPeople))));
                series.addPoint(new ValueLinePoint("Test", (float) Double.parseDouble(String.valueOf(oneTestPerPeople))));
                series.addPoint(new ValueLinePoint("Act", (float) Double.parseDouble(String.valueOf(activePerOneMillion))));
                series.addPoint(new ValueLinePoint("Reco", (float) Double.parseDouble(String.valueOf(recoveredPerOneMillion))));
                series.addPoint(new ValueLinePoint("Cri", (float) Double.parseDouble(String.valueOf(criticalPerOneMillion))));
                series.addPoint(new ValueLinePoint("0", 0));

                mCubicValueLineChart.addSeries(series);
                mCubicValueLineChart.startAnimation();


            }

            @Override
            public void onFailure(Call<CountryData> call, Throwable t) {
                Toast.makeText(getContext(), "Connect internet is wrong! ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setProgressBar(){
        // progress bar
        Circle circle = new Circle();
        progressBar.setIndeterminateDrawable(circle);
        progressBar.setVisibility(View.VISIBLE);
    }
}