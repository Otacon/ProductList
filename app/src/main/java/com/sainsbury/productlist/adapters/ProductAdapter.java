package com.sainsbury.productlist.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sainsbury.productlist.R;
import com.sainsbury.productlist.mvp.IProductItemPresenter;
import com.sainsbury.productlist.mvp.IProductItemView;
import com.sainsbury.productlist.mvp.ProductItemView;
import com.sainsbury.productlist.mvp.ProductModel;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<ProductModel> models;

    /*
    Inflating the custom view
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        IProductItemView view = (ProductItemView) inflater.inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    /*
    Binding the custom view with the view holder and setting presenter's data
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        IProductItemView view = (ProductItemView) holder.itemView;
        IProductItemPresenter presenter = view.getPresenter();
        presenter.setModel(models.get(position));
        presenter.run();
    }

    /*
    Setting the data and refreshing the adapter
     */
    public void setModels(List<ProductModel> models) {
        this.models = models;
        notifyDataSetChanged();
    }

    /*
    returning the correct item count with nullcheck
     */
    @Override
    public int getItemCount() {
        return models != null ? models.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(IProductItemView itemView) {
            super((View) itemView);
        }
    }
}
