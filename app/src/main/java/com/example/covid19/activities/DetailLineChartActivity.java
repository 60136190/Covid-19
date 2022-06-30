package com.example.covid19.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covid19.R;
import com.example.covid19.api.ApiClient;
import com.example.covid19.model.CountryData;
import com.example.covid19.utils.Contants;
import com.github.ybq.android.spinkit.style.Circle;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.PieModel;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;
import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailLineChartActivity extends AppCompatActivity {
    private ValueLineChart mCubicValueLineChart;
    ProgressBar progressBar;
    private TextView tvCasePer;
    private TextView tvDeathPer;
    private TextView tvTestPer;
    private TextView tvActPer;
    private TextView tvRecoPer;
    private TextView tvCriPer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_line_chart);
        initUi();
        setProgressBar();
        getDataVietNam();
    }

    private void initUi() {
        mCubicValueLineChart = findViewById(R.id.detail_chart_line);
        progressBar = findViewById(R.id.spin_kit);
        tvCasePer = findViewById(R.id.tv_case_per);
        tvDeathPer = findViewById(R.id.tv_death_per);
        tvTestPer = findViewById(R.id.tv_test_per);
        tvActPer = findViewById(R.id.tv_active_per);
        tvRecoPer = findViewById(R.id.tv_reco_per);
        tvCriPer = findViewById(R.id.tv_cri_per);


    }

    private void getDataVietNam() {
        Call<CountryData> responseDTOCall = ApiClient.getApiCovid19().getDataVietNam();
        responseDTOCall.enqueue(new Callback<CountryData>() {
            @Override
            public void onResponse(Call<CountryData> call, Response<CountryData> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.INVISIBLE);
                }


                //formatNumber
                DecimalFormat formatter = new DecimalFormat("#,###,###");

                Integer oneCasePerPeople = response.body().getOneCasePerPeople();
                Integer oneDeathPerPeople = response.body().getOneDeathPerPeople();
                Integer oneTestPerPeople = response.body().getOneTestPerPeople();
                Double activePerOneMillion = response.body().getActivePerOneMillion();
                Double recoveredPerOneMillion = response.body().getRecoveredPerOneMillion();
                Double criticalPerOneMillion = response.body().getCriticalPerOneMillion();

                tvCasePer.setText(String.valueOf(formatter.format(oneCasePerPeople)));
                tvDeathPer.setText(String.valueOf(formatter.format(oneDeathPerPeople)));
                tvTestPer.setText(String.valueOf(formatter.format(oneTestPerPeople)));
                tvActPer.setText(String.valueOf(formatter.format(activePerOneMillion)));
                tvRecoPer.setText(String.valueOf(formatter.format(recoveredPerOneMillion)));
                tvCriPer.setText(String.valueOf(formatter.format(criticalPerOneMillion)));

                // Line chart
                ValueLineSeries series = new ValueLineSeries();
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
                Toast.makeText(DetailLineChartActivity.this, "Connect internet is wrong! ", Toast.LENGTH_SHORT).show();
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