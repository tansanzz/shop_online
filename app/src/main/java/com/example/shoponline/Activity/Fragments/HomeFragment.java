package com.example.shoponline.Activity.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoponline.R;
import com.example.shoponline.Shared.Adapter.PopularProductAdapter;
import com.example.shoponline.Shared.Entities.ProductEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
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
        products.add(new ProductEntity("Cơm", 15.5, ""));
        products.add(new ProductEntity("Cơm 1", 5.55, ""));
        products.add(new ProductEntity("Cơm 1",51.5, ""));
        PopularProductAdapter adapter = new PopularProductAdapter(products);

        rcvPopularFood.setLayoutManager(new LinearLayoutManager( getContext(), LinearLayoutManager.HORIZONTAL, false));
        rcvPopularFood.setAdapter(adapter);

        rcvPopularDrink.setLayoutManager(new LinearLayoutManager( getContext(), LinearLayoutManager.HORIZONTAL, false));
        rcvPopularDrink.setAdapter(adapter);
    }
}