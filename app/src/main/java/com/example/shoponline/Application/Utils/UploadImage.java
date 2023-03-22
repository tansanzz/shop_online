package com.example.shoponline.Application.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UploadImage {
    private ArrayList<String> pathArray;
    private int array_position;

    private StorageReference mStorageRef;

    public UploadImage() {
        pathArray = new ArrayList<>();
    }


    private Bitmap loadImageFromStorage() {
        Bitmap b = null;
        try {
            String path = pathArray.get(array_position);
            File f = new File(path, "");
            b = BitmapFactory.decodeStream(new FileInputStream(f));
        } catch (FileNotFoundException e) {
            Log.e("TAG", "loadImageFromStorage: FileNotFoundException: " + e.getMessage());
        }
        return b;
    }
}
