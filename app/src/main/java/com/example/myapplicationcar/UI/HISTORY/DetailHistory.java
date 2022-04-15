package com.example.myapplicationcar.UI.HISTORY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.myapplicationcar.MODEL.Oder;
import com.example.myapplicationcar.MODEL.Service;
import com.example.myapplicationcar.R;

import java.text.DecimalFormat;

public class DetailHistory extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView bienXe, hang, tenXe, dichVu, ngay, thoiGian, tienDV, tiemSum;
    private Oder oder;
    private Service service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_history);

        getData();

        initUI();

        setUp();
    }

    private void setUp() {
        //toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Chi tiết");
        if (oder != null && service != null){
            bienXe.setText(oder.getNumber_car());
            hang.setText(oder.getType_car());
            tenXe.setText(oder.getName_car());
            if (service.getType().equals("bao_duong_dinh_ky")){
                dichVu.setText("Bảo dưỡng xe ĐK "+service.getName());
            }
            ngay.setText("Ngày: "+oder.getDate());



            thoiGian.setText(convertTime(Integer.parseInt(oder.getTime())));
            DecimalFormat formatter = new DecimalFormat("###,###,###");
            int tb = 0;
            tb =   (Integer.parseInt(String.valueOf(service.getPrice().get(0))) + Integer.parseInt(String.valueOf(service.getPrice().get(1)))) / 2;
            tienDV.setText(formatter.format(tb)+"đ");
            tiemSum.setText(formatter.format(tb + 100000) + "đ");
        }
    }

    private void initUI() {
        toolbar = findViewById(R.id.detail_tb);
        bienXe = findViewById(R.id.dt_bienSo);
        hang = findViewById(R.id.dt_hang);
        tenXe = findViewById(R.id.dt_tenXe);
        dichVu = findViewById(R.id.dt_service);
        ngay = findViewById(R.id.dt_date);
        thoiGian = findViewById(R.id.dt_time);
        tienDV = findViewById(R.id.dt_monney);
        tiemSum = findViewById(R.id.dt_monneySum);
    }
    void getData(){
        oder = (Oder) getIntent().getSerializableExtra("oder");
        service = (Service) getIntent().getSerializableExtra("service");
    }
    String convertTime(int timeSP){
        int minutes = timeSP;

        if (minutes > 60){
            int hour = minutes/60;
            minutes=minutes-hour*60;
            String m = Integer.toString(minutes);
            String h = Integer.toString(hour);
            String newTime=h+"h:"+m+"p";
            return newTime;
        }else {
            String newTime= minutes+"p";
            return newTime;
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:break;
        }
        return super.onOptionsItemSelected(item);
    }
}