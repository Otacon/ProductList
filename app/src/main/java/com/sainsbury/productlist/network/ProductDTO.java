package com.sainsbury.productlist.network;
/*
Network data representation of a product
 */
public class ProductDTO {
    String name;
    String imageUrl;
    String unitPrice;
    String singlePrice;

    public ProductDTO(String name, String imageUrl, String unitPrice, String singlePrice) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.unitPrice = unitPrice;
        this.singlePrice = singlePrice;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public String getSinglePrice() {
        return singlePrice;
    }
}
