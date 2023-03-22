package com.example.shoponline.Presentation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.shoponline.Presentation.Fragments.HistoryFragment;
import com.example.shoponline.Presentation.Fragments.HomeFragment;
import com.example.shoponline.Presentation.Fragments.MenuFragment;
import com.example.shoponline.Presentation.Fragments.MoreFragment;
import com.example.shoponline.R;
import com.example.shoponline.Application.Utils.LoadingDialog;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity {
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadingDialog = new LoadingDialog(HomeActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        BottomNavigationView bottomMenu = findViewById(R.id.bnv_bottom);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_home,  new HomeFragment()).commit();

        bottomMenu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.mi_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl_home,  new HomeFragment()).commit();
                        break;
                    case R.id.mi_menu:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl_home,  new MenuFragment(loadingDialog)).commit();
                        break;
                    case R.id.mi_history:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl_home,  new HistoryFragment()).commit();
                        break;
                    case R.id.mi_more:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl_home,  new MoreFragment()).commit();
                        break;
                };
                return true;
            }
        });
    }
}