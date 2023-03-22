package com.example.shoponline.Domain.Entities;

import com.example.shoponline.Domain.Entities.Anotations.Table;

@Table(name="cart")
public class CartEntity {

    private String cartId;

    private String productCode;

    private String productName;

    private int productQuantity;

    private double productPrice;

    public CartEntity() {
    }

    public CartEntity(String cartId, String productCode, String productName, int productQuantity, double productPrice) {
        this.cartId = cartId;
        this.productCode = productCode;
        this.productName = productName;
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
    }

    public CartEntity(ProductEntity pe) {
        this.productCode = pe.getProductCode();
        this.productName = pe.getProductName();
        this.productPrice = pe.getProductPrice();
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }
}
