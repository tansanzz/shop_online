package com.example.shoponline.Application.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoponline.R;
import com.example.shoponline.Domain.Entities.ProductEntity;

import java.util.List;

public class PopularProductAdapter extends RecyclerView.Adapter<PopularProductAdapter.ViewHolder> {
    private List<ProductEntity> products;

    public PopularProductAdapter(List<ProductEntity> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public PopularProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.popular_product_view, parent, false);
        PopularProductAdapter.ViewHolder viewHolder = new PopularProductAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PopularProductAdapter.ViewHolder holder, int position) {
        final ProductEntity product = products.get(position);
        holder.imvProductImage.setImageResource(R.drawable.frame_8);
        holder.tvProductName.setText(product.getProductName());
        holder.tvProductPrice.setText(product.getProductPrice() == null ? "" : product.getProductPrice().toString() + " đồng");
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imvProductImage;
        public TextView tvProductName, tvProductPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            this.imvProductImage = itemView.findViewById(R.id.imvProductImage);
            this.tvProductName = itemView.findViewById(R.id.tvProductName);
            this.tvProductPrice = itemView.findViewById(R.id.tvProductPrice);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                }
            });
        }
    }
}
