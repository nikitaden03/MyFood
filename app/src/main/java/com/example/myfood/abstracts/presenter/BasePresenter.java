package com.example.myfood.abstracts.presenter;

import com.example.myfood.abstracts.view.BaseCompatActivity;

public class BasePresenter <T extends BaseCompatActivity> implements BaseMvpPresenter <T>{

    public T view;
    private boolean isAttached = false;

    @Override
    public void attach(T view) {
        this.view = view;
        isAttached = true;
    }

    @Override
    public void detach() {
        view = null;
        isAttached = false;
    }
}
