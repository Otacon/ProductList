package com.sainsbury.productlist.network;

import com.sainsbury.productlist.domain.ProductDomain;
import com.sainsbury.productlist.mappers.Mapper;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Collection;

import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class WebViewNetworkInteractorTest {

    @Mock
    Mapper<ProductDTO, ProductDomain> mapper;
    @Mock
    IWebViewHolder holder;
    @Mock
    IAsyncNetworkInteractor.ConsumerHandler<Collection<ProductDomain>> handler;

    private WebViewNetworkInteractor interactor;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        interactor = new WebViewNetworkInteractor(holder, mapper);
    }

    @Test
    public void testGet() throws Exception {
        interactor.get(handler);
        verify(holder).getHtml(interactor.page, interactor.handler);
        assertNotNull(interactor.consumerHandler);
    }
}