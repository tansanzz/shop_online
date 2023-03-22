package com.example.shoponline.Services;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.shoponline.Shared.Entities.ProductEntity;
import com.example.shoponline.Shared.Enums.ProductType;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class ProductServices extends BaseServices<ProductEntity> {
    public ProductServices() {
        super(ProductEntity.class);
    }
}
