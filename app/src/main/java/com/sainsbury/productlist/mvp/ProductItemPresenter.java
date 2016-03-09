package com.sainsbury.productlist.mvp;
/*
Presenter implementation
 */
public class ProductItemPresenter implements IProductItemPresenter {

    IProductItemView view;
    ProductModel model;

    @Override
    public void setView(IProductItemView view) {
        this.view = view;
    }

    @Override
    public void setModel(ProductModel model) {
        this.model = model;
    }

    @Override
    public void run() {
        if (model == null) {
            return;
        }
        view.setName(model.getName());
        checkQuantity();
        view.setQuantity("" + model.getQuantity());
        view.setImage(model.getImageUrl());
        view.setSinglePrice(model.getSinglePrice());
        view.setUnitPrice(model.getUnitPrice());
    }

    @Override
    public void onPlusClicked() {
        int quantity = model.getQuantity() + 1;
        model.setQuantity(quantity);
        checkQuantity();
        view.setQuantity("" + model.getQuantity());
    }

    @Override
    public void onMinusClicked() {
        int quantity = model.getQuantity() - 1;
        model.setQuantity(quantity);
        checkQuantity();
        view.setQuantity("" + model.getQuantity());
    }

    /*
    Utility method to make sure that the quantity is always above zero
     */
    private void checkQuantity() {
        int quantity = model.getQuantity();
        if (quantity <= 0) {
            quantity = 0;
            model.setQuantity(quantity);
            view.showMinus(false);
        } else {
            view.showMinus(true);
        }
    }
}
