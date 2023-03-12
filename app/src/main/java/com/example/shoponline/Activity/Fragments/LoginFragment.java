package com.example.shoponline.Activity.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.shoponline.Activity.HomeActivity;
import com.example.shoponline.Domain.UserDomain;
import com.example.shoponline.R;
import com.example.shoponline.Shared.Utils.CommonFunction;
import com.example.shoponline.Shared.Utils.LoadingDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {

    private FirebaseAuth mAuth;
    private LoadingDialog loadingDialog;

    private EditText edtUsername, edtPassword;

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
        UserDomain user = new UserDomain(edtUsername.getText().toString(), edtPassword.getText().toString());

        boolean isUserValid = validateUser(user);

        if (isUserValid) {
            loginToFirebase(user);
        }
    }

    /**
     * Đăng nhập
     */
    private void loginToFirebase(UserDomain user) {
        loadingDialog.mask();

        mAuth.signInWithEmailAndPassword(user.getUsername(), user.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> result) {
                if (result.isSuccessful()) {
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    startActivity(intent);
                } else {
                    System.out.println("error");
                }
                loadingDialog.unmask();
            }
        });
    }

    /**
     * Validate người dùng
     */
    private boolean validateUser(UserDomain user) {
        // Tài khoản hoặc mật khẩu không trống
        return !CommonFunction.isEmpty(user.getUsername()) && !CommonFunction.isEmpty(user.getPassword());
    }



}