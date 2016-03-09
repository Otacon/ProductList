package com.sainsbury.productlist.utils;

import android.content.Context;

/**
 * Created by Orfeo Ciano on 09/03/2016.
 */
public class StringResourceWrapper implements IStringResource {

    private final Context context;

    public StringResourceWrapper(Context context){
        this.context = context;
    }

    @Override
    public String getString(int resourceId) {
        return context.getString(resourceId);
    }
}
