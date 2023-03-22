package com.example.shoponline.Services;

import com.example.shoponline.Shared.Entities.OrderHistoryEntity;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class OrderHistoryServices extends BaseServices{
    public OrderHistoryServices() {
        super(OrderHistoryEntity.class);
    }

    public Task<QuerySnapshot> getMostBuyProduct(FirebaseFirestore db) {
        String tableName = getTableName();
        CollectionReference collectionRef = db.collection(tableName);
        Query query = collectionRef.orderBy("quantity", Query.Direction.DESCENDING).limit(5);
        return query.get();
    }
}
