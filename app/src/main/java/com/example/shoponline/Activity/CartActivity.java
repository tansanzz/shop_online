package com.example.shoponline.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.shoponline.R;
import com.example.shoponline.Services.CartServices;
import com.example.shoponline.Services.ProductServices;
import com.example.shoponline.Shared.Entities.CartEntity;
import com.example.shoponline.Shared.Entities.ProductEntity;
import com.example.shoponline.Shared.Enums.ProductType;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    FirebaseFirestore db;
    CartServices cartServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();
        cartServices = new CartServices();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_layout);

        findViewById(R.id.imvCartBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        List<CartEntity> cart = new ArrayList<>();
        cartServices.getAll(db).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("TAG", document.getId() + " => " + document.getData());
                        CartEntity product = document.toObject(CartEntity.class);
                        cart.add(product);
                    }
                    showListProduct(cart);
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });
    }

    private void showListProduct(List<CartEntity> cart) {
        TextView tvCartTotal = findViewById(R.id.tvCartTotal);
        tvCartTotal.setText(String.valueOf(calculateTotalPrice(cart)));
    }

    private double calculateTotalPrice(List<CartEntity> cart) {
        double total = 0;
        for (CartEntity product: cart) {
            total += product.getProductPrice() * product.getProductQuantity();
        }
        return total;
    }
}