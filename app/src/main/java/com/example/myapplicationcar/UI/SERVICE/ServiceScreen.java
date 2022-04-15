package com.example.myapplicationcar.UI.SERVICE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplicationcar.R;
import com.example.myapplicationcar.UI.HOME.HomeScreen;

public class ServiceScreen extends AppCompatActivity {

    private Toolbar mToolbar;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private ImageView step_1, step_2, step_3, step_4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_screen);

        initUI();

        setUp();
    }

    private void initUI() {
        mToolbar = findViewById(R.id.sv_toolbar);
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        step_1 = findViewById(R.id.img_step_1);
        step_2 = findViewById(R.id.img_step_2);
        step_3 = findViewById(R.id.img_step_3);
        step_4 = findViewById(R.id.img_step_4);
    }

    private void setUp() {
        // Toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Đặt dịch vụ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Fragment
        transaction.add(R.id.sv_fragmentContainer, new ServiceFragment())
                .commit();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                openDialog();
                return true;

            default:break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        openDialog();
    }

    void openDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Bạn có đồng ý thoát không?\n"+"Dữ liệu sẽ bị xóa hết!");

        builder.setTitle("Thông báo");

        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(ServiceScreen.this, HomeScreen.class));
                finishAffinity();
            }
        });

        builder.setNegativeButton("Bỏ qua", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void setDoneStep1(){
        step_1.setImageResource(R.drawable.img_step_done);
    }
    public void setDoneStep2(){
        step_2.setImageResource(R.drawable.img_step_done);
    }
    public void setDoneStep3(){
        step_3.setImageResource(R.drawable.img_step_done);
    }

}