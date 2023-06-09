package com.example.shoponline.Presentation.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.shoponline.Presentation.CartActivity;
import com.example.shoponline.R;
import com.example.shoponline.Infrastrucure.Services.ProductServices;
import com.example.shoponline.Application.Adapter.ListProductAdapter;
import com.example.shoponline.Domain.Entities.ProductEntity;
import com.example.shoponline.Application.Enums.ProductType;
import com.example.shoponline.Application.Utils.CommonFunction;
import com.example.shoponline.Application.Utils.LoadingDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends Fragment {

    FirebaseFirestore db;
    ProductServices productServices;
    RecyclerView rcvMenuItem;
    LoadingDialog loadingDialog;
    private Handler handler = new Handler();
    private Runnable runnable;

    public MenuFragment(LoadingDialog loadingDialog) {
        db = FirebaseFirestore.getInstance();
        productServices = new ProductServices();
        this.loadingDialog = loadingDialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAllProducts();
    }

    private void getAllProducts() {
        loadingDialog.mask();
        List<ProductEntity> products = new ArrayList<>();
        productServices.getAll(db).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("TAG", document.getId() + " => " + document.getData());
                        ProductEntity product = document.toObject(ProductEntity.class);
                        products.add(product);
                    }
                    showListProduct(ProductType.FOOD, products);
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
                loadingDialog.unmask();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcvMenuItem = getView().findViewById(R.id.rcvMenuItem);
        rcvMenuItem.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        getView().findViewById(R.id.btnTypeFood).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonStatus(true, getView().findViewById(R.id.btnTypeFood));
                setButtonStatus(false, getView().findViewById(R.id.btnTypeDrink));
                loadingDialog.mask();
                productServices.getAll(db).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<ProductEntity> products = new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("TAG", document.getId() + " => " + document.getData());
                                ProductEntity product = document.toObject(ProductEntity.class);
                                if (product.getProductType() == ProductType.FOOD.getValue()) {
                                    products.add(product);
                                }
                            }
                            showListProduct(ProductType.FOOD, products);
                        } else {
                            Log.d("TAG", "get failed with ", task.getException());
                        }
                        loadingDialog.unmask();
                    }
                });
            }
        });
        getView().findViewById(R.id.btnTypeDrink).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonStatus(true, getView().findViewById(R.id.btnTypeDrink));
                setButtonStatus(false, getView().findViewById(R.id.btnTypeFood));
                loadingDialog.mask();
                productServices.getAll(db).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<ProductEntity> products = new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("TAG", document.getId() + " => " + document.getData());
                                ProductEntity product = document.toObject(ProductEntity.class);
                                if (product.getProductType() == ProductType.DRINK.getValue()) {
                                    products.add(product);
                                }

                            }
                            showListProduct(ProductType.FOOD, products);
                        } else {
                            Log.d("TAG", "get failed with ", task.getException());
                        }
                        loadingDialog.unmask();
                    }
                });
            }
        });
        getView().findViewById(R.id.imvCart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), CartActivity.class);
                startActivity(i);
            }
        });
        EditText edtSearchProducts = getView().findViewById(R.id.edtSearchProducts);

        edtSearchProducts.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
// Hủy bỏ việc xử lý trước đó (nếu có)
                handler.removeCallbacks(runnable);

                // Tạo một Runnable để xử lý sau 300ms
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        String text = edtSearchProducts.getText().toString();
                        if (text.length() == 0) {
                            getAllProducts();
                            return;
                        }
                        loadingDialog.mask();
                        productServices.getAll(db).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            List<ProductEntity> products = new ArrayList<>();
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                Log.d("TAG", document.getId() + " => " + document.getData());
                                                ProductEntity product = document.toObject(ProductEntity.class);
                                                String productNameNomalize = CommonFunction.convertStringToUnaccented(product.getProductName().toLowerCase());
                                                String searchText = CommonFunction.convertStringToUnaccented(text.toLowerCase());
                                                if (productNameNomalize.contains(searchText)) {
                                                    products.add(product);
                                                }
                                            }
                                            showListProduct(ProductType.FOOD, products);
                                        }
                                        loadingDialog.unmask();
                                    }
                                });
                    }
                };

                // Trì hoãn việc xử lý trong 300ms
                handler.postDelayed(runnable, 1000);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void showListProduct(ProductType type, List products) {
        ListProductAdapter adapter = null;
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", getActivity().MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        switch (type) {
            case FOOD:
                adapter = new ListProductAdapter(products, username);
                break;
            case DRINK:
                adapter = new ListProductAdapter(products, username);
                break;
        }
        rcvMenuItem.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.menu_view, container, false);
    }

    public void setButtonStatus(boolean isActtive, Button button) {
        if (isActtive) {
            button.setBackgroundResource(R.drawable.bg_button_active);
            button.setTextColor(Color.parseColor("#F3F3F3"));
        } else {
            button.setBackgroundResource(R.drawable.bg_button_inactive);
            button.setTextColor(Color.parseColor("#808080"));
        }
    }
}