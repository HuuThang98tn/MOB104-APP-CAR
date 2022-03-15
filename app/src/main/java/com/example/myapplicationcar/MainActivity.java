package com.example.myapplicationcar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.myapplicationcar.SCREENACCOUNT.ScreenLogin;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        loadSplashScreen();

    }

    public void loadSplashScreen() {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(MainActivity.this, ScreenLogin.class)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    finish();
                }
            }, 4000);

    }
}