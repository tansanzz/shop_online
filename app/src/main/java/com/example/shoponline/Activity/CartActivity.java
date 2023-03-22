package com.example.shoponline.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoponline.R;
import com.example.shoponline.Services.CartServices;
import com.example.shoponline.Services.OrderHistoryServices;
import com.example.shoponline.Services.ProductServices;
import com.example.shoponline.Shared.Adapter.CartItemsAdapter;
import com.example.shoponline.Shared.Adapter.PopularProductAdapter;
import com.example.shoponline.Shared.Entities.CartEntity;
import com.example.shoponline.Shared.Entities.ProductEntity;
import com.example.shoponline.Shared.Enums.ProductType;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartActivity extends AppCompatActivity {

    FirebaseFirestore db;
    CartServices cartServices;
    OrderHistoryServices orderHistoryServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();
        cartServices = new CartServices();
        orderHistoryServices = new OrderHistoryServices();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_layout);

        findViewById(R.id.imvCartBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        List<CartEntity> cart = new ArrayList<>();
        OrderHistoryServices orderHistoryServices = new OrderHistoryServices();
        findViewById(R.id.btnBuyProducts).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                String username = sharedPreferences.getString("username", "");
                cartServices.clear(db, username);
                for (CartEntity item: cart) {
                    orderHistoryServices.get(db, item.getProductCode()).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()) {
                                DocumentSnapshot result = task.getResult();
                                if(result.exists()) {
                                    int quantity = Integer.parseInt(result.get("quantity").toString());
                                    Map<String, Integer> orderHistory = new HashMap<>();
                                    orderHistory.put("quantity" , quantity + item.getProductQuantity());
                                    orderHistoryServices.update(db, item.getProductName(), orderHistory);
                                    return;
                                }
                            }
                                Map<String, Integer> orderHistory = new HashMap<>();
                                orderHistory.put("quantity" , item.getProductQuantity());
                                orderHistoryServices.insert(db, item.getProductName(), orderHistory);
                        }
                    });
                }
                Toast.makeText(getApplicationContext(), "Mua hàng thành công", Toast.LENGTH_SHORT).show();
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        ProductServices productServices = new ProductServices();

        productServices.getAll(db).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Map<String, ProductEntity> products = new HashMap<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("TAG", document.getId() + " => " + document.getData());
                        ProductEntity product = document.toObject(ProductEntity.class);
                        products.put(product.getProductCode(), product);
                    }
                    if(products.size() > 0) {
                        cartServices.get(db, username).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    Map<String, Object> result = task.getResult().getData();
                                    if(result != null) {
                                        for (Map.Entry<String, Object> entry : result.entrySet()) {
                                            String key = entry.getKey();
                                            ProductEntity pe =  products.get(key);
                                            CartEntity product = new CartEntity(pe);
                                            product.setProductQuantity(Integer.parseInt(entry.getValue().toString()));
                                            cart.add(product);
                                        }
                                        showListProduct(cart);
                                    }
                                } else if (!task.isSuccessful()) {
                                    Log.d("TAG", "get failed with ", task.getException());
                                }
                            }
                        });
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });
    }

    private void showListProduct(List<CartEntity> cart) {
        TextView tvCartTotal = findViewById(R.id.tvCartTotal);
        tvCartTotal.setText(String.valueOf(calculateTotalPrice(cart)));

        RecyclerView rcv_cartItems = findViewById(R.id.rcv_cartItems);
        CartItemsAdapter adapter = new CartItemsAdapter(cart);

        rcv_cartItems.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        rcv_cartItems.setAdapter(adapter);
    }

    private double calculateTotalPrice(List<CartEntity> cart) {
        double total = 0;
        for (CartEntity product: cart) {
            total += product.getProductPrice() * product.getProductQuantity();
        }
        return total;
    }
}