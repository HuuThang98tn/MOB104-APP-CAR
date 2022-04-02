package com.example.myapplicationcar.UI.SPLASHSCREEN;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.myapplicationcar.R;
import com.example.myapplicationcar.UI.HOME.HomeScreen;
import com.example.myapplicationcar.UI.SCREENACCOUNT.ScreenLogin;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        loadSplashScreen();

    }

    public void loadSplashScreen() {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    nexActivity();
                }
            }, 4000);

    }

    private void nexActivity() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null){
            // Chưa Login
            startActivity(new Intent(SplashActivity.this, ScreenLogin.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }else {
            // Đã Login
            startActivity(new Intent(SplashActivity.this, HomeScreen.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
    }

}