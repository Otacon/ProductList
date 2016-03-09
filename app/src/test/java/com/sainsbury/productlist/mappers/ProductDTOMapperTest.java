package com.sainsbury.productlist.mappers;

import com.sainsbury.productlist.domain.CurrencyDomain;
import com.sainsbury.productlist.domain.ProductDomain;
import com.sainsbury.productlist.network.ProductDTO;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

public class ProductDTOMapperTest {

    private static final String UNIT_PRICE = "£1.2";
    private static final String WEIRD_UNIT_PRICE = "qwe";
    private static final String SINGLE_PRICE = "£1.5";
    private static final String WEIRD_SINGLE_PRICE = "asd";
    private static final String IMAGE_URL = "imageUrl";
    private static final String NAME = "name";

    private ProductDTOMapper mapper;

    @Before
    public void setUp() throws Exception {
        this.mapper = new ProductDTOMapper();
    }

    @Test
    public void testMapUp() throws Exception {
        ProductDomain domain = mapper.mapUp(new ProductDTO(NAME, IMAGE_URL, UNIT_PRICE, SINGLE_PRICE));
        assertEquals(NAME, domain.getName());
        assertEquals(IMAGE_URL, domain.getImageUrl());
        assertEquals(1.2f, domain.getUnitPrice());
        assertEquals(1.5f, domain.getSinglePrice());
        assertEquals(CurrencyDomain.GBP, domain.getCurrency());
    }

    @Test
    public void testMapUp_null() throws Exception {
        ProductDTO productDTO = null;
        ProductDomain domain = mapper.mapUp(productDTO);
        assertNull(domain);
    }

    @Test
    public void testMapUp_weirdUnitPrice() throws Exception {
        ProductDomain domain = mapper.mapUp(new ProductDTO(NAME, IMAGE_URL, WEIRD_UNIT_PRICE, SINGLE_PRICE));
        assertEquals(NAME, domain.getName());
        assertEquals(IMAGE_URL, domain.getImageUrl());
        assertEquals(0.0f, domain.getUnitPrice());
        assertEquals(1.5f, domain.getSinglePrice());
        assertEquals(CurrencyDomain.GBP, domain.getCurrency());
    }

    @Test
    public void testMapUp_weirdSinglePrice() throws Exception {
        ProductDomain domain = mapper.mapUp(new ProductDTO(NAME, IMAGE_URL, UNIT_PRICE, WEIRD_SINGLE_PRICE));
        assertEquals(NAME, domain.getName());
        assertEquals(IMAGE_URL, domain.getImageUrl());
        assertEquals(1.2f, domain.getUnitPrice());
        assertEquals(0.0f, domain.getSinglePrice());
        assertEquals(CurrencyDomain.GBP, domain.getCurrency());
    }

    @Test
    public void testMapDown_null() throws Exception {
        ProductDomain domain = null;
        ProductDTO productDTO = mapper.mapDown(domain);
        assertNull(productDTO);
    }

    @Test
    public void testMapDown() throws Exception {
        ProductDomain domain = new ProductDomain();
        domain.setName(NAME);
        domain.setImageUrl(IMAGE_URL);
        domain.setCurrency(CurrencyDomain.GBP);
        domain.setUnitPrice(1.1f);
        domain.setSinglePrice(1.2f);
        ProductDTO productDTO = mapper.mapDown(domain);
        assertEquals(NAME,productDTO.getName());
        assertEquals(IMAGE_URL, productDTO.getImageUrl());
        assertEquals("£1.1",productDTO.getUnitPrice());
        assertEquals("£1.2",productDTO.getSinglePrice());

    }
}