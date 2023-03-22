package com.example.shoponline.Services;

import com.example.shoponline.Shared.Entities.CartEntity;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class CartServices extends BaseServices<CartEntity>{
    public CartServices() {
        super(CartEntity.class);
    }

    public Task<Void> insert(FirebaseFirestore db, String username, Map<String, Object> object) {
        String tableName = getTableName();
        Map<String, Object> updates = new HashMap<>();
        for (Map.Entry<String, Object> entry : object.entrySet()) {
            updates.put(entry.getKey(), entry.getValue());
        }
        CollectionReference collectRef = db.collection(tableName);
        return collectRef.document(username).set(updates, SetOptions.merge());
    }

    public Task<Void> clear(FirebaseFirestore db, String username) {
        String tableName = getTableName();
        CollectionReference collectRef = db.collection(tableName);
        return collectRef.document(username).delete();
    }
}
