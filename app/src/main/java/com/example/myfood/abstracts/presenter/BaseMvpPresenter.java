package com.example.myfood.abstracts.presenter;

import com.example.myfood.abstracts.view.BaseCompatActivity;

public interface BaseMvpPresenter <T extends BaseCompatActivity>{
    boolean isAttached = false;
    void attach(T view);
    void detach();
}
