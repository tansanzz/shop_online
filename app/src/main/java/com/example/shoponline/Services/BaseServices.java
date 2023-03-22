package com.example.shoponline.Services;

import com.example.shoponline.Shared.Entities.Anotations.Table;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
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

    public Task<Void> insert(FirebaseFirestore db, String document, Map<String, Object> object) {
        String tableName = getTableName();
        CollectionReference collectRef = db.collection(tableName);
        return collectRef.document(document).set(object);
    }

    public Task<DocumentSnapshot> get(FirebaseFirestore db, String document) {
        String tableName = getTableName();
        CollectionReference collectRef = db.collection(tableName);
        return collectRef.document(document).get();
    }

    public Task<Void> delete(FirebaseFirestore db,String username, String document) {
        String tableName = getTableName();
        CollectionReference collectRef = db.collection(tableName);
        Map<String, Object> updates = new HashMap<>();
        updates.put(document, FieldValue.delete());

        return collectRef.document(username).update(updates);
    }

    public Task<Void> update(FirebaseFirestore db,String document, Map<String, Object> object) {
        String tableName = getTableName();
        CollectionReference collectRef = db.collection(tableName);
        return collectRef.document(document).update(object);
    }
}
