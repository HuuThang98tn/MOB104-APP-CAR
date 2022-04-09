package com.example.myapplicationcar.UI.ACCOUNT;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplicationcar.R;
import com.example.myapplicationcar.UI.SCREENACCOUNT.ScreenLogin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SettingScreen extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    ImageView imageView, mCamera;
    TextView mEmailUser;
    LinearLayout mLogout, mCallPhone, mInformation;
    FirebaseAuth mAuth;
    ConstraintLayout mClickEmail, mClickPass, mClickCamera;


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
        clickEmail();
        clickPass();
    }

    void init() {
        imageView = findViewById(R.id.avtUser);
        mCamera = findViewById(R.id.camera_User);
        mEmailUser = findViewById(R.id.email_user_);
        mLogout = findViewById(R.id.mLogout);
        mCallPhone = findViewById(R.id.mCallPhone);
        mInformation = findViewById(R.id.mInformation);
        mClickEmail = findViewById(R.id.email_user);
        mClickPass = findViewById(R.id.updatePass_user);
        mClickCamera = findViewById(R.id.constraint_1);


    }

    // Tải ảnh lên cho user
    // Ảnh nhỏ vl cho to lên tý
    void pickCamera() {
        mClickCamera.setOnClickListener(new View.OnClickListener() {
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
            String name = user.getDisplayName();
            Uri photoUrl = user.getPhotoUrl();

            Glide.with(this).load(photoUrl).error(R.drawable.img_df_user).into(imageView);
            mEmailUser.setText(name);
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

    void information() {
        mInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingScreen.this, InformationScreen.class));

            }
        });
    }
    //Click Update Email

    void clickEmail() {
        mClickEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view_ = inflater.inflate(R.layout.layout_dialog_update_email, null);
                AlertDialog alertDialog = new AlertDialog.Builder(view.getContext())
                        .setPositiveButton("Lưu", null)
                        .setNegativeButton("Hủy", null)
                        .setCancelable(false)
                        .setView(view_)
                        .show();


                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                EditText mED = view_.findViewById(R.id.editText);
                String mName = user.getDisplayName();
                mED.setText(mName);
                Button button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String mEDIT = mED.getText().toString().trim();

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(mEDIT)
                                .build();
                        user.updateProfile(profileUpdates)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            overridePendingTransition(0, 0);
                                            finish();
                                            overridePendingTransition(0, 0);
                                            startActivity(getIntent());
                                            overridePendingTransition(0, 0);
                                            alertDialog.dismiss();
                                            Toast.makeText(SettingScreen.this, "Tên của bạn đã được cập nhật !!!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                });
            }
        });
    }

    void clickPass() {
        mClickPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view_ = inflater.inflate(R.layout.layout_dialog_update_password, null);
                AlertDialog alertDialog = new AlertDialog.Builder(view.getContext())
                        .setPositiveButton("Lưu", null)
                        .setNegativeButton("Hủy", null)
                        .setCancelable(false)
                        .setView(view_)
                        .show();
                EditText mPass = view_.findViewById(R.id.ed_pass);
                EditText mPassConfirm = view_.findViewById(R.id.ed_passConfirm);

                Button button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String mStrPass = mPass.getText().toString().trim();
                        String mStrPassConfirm = mPassConfirm.getText().toString().trim();

                        if (!mStrPass.equals(mStrPassConfirm)) {
                            Log.d("KK", "Không khớp");
                        } else {
                            if (TextUtils.isEmpty(mStrPass) || TextUtils.isEmpty(mStrPassConfirm)) {
                                Toast.makeText(SettingScreen.this, "Bạn cần nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                            } else if (mStrPass.length() < 6 || mPassConfirm.length() < 6) {
                                Toast.makeText(SettingScreen.this, "Mật khẩu tối thiểu 6 chữ số!", Toast.LENGTH_SHORT).show();
                            } else {
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                user.updatePassword(mStrPass)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(SettingScreen.this, "Cập nhật mật khẩu thành công vui lòng đăng nhập lại !!!", Toast.LENGTH_SHORT).show();
                                                    FirebaseAuth.getInstance()
                                                            .signOut();
                                                    startActivity(new Intent(SettingScreen.this, ScreenLogin.class));
                                                    finish();
                                                }
                                            }
                                        });
                            }
                        }
                    }
                });
            }
        });
    }
}