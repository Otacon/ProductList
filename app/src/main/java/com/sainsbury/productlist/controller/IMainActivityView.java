package com.sainsbury.productlist.controller;

import com.sainsbury.productlist.mvp.ProductModel;

import java.util.Collection;
/*
Main activity interface
 */
public interface IMainActivityView {
    void setProducts(Collection<ProductModel> productModels);

    void setLoading(boolean isLoading);

    void showError(String s);
}
