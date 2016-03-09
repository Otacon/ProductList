package com.sainsbury.productlist.network;
/*
The class that implements this interface is the one that has a webview and is able to
download a webpage, run the javascript and gives back the page html.
 */
public interface IWebViewHolder {
    void getHtml(String page, IWebViewHandler handler);

    interface IWebViewHandler {
        void onHtmlDownloaded(String html);
        void onError();
    }
}
