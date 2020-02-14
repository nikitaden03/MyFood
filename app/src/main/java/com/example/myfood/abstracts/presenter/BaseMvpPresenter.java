package com.example.myfood.abstracts.presenter;

import android.view.View;

public interface BaseMvpPresenter {
    boolean isAttached = false;
    void attach(View view);
    void detach();
}
