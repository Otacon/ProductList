package com.sainsbury.productlist.mappers;

import com.sainsbury.productlist.R;
import com.sainsbury.productlist.domain.CurrencyDomain;
import com.sainsbury.productlist.domain.ProductDomain;
import com.sainsbury.productlist.mvp.ProductModel;
import com.sainsbury.productlist.utils.IStringResource;

import java.text.DecimalFormat;
/*
Mapper to translate from a Domain Product coming from network to a Model Product.
This is meant to be used for rendering purposes.
 */
public class ProductModelMapper extends Mapper<ProductDomain, ProductModel> {

    static final int EACH = R.string.ea;
    static final int UNIT = R.string.unit;
    private final IStringResource stringResource;

    public ProductModelMapper(IStringResource stringResource) {
        this.stringResource = stringResource;
    }

    @Override
    public ProductModel mapUp(ProductDomain domain) {
        if (domain == null) {
            return null;
        }
        String name = domain.getName();
        DecimalFormat df = new DecimalFormat("0.00");
        String singlePrice = domain.getCurrency().symbol + df.format(domain.getSinglePrice()) + "/" + stringResource.getString(EACH);
        String unitPrice = domain.getCurrency().symbol + df.format(domain.getUnitPrice()) + "/" + stringResource.getString(UNIT);
        ProductModel model = new ProductModel();
        model.setName(name);
        model.setQuantity(0);
        model.setSinglePrice(singlePrice);
        model.setUnitPrice(unitPrice);
        model.setImageUrl(domain.getImageUrl());
        return model;
    }

    @Override
    public ProductDomain mapDown(ProductModel model) {
        if (model == null) {
            return null;
        }

        ProductDomain domain = new ProductDomain();

        domain.setName(model.getName());

        try {
            String unitPrice = model.getUnitPrice().replaceAll("[^\\d.]", "");
            domain.setUnitPrice(Float.parseFloat(unitPrice));
        } catch (NullPointerException | NumberFormatException e) {
            domain.setUnitPrice(0.0f);
        }

        try {
            String singlePrice = model.getSinglePrice().replaceAll("[^\\d.]", "");
            domain.setSinglePrice(Float.parseFloat(singlePrice));
        } catch (NullPointerException | NumberFormatException e) {
            domain.setSinglePrice(0.0f);
        }

        domain.setCurrency(CurrencyDomain.GBP);
        domain.setImageUrl(model.getImageUrl());
        return domain;
    }
}
