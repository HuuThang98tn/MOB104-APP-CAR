package com.example.myapplicationcar.UI.ACCOUNT;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplicationcar.R;
import com.example.myapplicationcar.UI.SCREENACCOUNT.ScreenLogin;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingScreen extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    ImageView imageView, mCamera;
    TextView mEmailUser;
    LinearLayout mLogout, mCallPhone,mInformation;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_screen);
        mAuth = FirebaseAuth.getInstance();

        init();
        showUserInformation();
        pickCamera();
        clickLogOut();
        callPhone();
        information();
    }

    void init() {
        imageView = findViewById(R.id.avtUser);
        mCamera = findViewById(R.id.camera_User);
        mEmailUser = findViewById(R.id.email_user_);
        mLogout = findViewById(R.id.mLogout);
        mCallPhone = findViewById(R.id.mCallPhone);
        mInformation= findViewById(R.id.mInformation);

    }

    // Tải ảnh lên cho user
    void pickCamera() {
        mCamera.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                } else {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Được cấp quyền đối với máy ảnh", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "Quyền máy ảnh bị từ chối", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");


            imageView.setImageBitmap(photo);
        }
    }

    // Gán ảnh và tên
    void showUserInformation() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
//            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            Glide.with(this).load(photoUrl).error(R.drawable.img_df_user).into(imageView);
            mEmailUser.setText(email);
//            Log.d("rd-render", "rd");
        }
    }

    //LogOut
    void clickLogOut() {
        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance()
                        .signOut();
                startActivity(new Intent(SettingScreen.this, ScreenLogin.class));
                finish();
            }
        });
    }

    void callPhone() {
        String mNumberPhone = "0385169494";
        mCallPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                Intent in = new Intent(Intent.ACTION_CALL, Uri.parse(mNumberPhone));
                try {
                    if (!TextUtils.isEmpty(mNumberPhone)) {
                        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mNumberPhone)));
                    }
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(), "Could not find an activity to place the call.", Toast.LENGTH_SHORT).show();

                    Log.d("err", ex + "");
                }
            }
        });
    }
    void information(){
        mInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingScreen.this, InformationScreen.class));

            }
        });
    }
}