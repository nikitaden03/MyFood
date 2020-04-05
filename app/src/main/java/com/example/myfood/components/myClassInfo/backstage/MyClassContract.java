package com.example.myfood.components.myClassInfo.backstage;

import com.example.myfood.abstracts.presenter.BaseMvpPresenter;
import com.example.myfood.abstracts.view.BaseMvpView;
import com.example.myfood.data.User;

import java.util.ArrayList;

public interface MyClassContract {
    interface Presenter extends BaseMvpPresenter {
        ArrayList<String> getInformMyClass(User user);
    }
    interface View extends BaseMvpView {
        void openMenu(android.view.View view);
        void showMyClass();
    }
}