package com.example.myapplicationcar.UI.HISTORY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myapplicationcar.R;
import com.example.myapplicationcar.UI.ACCOUNT.SettingScreen;
import com.example.myapplicationcar.UI.HOME.HomeScreen;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HistoryScreen extends AppCompatActivity {
    private Toolbar htToolbar;
    private BottomNavigationView htBoottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_screen);

        initUI();

        setUp();
    }

    void setUp() {

        //Toolbar setup
        setSupportActionBar(htToolbar);
        getSupportActionBar().setTitle("Lịch sử");

        //Set bottoom tab
        htBoottom.setSelectedItemId(R.id.action_history);
        htBoottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        startActivity(new Intent(HistoryScreen.this, HomeScreen.class));
                        return true;
                    case R.id.action_history:
                        return true;
                    case R.id.action_account:
                        startActivity(new Intent(HistoryScreen.this, SettingScreen.class));
                        return true;
                }
                return false;
            }
        });

    }


    void initUI() {
        htToolbar = findViewById(R.id.history_toolbar);
        htBoottom = findViewById(R.id.history_bottomTab);
    }
}