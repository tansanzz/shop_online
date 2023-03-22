package com.example.shoponline.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shoponline.Activity.Fragments.LoginFragment;
import com.example.shoponline.Activity.Fragments.SignupFragment;
import com.example.shoponline.R;
import com.example.shoponline.Shared.Utils.LoadingDialog;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
//        Intent intent = new Intent(this, HomeActivity.class);
//        startActivity(intent);

        mAuth = FirebaseAuth.getInstance();

        loadingDialog = new LoadingDialog(LoginActivity.this);

        BottomNavigationView loginMenu = findViewById(R.id.bnv_login);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_login,  new LoginFragment(mAuth, loadingDialog)).commit();

        loginMenu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.mi_login:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl_login,  new LoginFragment(mAuth, loadingDialog)).commit();
                        break;
                    case R.id.mi_signup:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl_login,  new SignupFragment(mAuth, loadingDialog)).commit();
                        break;
                };
                return true;
            }
        });
    }
}