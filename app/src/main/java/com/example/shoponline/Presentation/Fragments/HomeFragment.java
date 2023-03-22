package com.example.shoponline.Presentation.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoponline.Presentation.CartActivity;
import com.example.shoponline.R;
import com.example.shoponline.Infrastrucure.Services.OrderHistoryServices;
import com.example.shoponline.Application.Adapter.PopularProductAdapter;
import com.example.shoponline.Domain.Entities.ProductEntity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    FirebaseFirestore db;

    public HomeFragment() {
        // Required empty public constructor
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.home_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rcvPopularFood = (RecyclerView) getView().findViewById(R.id.rcvPopularFood);
        RecyclerView rcvPopularDrink = (RecyclerView) getView().findViewById(R.id.rcvPopularDrink);

        List products = new ArrayList<ProductEntity>();
        OrderHistoryServices orderHistoryServices = new OrderHistoryServices();
        orderHistoryServices.getMostBuyProduct(db).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<DocumentSnapshot> documentSnapshots = task.getResult().getDocuments();
                    for (DocumentSnapshot documnet:documentSnapshots) {
                        String fieldName = documnet.getId();
                        products.add(new ProductEntity(fieldName, null, ""));
                    }

                    PopularProductAdapter adapter = new PopularProductAdapter(products);

                    rcvPopularFood.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                    rcvPopularFood.setAdapter(adapter);

                    rcvPopularDrink.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                    rcvPopularDrink.setAdapter(adapter);
                }
            }
        });

        getView().findViewById(R.id.imvCart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), CartActivity.class);
                startActivity(i);
            }
        });
    }
}