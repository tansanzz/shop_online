package com.example.shoponline.Services;

import com.example.shoponline.Shared.Entities.Anotations.Table;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class BaseServices<T> {
    private Class<T> thisClass;

    public BaseServices(Class<T> thisClass) {
        this.thisClass = thisClass;
    }

    public String getTableName() {
        return thisClass.getAnnotation(Table.class).name();
    }

    public Task<QuerySnapshot> getAll(FirebaseFirestore db) {
        String tableName = getTableName();
        CollectionReference collectRef = db.collection(tableName);
        return collectRef.get();
    }

    public Task<Void> insert(FirebaseFirestore db, String documnent, Map<String, Object> object) {
        String tableName = getTableName();
        CollectionReference collectRef = db.collection(tableName);
        return collectRef.document(documnent).set(object);
    }
}
