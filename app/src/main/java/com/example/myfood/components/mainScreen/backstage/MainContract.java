package com.example.myfood.components.mainScreen.backstage;

import com.example.myfood.abstracts.presenter.BaseMvpPresenter;
import com.example.myfood.abstracts.view.BaseMvpView;
import com.example.myfood.data.models.User;

public interface MainContract {
    interface Presenter extends BaseMvpPresenter{
    }
    interface View extends BaseMvpView {
        void openMenu(android.view.View v);
        void setUserData(User user);
    }
}
