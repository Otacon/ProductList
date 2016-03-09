package com.sainsbury.productlist.mappers;

import com.sainsbury.productlist.domain.CurrencyDomain;
import com.sainsbury.productlist.domain.ProductDomain;
import com.sainsbury.productlist.mvp.ProductModel;
import com.sainsbury.productlist.utils.IStringResource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ProductModelMapperTest {

    public static final String EACH = "each";
    public static final String UNIT = "unit";
    public static final String IMAGE_URL = "imageUrl";
    public static final String NAME = "name";
    public static final String WEIRD_UNIT_PRICE = "asdasd";
    public static final String WEIRD_SINGLE_PRICE = "qweqwe";
    @Mock
    IStringResource stringResource;

    private ProductModelMapper mapper;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        when(stringResource.getString(ProductModelMapper.EACH)).thenReturn(EACH);
        when(stringResource.getString(ProductModelMapper.UNIT)).thenReturn(UNIT);
        mapper = new ProductModelMapper(stringResource);
    }

    @Test
    public void testMapUp() throws Exception {
        ProductDomain domain = new ProductDomain();
        domain.setCurrency(CurrencyDomain.GBP);
        domain.setName(NAME);
        domain.setSinglePrice(1.2f);
        domain.setUnitPrice(1.5f);
        domain.setImageUrl(IMAGE_URL);
        ProductModel model = mapper.mapUp(domain);
        assertEquals(NAME, model.getName());
        assertEquals(0, model.getQuantity());
        verify(stringResource).getString(ProductModelMapper.EACH);
        assertEquals("£1,20/" + EACH, model.getSinglePrice());
        verify(stringResource).getString(ProductModelMapper.UNIT);
        assertEquals("£1,50/" + UNIT, model.getUnitPrice());
        assertEquals(IMAGE_URL, model.getImageUrl());
    }

    @Test
    public void testMapUp_null() throws Exception {
        ProductDomain domain = null;
        ProductModel model = mapper.mapUp(domain);
        assertNull(model);
        verifyNoMoreInteractions(stringResource);
    }

    @Test
    public void testMapDown() throws Exception {
        ProductModel model = new ProductModel();
        model.setName(NAME);
        model.setUnitPrice("£1.20/" + EACH);
        model.setSinglePrice("£1.50/" + UNIT);
        model.setImageUrl(IMAGE_URL);
        model.setQuantity(2);
        ProductDomain domain = mapper.mapDown(model);
        assertEquals(NAME, domain.getName());
        assertEquals(1.2f, domain.getUnitPrice());
        assertEquals(1.5f, domain.getSinglePrice());
        assertEquals(CurrencyDomain.GBP, domain.getCurrency());
    }

    @Test
    public void testMapDown_weirdSinglePrice() throws Exception {
        ProductModel model = new ProductModel();
        model.setName(NAME);
        model.setUnitPrice(WEIRD_UNIT_PRICE);
        model.setSinglePrice("£1.50/" + UNIT);
        model.setImageUrl(IMAGE_URL);
        model.setQuantity(2);
        ProductDomain domain = mapper.mapDown(model);
        assertEquals(NAME, domain.getName());
        assertEquals(0.0f, domain.getUnitPrice());
        assertEquals(1.5f, domain.getSinglePrice());
        assertEquals(CurrencyDomain.GBP, domain.getCurrency());
    }

    @Test
    public void testMapDown_weirdUnitPrice() throws Exception {
        ProductModel model = new ProductModel();
        model.setName(NAME);
        model.setUnitPrice("£1.20/" + EACH);
        model.setSinglePrice(WEIRD_SINGLE_PRICE);
        model.setImageUrl(IMAGE_URL);
        model.setQuantity(2);
        ProductDomain domain = mapper.mapDown(model);
        assertEquals(NAME, domain.getName());
        assertEquals(1.2f, domain.getUnitPrice());
        assertEquals(0.0f, domain.getSinglePrice());
        assertEquals(CurrencyDomain.GBP, domain.getCurrency());
    }

    @Test
    public void testMapDown_null() throws Exception {
        ProductModel model = null;
        ProductDomain domain = mapper.mapDown(model);
        assertNull(domain);
    }
}