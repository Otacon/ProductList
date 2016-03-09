package com.sainsbury.productlist.mvp;
/*
Generic MVP View that has a presenter
 */
public interface IView<P extends IPresenter> {

    P getPresenter();

}
