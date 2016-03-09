package com.sainsbury.productlist;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sainsbury.productlist.adapters.ProductAdapter;
import com.sainsbury.productlist.controller.IMainActivityController;
import com.sainsbury.productlist.controller.IMainActivityView;
import com.sainsbury.productlist.controller.MainActivityController;
import com.sainsbury.productlist.mappers.ProductDTOMapper;
import com.sainsbury.productlist.mappers.ProductModelMapper;
import com.sainsbury.productlist.mvp.ProductModel;
import com.sainsbury.productlist.network.IWebViewHolder;
import com.sainsbury.productlist.network.WebViewNetworkInteractor;
import com.sainsbury.productlist.utils.StringResourceWrapper;

import java.util.ArrayList;
import java.util.Collection;

public class MainActivity extends AppCompatActivity implements IMainActivityView, SwipeRefreshLayout.OnRefreshListener, IWebViewHolder {

    IMainActivityController controller;

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private SwipeRefreshLayout refreshLayout;
    //Webview to download page and runs the inner javascript.
    private WebView webview;
    private IWebViewHandler webViewHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.product_recycler_view);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        refreshLayout.setOnRefreshListener(this);
        controller = new MainActivityController(
                this,
                new StringResourceWrapper(this),
                new ProductModelMapper(new StringResourceWrapper(this)),
                new WebViewNetworkInteractor(this, new ProductDTOMapper()));

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ProductAdapter();
        recyclerView.setAdapter(adapter);

        webview = (WebView) findViewById(R.id.browser);
    }

    @Override
    protected void onStart() {
        super.onStart();
        controller.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        controller.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        controller.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        controller.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        controller.onDestroy();
    }

    /*
    This is called by the AsynchronousNetworkInteractor
     */
    @Override
    public void setProducts(final Collection<ProductModel> productModels) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.setModels(new ArrayList<>(productModels));
            }
        });
    }

    /*
    using "post" to fix a but in the refreshLayoout that prevents showind the spinning wheel
    the first time the screen has been loaded.
     */
    @Override
    public void setLoading(final boolean isLoading) {
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(isLoading);
            }
        });
    }

    /*
    This is called by the AsynchronousNetworkInteractor
     */
    @Override
    public void showError(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                View view = getWindow().getDecorView().findViewById(android.R.id.content);
                Snackbar snackbar = Snackbar
                        .make(view, s, Snackbar.LENGTH_LONG)
                        .setAction(R.string.retry, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                controller.onRefresh();
                            }
                        });

                snackbar.show();
            }
        });
    }

    @Override
    public void onRefresh() {
        controller.onRefresh();
    }

    /*
    This is called by the AsynchronousNetworkInteractor in order to start downloading a page.
    After the page has been loaded the source will be sent back to the AsynchronousNetworkInteractor
     */
    @Override
    public void getHtml(String page, IWebViewHandler handler) {
        this.webViewHandler = handler;
        webview.getSettings().setJavaScriptEnabled(true);
        webview.addJavascriptInterface(new JavsscriptInterface(this), "HtmlViewer");

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                webview.loadUrl("javascript:window.HtmlViewer.showHTML" +
                        "('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                webViewHandler.onError();
            }
        });

        webview.loadUrl(page);
    }

    /*
    Class that takes care of notifying the NetowrkInteractor that the HTML has been downloaded
     */
    class JavsscriptInterface {

        private Context ctx;

        JavsscriptInterface(Context ctx) {
            this.ctx = ctx;
        }

        @JavascriptInterface
        public void showHTML(String html) {
            if (webViewHandler != null) {
                webViewHandler.onHtmlDownloaded(html);
            }
        }

    }
}
