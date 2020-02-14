package com.example.myfood.abstracts.presenter;

import android.view.View;

public class BasePresenter implements BaseMvpPresenter {

    private View view;
    private boolean isAttached = false;

    @Override
    public void attach(View view) {
        this.view = view;
        isAttached = true;
    }

    @Override
    public void detach() {
        view = null;
        isAttached = false;
    }
}
