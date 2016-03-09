package com.sainsbury.productlist.mvp;
/*
Product Presenter that uses a ProductItemView and it's data representation (model)
It exposes all the user interactions (clicks on + and - buttons)
 */
public interface IProductItemPresenter extends IPresenter<IProductItemView, ProductModel> {

    void onPlusClicked();

    void onMinusClicked();
}
