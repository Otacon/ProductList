package com.sainsbury.productlist.mappers;

import com.sainsbury.productlist.domain.CurrencyDomain;
import com.sainsbury.productlist.domain.ProductDomain;
import com.sainsbury.productlist.network.ProductDTO;
/*
Mapper to translate from a Data Transfer Object (DTO) Product coming from network to a Domain Product.
 */
public class ProductDTOMapper extends Mapper<ProductDTO, ProductDomain> {
    @Override
    public ProductDomain mapUp(ProductDTO dto) {
        if (dto == null) {
            return null;
        }

        ProductDomain domain = new ProductDomain();

        domain.setName(dto.getName());
        domain.setImageUrl(dto.getImageUrl());

        try {
            String unitPrice = dto.getUnitPrice().replaceAll("[^\\d.]", "");
            domain.setUnitPrice(Float.parseFloat(unitPrice));
        } catch (NullPointerException | NumberFormatException e) {
            domain.setUnitPrice(0.0f);
        }

        try {
            String singlePrice = dto.getSinglePrice().replaceAll("[^\\d.]", "");
            domain.setSinglePrice(Float.parseFloat(singlePrice));
        } catch (NullPointerException | NumberFormatException e) {
            domain.setSinglePrice(0.0f);
        }
        //This can be improved by parsing the currency
        domain.setCurrency(CurrencyDomain.GBP);
        return domain;
    }

    @Override
    public ProductDTO mapDown(ProductDomain domain) {
        if (domain == null) {
            return null;
        }
        String name = domain.getName();
        String imageUrl = domain.getImageUrl();
        String currency = domain.getCurrency() != null ? domain.getCurrency().symbol : "";
        String unitPrice = currency + domain.getUnitPrice();
        String singlePrice = currency + domain.getSinglePrice();
        return new ProductDTO(name, imageUrl, unitPrice, singlePrice);
    }
}
