package com.example.shoponline.Activity.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoponline.R;
import com.example.shoponline.Services.ProductServices;
import com.example.shoponline.Shared.Adapter.ListProductAdapter;
import com.example.shoponline.Shared.Adapter.PopularProductAdapter;
import com.example.shoponline.Shared.Entities.ProductEntity;
import com.example.shoponline.Shared.Enums.ProductType;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends Fragment {

    DatabaseReference mDatabase;
    FirebaseFirestore db;
    ProductServices productServices;
    RecyclerView rcvMenuItem;

    public MenuFragment() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        db = FirebaseFirestore.getInstance();
        productServices = new ProductServices();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                    }
                });
            }
        });
        getView().findViewById(R.id.btnTypeDrink).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    }
                });
            }
        });
    }

    private void showListProduct(ProductType type, List products) {
        ListProductAdapter adapter = null;
        switch (type) {
            case FOOD:
                adapter = new ListProductAdapter(products);
                break;
            case DRINK:
                adapter = new ListProductAdapter(products);
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
}