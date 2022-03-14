package com.example.myapplicationcar.SCREENACCOUNT;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.myapplicationcar.R;

public class ScreenLogin extends AppCompatActivity {
    TextView tv_dangKy ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_login);

        tv_dangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ScreenLogin.this,ScreenSigin.class));
            }
        });
    }
    void connect (){
        tv_dangKy = findViewById(R.id.tv_dangKy);
    }
}