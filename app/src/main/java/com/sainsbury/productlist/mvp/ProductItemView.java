package com.sainsbury.productlist.mvp;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sainsbury.productlist.R;
import com.squareup.picasso.Picasso;

public class ProductItemView extends CardView implements IProductItemView {

    private IProductItemPresenter presenter;
    private ImageView thumbnail;
    private TextView description;
    private TextView unitPrice;
    private TextView singlePrice;
    private Button plus;
    private Button minus;
    private TextView count;

    public ProductItemView(Context context) {
        super(context);
    }

    public ProductItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProductItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        thumbnail = (ImageView) findViewById(R.id.thumbnail);
        description = (TextView) findViewById(R.id.description);
        unitPrice = (TextView) findViewById(R.id.unit_price);
        singlePrice = (TextView) findViewById(R.id.single_price);
        count = (TextView) findViewById(R.id.count);
        plus = (Button) findViewById(R.id.plus);
        plus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onPlusClicked();
            }
        });
        minus = (Button) findViewById(R.id.minus);
        minus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onMinusClicked();
            }
        });
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (!isInEditMode()) {
            init();
            presenter = new ProductItemPresenter();
            presenter.setView(this);
        }
    }

    @Override
    public IProductItemPresenter getPresenter() {
        return presenter;
    }

    public void setName(String name) {
        this.description.setText(name);
    }

    /*
    For convenience I'm using Picasso:
    it's a must-have since it handles in an elegant and clean way image downloading and caching.
    No more dirty code or memory leaks or LRU Cache managment.
     */
    @Override
    public void setImage(String imageUrl) {
        Picasso.with(getContext()).load(imageUrl).into(thumbnail);
    }

    @Override
    public void setSinglePrice(String singlePrice) {
        this.singlePrice.setText(singlePrice);
    }

    @Override
    public void setUnitPrice(String unitPrice) {
        this.unitPrice.setText(unitPrice);
    }

    @Override
    public void showMinus(boolean isVisible) {
        minus.setVisibility(isVisible ? VISIBLE : INVISIBLE);
    }

    @Override
    public void setQuantity(String s) {
        this.count.setText(s);
    }


}
