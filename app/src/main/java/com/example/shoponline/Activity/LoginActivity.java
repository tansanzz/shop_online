package com.example.shoponline.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shoponline.Utils.CommonFunction;
import com.example.shoponline.Domain.UserDomain;
import com.example.shoponline.R;
import com.example.shoponline.Utils.LoadingDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText edtUsername;
    private EditText edtPassword;

    private FirebaseAuth mAuth;

    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        mAuth = FirebaseAuth.getInstance();

        loadingDialog = new LoadingDialog(LoginActivity.this);
    }

    /**
     * Sự kiện khi nhấn nút đăng nhập
     *
     * @param view
     */
    public void onClickLogin(View view) {
        UserDomain user = new UserDomain(edtUsername.getText().toString(), edtPassword.getText().toString());

        boolean isUserValid = validateUser(user);

        if (isUserValid) {
            loginToFirebase(user);
        }
    }

    /**
     * Validate người dùng
     *
     * @param user
     */
    private boolean validateUser(UserDomain user) {
        // Tài khoản hoặc mật khẩu không trống
        return CommonFunction.isEmpty(user.getUsername()) && CommonFunction.isEmpty(user.getPassword());
    }

    /**
     * Đăng nhập
     *
     * @param user
     */
    private void loginToFirebase(UserDomain user) {
        loadingDialog.mask();

        mAuth.signInWithEmailAndPassword(user.getUsername(), user.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> result) {
                if (result.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, result.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
                loadingDialog.unmask();
            }
        });
    }

    public void onClickTvSignUp(View view) {
        Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
        startActivity(intent);
    }
}