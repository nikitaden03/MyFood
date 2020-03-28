package com.example.myfood.components.myINK.backstage;

import com.example.myfood.abstracts.presenter.BaseMvpPresenter;
import com.example.myfood.abstracts.view.BaseMvpView;

public interface MyInkContract {
    interface Presenter extends BaseMvpPresenter {
        void toCopy(String ink);
    }
    interface View extends BaseMvpView {
        void openMenu(android.view.View view);
        void showToast();
    }
}
