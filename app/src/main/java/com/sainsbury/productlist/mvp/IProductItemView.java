package com.sainsbury.productlist.mvp;
/*
Product View that will be used to render the model. The main in between is the IProductItemPresenter.
 */
public interface IProductItemView extends IView<IProductItemPresenter> {
    void setQuantity(String quantity);

    void setName(String name);

    void setImage(String imageUrl);

    void setSinglePrice(String singlePrice);

    void setUnitPrice(String unitPrice);

    void showMinus(boolean b);
}
