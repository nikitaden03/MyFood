package com.example.myfood.abstracts.presenter;

import com.example.myfood.abstracts.view.BaseCompatActivity;

public interface BaseMvpPresenter <T extends BaseCompatActivity>{
    void attach(T view);
}
