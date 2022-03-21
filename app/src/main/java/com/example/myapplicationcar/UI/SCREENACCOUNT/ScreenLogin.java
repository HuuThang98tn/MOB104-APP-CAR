package com.example.myapplicationcar.UI.SCREENACCOUNT;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplicationcar.R;
import com.example.myapplicationcar.UI.HOME.HomeScreen;

public class ScreenLogin extends AppCompatActivity {
    TextView tv_dangKy ;
    Button btn_dangNhap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_login);

        connect();

        tv_dangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ScreenLogin.this,ScreenSigin.class));
            }
        });

        btn_dangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ScreenLogin.this, HomeScreen.class));
                finish();
            }
        });
    }
    void connect (){
        tv_dangKy = findViewById(R.id.tv_dangKy);
        btn_dangNhap = findViewById(R.id.btn_dangNhap);
    }
}