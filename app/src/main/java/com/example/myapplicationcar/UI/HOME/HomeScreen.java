package com.example.myapplicationcar.UI.HOME;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myapplicationcar.R;
import com.example.myapplicationcar.UI.MAP.MapScreen;
import com.example.myapplicationcar.UI.SETTING.SettingScreen;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeScreen extends AppCompatActivity {
    private BottomNavigationView mBottomTab;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);


        initUI();

        setup();
    }

    private void setup() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Auto Care");

        mBottomTab.setSelectedItemId(R.id.action_home);
        mBottomTab.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_home:
                        Toast.makeText(HomeScreen.this, "Đây là Home", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.action_map:
                        startActivity(new Intent(HomeScreen.this, MapScreen.class));
                        Toast.makeText(HomeScreen.this, "Đây là Map", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.action_setting:
                        startActivity(new Intent(HomeScreen.this, SettingScreen.class));
                        Toast.makeText(HomeScreen.this, "Đây là Setting", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return false;
            }
        });
    }

    private void initUI() {
        mBottomTab = findViewById(R.id.home_bottomTab);
        mToolbar = findViewById(R.id.home_toolbar);
    }


}