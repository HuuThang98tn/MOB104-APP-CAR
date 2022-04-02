package com.example.myapplicationcar.UI.SCREENACCOUNT;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplicationcar.R;
import com.example.myapplicationcar.UI.HOME.HomeScreen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScreenSigin extends AppCompatActivity {
    private EditText edUser, edPass, edRePass;
    private Button btnRegister;
    private TextView tvLogin;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_sigin);

        mAuth = FirebaseAuth.getInstance();

        initUI();

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ScreenSigin.this, ScreenLogin.class));
                finishAffinity();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
            }
        });
    }

    private void createUser() {

        String user = edUser.getText().toString();
        String pass = edPass.getText().toString();
        String rePass = edRePass.getText().toString();

        if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(rePass)) {
            Toast.makeText(this, "Bạn cần nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
        } else if (!isValidEmail(user)) {
            Toast.makeText(this, "Sai định dạng email!", Toast.LENGTH_SHORT).show();
        } else if (pass.length() < 6 || rePass.length() < 6) {
            Toast.makeText(this, "Mật khẩu tối thiểu 6 chữ số!", Toast.LENGTH_SHORT).show();
        } else if (!pass.equals(rePass)) {
            Toast.makeText(this, "Hai mật khẩu không trùng nhau!", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.createUserWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Tạo tài khoản thành công!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), HomeScreen.class));
                        finishAffinity();
                    } else {
                        Toast.makeText(getApplicationContext(), "Tạo tài khoản thất bại!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    public boolean isValidEmail(String email) {
        if (email != null) {
            Pattern p = Pattern.compile("^[A-Za-z].*?@gmail\\.com$");
            Matcher m = p.matcher(email);
            return m.find();
        }
        return false;
    }

    private void initUI() {
        edUser = findViewById(R.id.registered_edUser);
        edPass = findViewById(R.id.registered_edPass);
        edRePass = findViewById(R.id.registered_edRePass);
        btnRegister = findViewById(R.id.registered_btn);
        tvLogin = findViewById(R.id.registered_tvLogin);
    }
}