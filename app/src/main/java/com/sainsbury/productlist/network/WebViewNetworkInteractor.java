package com.sainsbury.productlist.network;

import com.sainsbury.productlist.domain.ProductDomain;
import com.sainsbury.productlist.mappers.Mapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WebViewNetworkInteractor implements IAsyncNetworkInteractor<Collection<ProductDomain>> {

    static final String page = "http://www.sainsburys.co.uk/webapp/wcs/stores/servlet/CategoryDisplay?listView=true&orderBy=FAVOURITES_FIRST&parent_category_rn=12518&top_category=12518&langId=44&beginIndex=0&pageSize=20&catalogId=10137&searchTerm=&categoryId=185749&listId=&storeId=10151&promotionId=#langId=44&storeId=10151&catalogId=10137&categoryId=185749&parent_category_rn=12518&top_category=12518&pageSize=20&orderBy=FAVOURITES_FIRST&searchTerm=&beginIndex=0&hideFilters=true";
    final IWebViewHolder holder;
    final Mapper<ProductDTO, ProductDomain> productDTOMapper;
    ConsumerHandler<Collection<ProductDomain>> consumerHandler;
    /*
    Once we got the HTML source we start to parse it and then map the network object to
    a domain format. If there is an exception/error, we propagate ti through the onError method
     */
    IWebViewHolder.IWebViewHandler handler = new IWebViewHolder.IWebViewHandler() {
        @Override
        public void onHtmlDownloaded(String html) {
            Collection<ProductDTO> response = parseHtml(html);
            if (response != null && consumerHandler != null) {
                consumerHandler.onResponse(productDTOMapper.mapUp(response));
            }
        }

        @Override
        public void onError() {
            consumerHandler.onError(null);
        }
    };

    /*
    Dependency injection of the webview inside the component in order to download through javascript
     the whole HTML source
     */
    public WebViewNetworkInteractor(IWebViewHolder holder, Mapper<ProductDTO, ProductDomain> productDTOMapper) {
        this.holder = holder;
        this.productDTOMapper = productDTOMapper;
    }


    /*
    Delegate the request to the android webview on the activity
    */
    @Override
    public void get(final ConsumerHandler<Collection<ProductDomain>> handler) {
        try {
            this.consumerHandler = handler;
            holder.getHtml(page, this.handler);
        } catch (Exception e) {
            handler.onError(e);
        }
    }

    /*
    It is possible to run tests using html files as source, but it takes time and configuration
    so I'll leave this horrible hack untested, trying to test the whole behaviour of the component
    */
    private List<ProductDTO> parseHtml(String html) {

        Document doc = Jsoup.parse(html);
        Elements products = doc.getElementsByClass("product");
        if (products.isEmpty()) {
            return null;
        }
        List<ProductDTO> productDTOs = new ArrayList<>();
        for (Element product : products) {
            Elements productName = product.getElementsByClass("productName");
            if (productName.isEmpty()) {
                continue;
            }
            Element h3 = productName.get(0);
            if (h3 == null || h3.children().isEmpty()) {
                continue;
            }
            Element ahref = h3.child(0);
            String name = Jsoup.parse(ahref.childNodes().get(0).toString()).text();
            String imageUrl = "http://sainsburys.co.uk" + ahref.childNodes().get(1).attributes().get("src");

            Elements pricePerUnit = product.getElementsByClass("pricePerUnit");
            if (pricePerUnit.isEmpty()) {
                continue;
            }
            String unitPrice = pricePerUnit.get(0).childNodes().get(0).toString();

            Elements pricePerMeasure = product.getElementsByClass("pricePerMeasure");
            if (pricePerMeasure.isEmpty()) {
                continue;
            }

            String singlePrice = pricePerMeasure.get(0).childNodes().get(0).toString();
            productDTOs.add(new ProductDTO(name, imageUrl, unitPrice, singlePrice));
        }
        return productDTOs;
    }
}
