package com.example.shoponline.Shared.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoponline.Activity.ProductDetailActivity;
import com.example.shoponline.R;
import com.example.shoponline.Shared.Entities.ProductEntity;

import java.util.List;

public class ListProductAdapter extends RecyclerView.Adapter<ListProductAdapter.ViewHolder> {
    public List<ProductEntity> products;

    public ListProductAdapter(List<ProductEntity> products) {
        this.products = products;
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
