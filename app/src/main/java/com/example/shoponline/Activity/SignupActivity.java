package com.example.shoponline.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shoponline.Domain.UserDomain;
import com.example.shoponline.R;
import com.example.shoponline.Utils.CommonFunction;
import com.example.shoponline.Utils.LoadingDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {

    private EditText edtUsername;
    private EditText edtPassword;
    private EditText edtConfirmPassword;

    private FirebaseAuth mAuth;

    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        edtUsername = findViewById(R.id.edtUsername2);
        edtPassword = findViewById(R.id.edtPassword2);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        mAuth = FirebaseAuth.getInstance();

        loadingDialog = new LoadingDialog(SignupActivity.this);
    }

    /**
     * Sự kiện khi nhấn nút đăng ký
     *
     * @param view
     */
    public void onClickSignup(View view) {
        UserDomain user = new UserDomain(edtUsername.getText().toString(), edtPassword.getText().toString(), edtConfirmPassword.getText().toString());

        boolean isUserValid = validateRegisterUser(user);

        if (isUserValid) {
            signupToFirebase(user);
        }
    }

    /**
     * Validate thông tin đăng ký người dùng
     *
     * @param user
     */
    private void signupToFirebase(UserDomain user) {
        loadingDialog.mask();

        mAuth.createUserWithEmailAndPassword(user.getUsername(), user.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> result) {
                if (result.isSuccessful()) {
                    Toast.makeText(SignupActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(SignupActivity.this, result.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
                loadingDialog.unmask();
            }
        });
    }

    /**
     * @param user
     * @return người dùng có thoả mãn để đăng ký không
     */
    private boolean validateRegisterUser(UserDomain user) {
        // Tài khoản hoặc mật khẩu không trống
        if (CommonFunction.isEmpty(user.getUsername()) || CommonFunction.isEmpty(user.getPassword()) || CommonFunction.isEmpty(user.getConfirmPassword()))
            return false;
        return user.getPassword().equals(user.getConfirmPassword());
    }

    /**
     * @param view
     */
    public void onClickTvLogin(View view) {
        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}