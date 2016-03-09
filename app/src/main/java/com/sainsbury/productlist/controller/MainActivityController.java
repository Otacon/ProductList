package com.sainsbury.productlist.controller;

import com.sainsbury.productlist.R;
import com.sainsbury.productlist.domain.ProductDomain;
import com.sainsbury.productlist.mappers.Mapper;
import com.sainsbury.productlist.mvp.ProductModel;
import com.sainsbury.productlist.network.IAsyncNetworkInteractor;
import com.sainsbury.productlist.utils.IStringResource;

import java.util.ArrayList;
import java.util.Collection;

public class MainActivityController implements IMainActivityController {

    final int GENERIC_ERROR = R.string.generic_error;
    final IMainActivityView activity;
    final Mapper<ProductDomain, ProductModel> productModelMapper;
    final IAsyncNetworkInteractor<Collection<ProductDomain>> interactor;
    final IStringResource stringResource;
    final IAsyncNetworkInteractor.ConsumerHandler<Collection<ProductDomain>> handler = new IAsyncNetworkInteractor.ConsumerHandler<Collection<ProductDomain>>() {
        @Override
        public void onResponse(Collection<ProductDomain> productDomains) {
            Collection<ProductModel> productModels = productModelMapper.mapUp(productDomains);
            activity.setProducts(productModels);
            activity.setLoading(false);
        }

        @Override
        public void onError(Exception e) {
            activity.setLoading(false);
            activity.showError(stringResource.getString(GENERIC_ERROR));
        }
    };

    /*
    Dependency injection of the needed components in order to retrieve the resources:
    - activity interface to communicate with the component
    - strings from string.xml (IStringResource to mock it)
    - model mapper, to map from Domain to the Model (mvp model)
    - interactor: an asynchronous network request component
     */
    public MainActivityController(IMainActivityView activity,
                                  IStringResource stringResource,
                                  Mapper<ProductDomain, ProductModel> productModelMapper,
                                  IAsyncNetworkInteractor<Collection<ProductDomain>> interactor) {
        this.activity = activity;
        this.productModelMapper = productModelMapper;
        this.interactor = interactor;
        this.stringResource = stringResource;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onStart() {
    }

    @Override
    public void onResume() {
        refreshData();
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onRefresh() {
        refreshData();
    }

    /*
    Emptying the view e showing the loading spinner before fetching the new items.
    Just a personal preference in order to not show the user blinking stuff. It causes to loose all
    the previously downloaded data so if something goes wrong he's unable to see what was the list
    before
     */
    private void refreshData() {
        activity.setProducts(new ArrayList<ProductModel>());
        activity.setLoading(true);
        interactor.get(handler);
    }
}
