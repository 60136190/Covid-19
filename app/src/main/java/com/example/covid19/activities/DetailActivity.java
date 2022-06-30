package com.example.covid19.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.covid19.R;
import com.example.covid19.api.ApiClient;
import com.example.covid19.model.CountryData;
import com.github.ybq.android.spinkit.style.Circle;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.PieModel;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    private PieChart mPieChart;
    private ValueLineChart mCubicValueLineChart;
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
    private TextView tvNameOfCountry;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initUi();
        setProgressBar();
        getCurrentDay();
        getDetailCountry();
    }

    private void initUi() {
        mPieChart = findViewById(R.id.chart);
        mCubicValueLineChart = findViewById(R.id.chart_line);
        tvDate = findViewById(R.id.tv_date);
        tvTodayCases = findViewById(R.id.tv_today_cases);
        tvTodayDeaths = findViewById(R.id.tv_today_deaths);
        tvTodayRecovered = findViewById(R.id.tv_today_recovered);

        tvTotalCases = findViewById(R.id.tv_total_cases);
        tvTotalDeaths = findViewById(R.id.tv_total_deaths);
        tvTotalRecovered = findViewById(R.id.tv_total_recovered);
        tvTotalActive = findViewById(R.id.tv_active);
        tvTotalCritical = findViewById(R.id.tv_total_critical);
        tvTotalTest = findViewById(R.id.tv_test);

        tvNameOfCountry = findViewById(R.id.tv_name_of_country);
        tvPopulation = findViewById(R.id.tv_population);
        progressBar = findViewById(R.id.spin_kit);


    }

    private void getCurrentDay() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        tvDate.setText(formatter.format(date));
    }


    private void getDetailCountry() {
        Intent intent = getIntent();
        int a = Integer.parseInt(intent.getStringExtra("someKey"));
        Call<CountryData> responseDTOCall = ApiClient.getApiCovid19().getDataCountry(a);
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

                String name = response.body().getCountry();
                tvNameOfCountry.setText(name);

                mPieChart.addPieSlice(new PieModel("Deaths", (float) Double.parseDouble(String.valueOf(death)), getResources().getColor(R.color.live_update)));
                mPieChart.addPieSlice(new PieModel("Recovered", (float) Double.parseDouble(String.valueOf(recovered)), getResources().getColor(R.color.recovered)));
                mPieChart.addPieSlice(new PieModel("Active", (float) Double.parseDouble(String.valueOf(active)), getResources().getColor(R.color.active)));
                mPieChart.addPieSlice(new PieModel("Critical", (float) Double.parseDouble(String.valueOf(critical)), getResources().getColor(R.color.critical)));
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
                ValueLineSeries series = new ValueLineSeries();
                series.setColor(0xFF56B7F1);

                series.addPoint(new ValueLinePoint("0", 0));
                series.addPoint(new ValueLinePoint("Case", (float) Double.parseDouble(String.valueOf(oneCasePerPeople))));
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
                Toast.makeText(DetailActivity.this, "Connect internet is wrong! ", Toast.LENGTH_SHORT).show();
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