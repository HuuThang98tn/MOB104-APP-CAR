package com.example.myapplicationcar.UI.ACCOUNT;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.myapplicationcar.R;

public class InformationScreen extends AppCompatActivity {

    ImageView  mBack, imgBanner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_screen);


        mBack = findViewById(R.id.avatarUser);
        imgBanner = findViewById(R.id.img_company);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        getBanner();
    }
    public void getBanner() {
        String photoUrl = "https://firebasestorage.googleapis.com/v0/b/mob104-auto-care.appspot.com/o/Banner%2Fbanner_2.jpg?alt=media&token=2b246630-1fe0-48c3-9e51-9c6b65d019ed";

        Glide.with(this).load(photoUrl).error(R.drawable.img_df_user).into(imgBanner);
    }
}