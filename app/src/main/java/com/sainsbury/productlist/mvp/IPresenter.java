package com.sainsbury.productlist.mvp;
/*
Generic Presenter that uses a view and a model
 */
public interface IPresenter<V extends IView, M extends Model> {

    void setView(V view);

    void setModel(M model);

    void run();

}
