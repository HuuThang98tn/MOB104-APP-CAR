package com.example.myapplicationcar.UI.SCREENACCOUNT;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplicationcar.R;
import com.example.myapplicationcar.UI.HOME.HomeScreen;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScreenLogin extends AppCompatActivity {
    TextView tv_dangKy, tv_forgotPass ;
    AppCompatButton btn_dangNhap;
    private TextInputEditText edUser, edPass;
    private LinearLayout btnFb, btnGg;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_login);

        mAuth = FirebaseAuth.getInstance();

        initUI();

        tv_dangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ScreenLogin.this,ScreenSigin.class));
            }
        });

        btn_dangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        String user = edUser.getText().toString();
        String pass = edPass.getText().toString();

        if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "Bạn cần nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
        } else if (!isValidEmail(user)) {
            Toast.makeText(this, "Sai định dạng email!", Toast.LENGTH_SHORT).show();
        }else {
            mAuth.signInWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(ScreenLogin.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ScreenLogin.this, HomeScreen.class));
                        finish();
                    } else {
                        Toast.makeText(ScreenLogin.this, "Đăng nhập không thành công! Vui lòng kiểm tra email hoặc mật khẩu?", Toast.LENGTH_SHORT).show();
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
    void initUI (){
        tv_dangKy = findViewById(R.id.tv_dangKy);
        tv_forgotPass = findViewById(R.id.tv_quenPass);
        btn_dangNhap = findViewById(R.id.btn_dangNhap);
        btnFb = findViewById(R.id.btnLoginFb);
        btnGg = findViewById(R.id.btnLoginGg);
        edUser = findViewById(R.id.ed_email);
        edPass = findViewById(R.id.ed_matKhau);
    }
}