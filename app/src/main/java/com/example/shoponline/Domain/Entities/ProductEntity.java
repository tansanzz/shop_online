package com.example.shoponline.Domain.Entities;

import com.example.shoponline.Domain.Entities.Anotations.Table;

import java.io.Serializable;
import java.util.UUID;

@Table(name="products")
public class ProductEntity implements Serializable {
    // Id sản phẩm
    private UUID productId;

    // Mã sản phẩm
    private String productCode;

    // Tên sản phẩm
    private String productName;

    // Loại sản phẩm
    private int productType;

    // Loại sản phẩm
    private int productSubType;

    // Giá sản phẩm
    private Double productPrice;

    // Ảnh sản phẩm
    private String productImageSrc;

    public ProductEntity() {
    }

    public ProductEntity(String productName, Double productPrice, String productImageSrc) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productImageSrc = productImageSrc;
    }

    public ProductEntity(String productName) {
        this.productName = productName;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
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

    public int getProductType() {
        return productType;
    }

    public void setProductType(int productType) {
        this.productType = productType;
    }

    public int getProductSubType() {
        return productSubType;
    }

    public void setProductSubType(int productSubType) {
        this.productSubType = productSubType;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductImageSrc() {
        return productImageSrc;
    }

    public void setProductImageSrc(String productImageSrc) {
        this.productImageSrc = productImageSrc;
    }
}
