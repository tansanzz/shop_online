package com.example.shoponline.Presentation.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shoponline.Presentation.ForgotPasswordActivity;
import com.example.shoponline.Presentation.HomeActivity;
import com.example.shoponline.Domain.Entities.UserEntity;
import com.example.shoponline.R;
import com.example.shoponline.Application.Utils.CommonFunction;
import com.example.shoponline.Application.Utils.LoadingDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {

    private FirebaseAuth mAuth;
    private LoadingDialog loadingDialog;

    private EditText edtUsername, edtPassword;
    CheckBox isRememberMe;
    public LoginFragment(){
        // require a empty public constructor
    }

    public LoginFragment(FirebaseAuth mAuth, LoadingDialog loadingDialog){
        this.mAuth = mAuth;
        this.loadingDialog = loadingDialog;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edtUsername = getView().findViewById(R.id.edtLoginEmail);
        edtPassword = getView().findViewById(R.id.edtSignupPassword);

        getView().findViewById(R.id.btn_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLogin(v);
            }
        });

        getView().findViewById(R.id.tvForgotPassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ForgotPasswordActivity.class);
                startActivity(i);
            }
        });

        isRememberMe = getView().findViewById(R.id.cbxRememberUser);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username1", "");
        String password = sharedPreferences.getString("password", "");
        if(!CommonFunction.isEmpty(username) && !CommonFunction.isEmpty(password)) {
            edtUsername.setText(username);
            edtPassword.setText(password);
            isRememberMe.setChecked(true);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.login_fragment, container, false);
    }


    /**
     * Sự kiện khi nhấn nút đăng nhập
     */
    public void onClickLogin(View view) {
        UserEntity user = new UserEntity(edtUsername.getText().toString(), edtPassword.getText().toString());

        boolean isUserValid = validateUser(user);

        if (isUserValid) {
            loginToFirebase(user);
        } else {
            Toast.makeText(getContext(), "Tài khoản hoặc mật khẩu không hợp lệ", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Đăng nhập
     */
    private void loginToFirebase(UserEntity user) {
        loadingDialog.mask();

        mAuth.signInWithEmailAndPassword(user.getUsername(), user.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> result) {
                if (result.isSuccessful()) {
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", user.getUsername());
                    if(isRememberMe.isChecked()) {
                        editor.putString("username1", user.getUsername());
                        editor.putString("password", user.getPassword());
                        editor.apply();
                    } else  {
                        editor.remove("username1");
                        editor.remove("password");
                        editor.apply();
                    }
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "Tài khoản hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                }
                loadingDialog.unmask();
            }
        });
    }

    /**
     * Validate người dùng
     */
    private boolean validateUser(UserEntity user) {
        // Tài khoản hoặc mật khẩu không trống
        return !CommonFunction.isEmpty(user.getUsername()) && !CommonFunction.isEmpty(user.getPassword());
    }



}