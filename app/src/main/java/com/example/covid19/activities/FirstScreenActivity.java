package com.example.covid19.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.covid19.R;
import com.example.covid19.utils.Contants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirstScreenActivity extends AppCompatActivity {

    protected boolean _active = true;
    protected int _splashTime = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                SharedPreferences google = FirstScreenActivity.this.getSharedPreferences("com.google.android.gms.signin", Context.MODE_PRIVATE);
                if (google.getString(Contants.defaultGoogleSignInAccount,"").isEmpty()){
                    Intent i3 = new Intent(FirstScreenActivity.this, SlideActivity.class);
                    startActivity(i3);
                    finish();
                }else{
                    Intent intent = new Intent(FirstScreenActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, _splashTime);
    }

}