package com.example.quicklistapp;

public class Product {
    private String productName;
    private String productDescription;
    private String productCategory;
    private String productImageUrl;

    public  Product(){
        //empty constructor needed
    }

    public  Product(String prodName,String prodDescp,String prodCategory,String prodImgUrl ){
        if(prodName.trim().equals("")){
            prodName = "No Name";
        }
        if(prodDescp.trim().equals("")){
            prodDescp = "No Description";
        }
        if(prodCategory.trim().equals("")){
            prodCategory = "No Category";
        }
        productName = prodName;
        productDescription = prodDescp;
        productCategory = prodCategory;
        productImageUrl = prodImgUrl;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }





}
