package com.example.shoponline.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.shoponline.R;
import com.example.shoponline.Services.ProductServices;
import com.example.shoponline.Shared.Entities.ProductEntity;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProductDetailActivity extends AppCompatActivity {
    FirebaseFirestore db;
    ProductServices productServices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();
        productServices = new ProductServices();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail_layout);

        Intent i = getIntent();
        ProductEntity intentProduct = (ProductEntity) i.getSerializableExtra("product");

        setProductDetail(intentProduct);

        findViewById(R.id.imvBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView tvQuantity = findViewById(R.id.tvQuantity);
        TextView tvDetailTotal = findViewById(R.id.tvDetailTotal);
        findViewById(R.id.imvRemoveItem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int quantity = Integer.parseInt(tvQuantity.getText().toString());
                    quantity -= 1;
                    if (quantity >= 0) {
                        tvQuantity.setText(String.valueOf(quantity));
                        tvDetailTotal.setText(String.valueOf(intentProduct.getProductPrice() * quantity));
                    }
                } catch (NumberFormatException e) {
                    Log.e("Error", "Parse number in ProductDetailActivity");
                }
            }
        });

        findViewById(R.id.imvAddItem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int quantity = Integer.parseInt(tvQuantity.getText().toString());
                    quantity += 1;
                    tvQuantity.setText(String.valueOf(quantity));
                    tvDetailTotal.setText(String.valueOf(intentProduct.getProductPrice() * quantity));
                } catch (NumberFormatException e) {
                    Log.e("Error", "Parse number in ProductDetailActivity");
                }
            }
        });

        findViewById(R.id.btnDetailAddToCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void setProductDetail(ProductEntity intentProduct) {
        ((TextView) findViewById(R.id.tvDetailName)).setText(intentProduct.getProductName());
        ((TextView) findViewById(R.id.tvDetailTypes)).setText("Food");
        ((TextView) findViewById(R.id.tvDetailPrice)).setText(intentProduct.getProductPrice().toString() + " đông");
    }
}