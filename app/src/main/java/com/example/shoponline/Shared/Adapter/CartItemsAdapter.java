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
import com.example.shoponline.Shared.Entities.CartEntity;
import com.example.shoponline.Shared.Entities.ProductEntity;

import java.util.List;

public class CartItemsAdapter extends RecyclerView.Adapter<CartItemsAdapter.ViewHolder>{

    List<CartEntity> cart;
    public CartItemsAdapter(List<CartEntity> cart) {
        this.cart = cart;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.cart_item_view, parent, false);
        CartItemsAdapter.ViewHolder viewHolder = new CartItemsAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final CartEntity cartItem = cart.get(position);
        holder.tvCartItemName.setText(cartItem.getProductName());
        holder.tvCartItemQuantity.setText("x" + cartItem.getProductQuantity());
    }

    @Override
    public int getItemCount() {
        return cart.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvCartItemName, tvCartItemQuantity;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tvCartItemName = itemView.findViewById(R.id.tvCartItemName);
            this.tvCartItemQuantity = itemView.findViewById(R.id.tvCartItemQuantity);
        }
    }
}
