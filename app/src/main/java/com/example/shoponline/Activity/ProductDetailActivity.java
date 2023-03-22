package com.example.shoponline.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.shoponline.R;
import com.example.shoponline.Services.CartServices;
import com.example.shoponline.Services.ProductServices;
import com.example.shoponline.Shared.Entities.ProductEntity;
import com.example.shoponline.Shared.Utils.LoadingDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ProductDetailActivity extends AppCompatActivity {
    FirebaseFirestore db;
    ProductServices productServices;
    CartServices cartServices;
    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();
        productServices = new ProductServices();
        cartServices = new CartServices();
        loadingDialog = new LoadingDialog(ProductDetailActivity.this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail_layout);

        Intent i = getIntent();
        ProductEntity intentProduct = (ProductEntity) i.getSerializableExtra("product");

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        loadingDialog.mask();
        cartServices.get(db, username).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    Map<String, Object> result = task.getResult().getData();
                    if (result != null && result.size() > 0 && result.get(intentProduct.getProductCode()) != null) {
                        TextView tvQuantity = findViewById(R.id.tvQuantity);
                        tvQuantity.setText(String.valueOf(result.get(intentProduct.getProductCode())));
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
                loadingDialog.unmask();
            }
        });

        setProductDetail(intentProduct);

        findViewById(R.id.imvBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        TextView tvDetailTotal = findViewById(R.id.tvDetailTotal);
        findViewById(R.id.imvRemoveItem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    TextView tvQuantity = findViewById(R.id.tvQuantity);
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
                    TextView tvQuantity = findViewById(R.id.tvQuantity);
                    int quantity = Integer.parseInt(tvQuantity.getText().toString());
                    quantity += 1;
                    tvQuantity.setText(String.valueOf(quantity));
                    tvDetailTotal.setText(String.valueOf(intentProduct.getProductPrice() * quantity));
                } catch (NumberFormatException e) {
                    Log.e("Error", "Parse number in ProductDetailActivity");
                }
            }
        });

        Map<String, Object> cartItem = new HashMap<>();

        findViewById(R.id.btnDetailAddToCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialog.mask();
                try {
                    TextView tvQuantity = findViewById(R.id.tvQuantity);
                    int quantity = Integer.parseInt(tvQuantity.getText().toString());
                    cartItem.put(intentProduct.getProductCode(), quantity);
                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    String username = sharedPreferences.getString("username", "");
                    if(quantity > 0) {
                        cartServices.insert(db, username, cartItem).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                loadingDialog.unmask();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                loadingDialog.unmask();
                            }
                        });
                    } else {
                        cartServices.delete(db, username, intentProduct.getProductCode()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                loadingDialog.unmask();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                loadingDialog.unmask();
                            }
                        });
                    }
                } catch (NumberFormatException e) {
                    Log.e("Error", "Parse number in ProductDetailActivity");
                }

            }
        });
    }

    private void setProductDetail(ProductEntity intentProduct) {
        ((TextView) findViewById(R.id.tvDetailName)).setText(intentProduct.getProductName());
        ((TextView) findViewById(R.id.tvDetailTypes)).setText("Food");
        ((TextView) findViewById(R.id.tvDetailPrice)).setText(intentProduct.getProductPrice().toString() + " đông");
    }
}