package com.sainsbury.productlist.domain;
/*
Product Domain class. A sort of "universal interchange format" to have a common language of communication
 between different components. It also allows to make manipulations and calculations (i.e. prices)
 */
public class ProductDomain {
    protected String name;
    protected String imageUrl;
    protected CurrencyDomain currency;
    protected float unitPrice;
    protected float singlePrice;

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public CurrencyDomain getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyDomain currency) {
        this.currency = currency;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public float getSinglePrice() {
        return singlePrice;
    }

    public void setSinglePrice(float singlePrice) {
        this.singlePrice = singlePrice;
    }

    @Override
    public String toString() {
        return "ProductDomain{" +
                "name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", currency=" + currency +
                ", unitPrice=" + unitPrice +
                ", singlePrice=" + singlePrice +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductDomain that = (ProductDomain) o;

        if (Float.compare(that.unitPrice, unitPrice) != 0) return false;
        if (Float.compare(that.singlePrice, singlePrice) != 0) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (imageUrl != null ? !imageUrl.equals(that.imageUrl) : that.imageUrl != null)
            return false;
        return currency == that.currency;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (unitPrice != +0.0f ? Float.floatToIntBits(unitPrice) : 0);
        result = 31 * result + (singlePrice != +0.0f ? Float.floatToIntBits(singlePrice) : 0);
        return result;
    }
}
