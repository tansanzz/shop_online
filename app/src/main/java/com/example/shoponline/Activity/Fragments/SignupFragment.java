package com.example.shoponline.Activity.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shoponline.Domain.UserDomain;
import com.example.shoponline.R;
import com.example.shoponline.Shared.Utils.CommonFunction;
import com.example.shoponline.Shared.Utils.LoadingDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupFragment extends Fragment {

    private FirebaseAuth mAuth;
    private LoadingDialog loadingDialog;

    private EditText edtUsername, edtPassword, edtConfirmPassword;

    public SignupFragment() {
        // Required empty public constructor
    }

    public SignupFragment(FirebaseAuth mAuth, LoadingDialog loadingDialog){
        this.mAuth = mAuth;
        this.loadingDialog = loadingDialog;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edtUsername = getView().findViewById(R.id.edtSignupEmail);
        edtPassword = getView().findViewById(R.id.edtSignupPassword);
        edtConfirmPassword = getView().findViewById(R.id.edtSignupConfirmPassword);

        getView().findViewById(R.id.btn_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLogin(v);
            }
        });
    }

    /**
     * Sự kiện khi nhấn nút đăng nhập
     */
    public void onClickLogin(View view) {
        UserDomain user = new UserDomain(edtUsername.getText().toString(), edtPassword.getText().toString(), edtConfirmPassword.getText().toString());

        boolean isUserValid = validateUser(user);

        if (isUserValid) {
            signupToFirebase(user);
        } else {
            Toast.makeText(getContext(), "Tài khoản hoặc mật khẩu không hợp lệ", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Validate người dùng
     */
    private boolean validateUser(UserDomain user) {
        // Tài khoản hoặc mật khẩu không trống
        return !CommonFunction.isEmpty(user.getUsername()) && !CommonFunction.isEmpty(user.getPassword()) && user.isValidPassword();
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
                    Toast.makeText(getContext(), "Đăng ký tài khoản thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Đăng ký tài khoản thất bại, vui lòng thử lại", Toast.LENGTH_SHORT).show();
                }
                loadingDialog.unmask();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.signup_fragment, container, false);
    }
}