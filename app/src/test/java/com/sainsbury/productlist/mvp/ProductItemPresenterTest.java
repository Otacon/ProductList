package com.sainsbury.productlist.mvp;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.MockitoAnnotations.initMocks;

public class ProductItemPresenterTest {

    public static final String NAME = "name";
    public static final String IMAGE_URL = "image";
    public static final String SINGLE_PRICE = "singlePrice";
    public static final String UNIT_PRICE = "unitPrice";
    ProductItemPresenter presenter;

    @Mock
    IProductItemView view;

    ProductModel model;

    @Before
    public void setUp() throws Exception {
        presenter = new ProductItemPresenter();
        initMocks(this);
        this.model = new ProductModel();
        presenter.setView(view);
        presenter.setModel(model);
    }

    @Test
    public void testSetView() throws Exception {
        assertNotNull(presenter.view);
    }

    @Test
    public void testSetModel() throws Exception {
        assertNotNull(presenter.model);
    }

    @Test
    public void testRun() throws Exception {
        model.setName(NAME);
        model.setQuantity(2);
        model.setImageUrl(IMAGE_URL);
        model.setSinglePrice(SINGLE_PRICE);
        model.setUnitPrice(UNIT_PRICE);

        presenter.run();

        verify(view).setName(NAME);
        verify(view).setQuantity("2");
        verify(view).setImage(IMAGE_URL);
        verify(view).setSinglePrice(SINGLE_PRICE);
        verify(view).setUnitPrice(UNIT_PRICE);
        verify(view).showMinus(true);

        verifyNoMoreInteractions(view);
    }

    @Test
    public void testRun_hideMinus() throws Exception {
        model.setQuantity(0);

        presenter.run();

        verify(view).setQuantity("0");
        verify(view).showMinus(false);

    }

    @Test
    public void testRun_null() throws Exception {
        presenter.setModel(null);
        presenter.run();
        verifyNoMoreInteractions(view);
    }

    @Test
    public void testOnPlusClicked_increaseByOne() throws Exception {
        model.setQuantity(0);
        presenter.onPlusClicked();
        verify(view).setQuantity("1");
        verify(view).showMinus(true);
        assertEquals(1, model.getQuantity());
        verifyNoMoreInteractions(view);
    }

    @Test
    public void testOnPlusClicked_increaseByOneFromNegative() throws Exception {
        model.setQuantity(-2);
        presenter.onPlusClicked();
        verify(view).setQuantity("0");
        verify(view).showMinus(false);
        assertEquals(0, model.getQuantity());
        verifyNoMoreInteractions(view);
    }

    @Test
    public void testOnMinusClicked_decreaseByOne() throws Exception {
        model.setQuantity(1);
        presenter.onMinusClicked();
        verify(view).setQuantity("0");
        verify(view).showMinus(false);
        assertEquals(0, model.getQuantity());
        verifyNoMoreInteractions(view);
    }
}