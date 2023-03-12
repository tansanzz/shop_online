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

    public Task<QuerySnapshot> getAllFoods(FirebaseFirestore db) {
        String tableName = getTableName();
        CollectionReference collectRef = db.collection(tableName);
        return collectRef.whereEqualTo("ProductType", ProductType.FOOD).get();
    }

    public Task<QuerySnapshot> getAllDrinks(FirebaseFirestore db) {
        String tableName = getTableName();
        CollectionReference collectRef = db.collection(tableName);
        return collectRef.whereEqualTo("ProductType", ProductType.DRINK).get();
    }
}
