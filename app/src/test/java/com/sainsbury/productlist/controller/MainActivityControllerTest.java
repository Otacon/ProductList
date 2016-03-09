package com.sainsbury.productlist.controller;

import com.sainsbury.productlist.domain.ProductDomain;
import com.sainsbury.productlist.mappers.Mapper;
import com.sainsbury.productlist.mvp.ProductModel;
import com.sainsbury.productlist.network.IAsyncNetworkInteractor;
import com.sainsbury.productlist.utils.IStringResource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Collection;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class MainActivityControllerTest {

    public static final String ERROR = "error";

    @Mock
    IMainActivityView activity;
    @Mock
    IStringResource stringResource;
    @Mock
    Mapper<ProductDomain, ProductModel> productModelMapper;
    @Mock
    IAsyncNetworkInteractor<Collection<ProductDomain>> interactor;

    MainActivityController controller;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        controller = new MainActivityController(activity, stringResource, productModelMapper, interactor);
    }

    @Test
    public void testOnCreate() throws Exception {
        controller.onCreate();
        verifyNoMoreInteractions(activity, stringResource, productModelMapper, interactor);
    }

    @Test
    public void testOnStart() throws Exception {
        controller.onStart();
        verifyNoMoreInteractions(activity, stringResource, productModelMapper, interactor);
    }

    @Test
    public void testOnResume() throws Exception {
        controller.onResume();
        verify(activity).setProducts(new ArrayList<ProductModel>());
        verify(activity).setLoading(true);
        verify(interactor).get(controller.handler);
    }

    @Test
    public void testOnPause() throws Exception {
        controller.onPause();
        verifyNoMoreInteractions(activity, stringResource, productModelMapper, interactor);
    }

    @Test
    public void testOnStop() throws Exception {
        controller.onStop();
        verifyNoMoreInteractions(activity, stringResource, productModelMapper, interactor);
    }

    @Test
    public void testOnDestroy() throws Exception {
        controller.onDestroy();
        verifyNoMoreInteractions(activity, stringResource, productModelMapper, interactor);
    }

    @Test
    public void testOnRefresh() throws Exception {
        controller.onRefresh();
        verify(activity).setProducts(new ArrayList<ProductModel>());
        verify(activity).setLoading(true);
        verify(interactor).get(controller.handler);
    }

    @Test
    public void testHandler_OnResponse() throws Exception {
        ArrayList<ProductDomain> productDomains = new ArrayList<>();
        ArrayList<ProductModel> productModels = new ArrayList<>();

        when(productModelMapper.mapUp(productDomains)).thenReturn(productModels);

        controller.handler.onResponse(productDomains);

        verify(productModelMapper).mapUp(productDomains);
        verify(activity).setProducts(productModels);
        verify(activity).setLoading(false);
    }

    @Test
    public void testHandler_OnError() throws Exception {
        ArrayList<ProductDomain> productDomains = new ArrayList<>();
        ArrayList<ProductModel> productModels = new ArrayList<>();

        when(stringResource.getString(controller.GENERIC_ERROR)).thenReturn(ERROR);

        controller.handler.onError(null);

        verify(activity).setLoading(false);
        verify(activity).showError(ERROR);
    }
}