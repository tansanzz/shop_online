package com.example.shoponline.Shared.Adapter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoponline.Activity.ProductDetailActivity;
import com.example.shoponline.R;
import com.example.shoponline.Services.CartServices;
import com.example.shoponline.Shared.Entities.ProductEntity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Map;

public class ListProductAdapter extends RecyclerView.Adapter<ListProductAdapter.ViewHolder> {
    public List<ProductEntity> products;
    CartServices cartServices;
    FirebaseFirestore db;
    String username;

    public ListProductAdapter(List<ProductEntity> products, String username) {
        this.products = products;
        cartServices = new CartServices();
        db = FirebaseFirestore.getInstance();
        this.username = username;
    }

    @NonNull
    @Override
    public ListProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_product_view, parent, false);
        ListProductAdapter.ViewHolder viewHolder = new ListProductAdapter.ViewHolder(listItem);
        viewHolder.setItemClickListener(products);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListProductAdapter.ViewHolder holder, int position) {
        final ProductEntity product = products.get(position);

        cartServices.get(db, username).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {
                    Map<String, Object> result = task.getResult().getData();
                    if (result != null && result.size() > 0 && result.get(product.getProductCode()) != null) {
                        holder.imvAdd.setImageResource(R.drawable.icon_checked);
                    }
                } else if (!task.isSuccessful()) {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });
        holder.tvProductName.setText(product.getProductName());
        holder.tvProductPrice.setText(product.getProductPrice().toString() + " đồng");
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvProductName, tvProductPrice;
        public ImageView imvAdd;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tvProductName = itemView.findViewById(R.id.tvName);
            this.tvProductPrice = itemView.findViewById(R.id.tvPrice);
            this.imvAdd = itemView.findViewById(R.id.imv_add);
        }

        public void setItemClickListener(List<ProductEntity> products) {
            imvAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    Intent i = new Intent(itemView.getContext(), ProductDetailActivity.class);
                    ProductEntity intentProduct = products.get(pos);
                    i.putExtra("product", intentProduct);
                    itemView.getContext().startActivity(i);
                }
            });
        }
    }
}
