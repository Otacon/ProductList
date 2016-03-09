package com.sainsbury.productlist.controller;
/*
Basic interface for lifecycle components (Activity, Fragment)
 */
public interface IController {

    void onCreate();

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();
}
