package com.sainsbury.productlist.network;
/*
Asyncronous network interactor. Useful to handle asynchronous request. The ConsumerHandler is a sort
of call-back contract.
 */
public interface IAsyncNetworkInteractor<T> {
    void get(ConsumerHandler<T> handler);

    interface ConsumerHandler<T> {
        void onResponse(T response);

        void onError(Exception e);
    }
}
