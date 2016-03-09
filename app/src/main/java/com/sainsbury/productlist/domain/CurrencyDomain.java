package com.sainsbury.productlist.domain;


public enum CurrencyDomain {
    /*
    The name of the curency could be retrieved from strings.xml for internationalization purposes
     */
    GBP("British Pound","£"),
    USD("US Dollar","£");

    public String name;
    public String symbol;

    CurrencyDomain(String name, String symbol){
        this.symbol = symbol;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return "CurrencyDomain{" +
                "name='" + name + '\'' +
                ", symbol='" + symbol + '\'' +
                '}';
    }
}
