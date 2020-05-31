package com.example.myfood.components.settings.backstage;

import android.content.Context;

import com.example.myfood.abstracts.presenter.BaseMvpPresenter;
import com.example.myfood.abstracts.view.BaseMvpView;
import com.example.myfood.data.models.User;

public interface SettingsContract  {
    interface Presenter extends BaseMvpPresenter {
        void onClick(android.view.View view);
    }
    interface View extends BaseMvpView {
        void openMenu(android.view.View view);
        void setPriceData(User user);
        void onClick(android.view.View view);
        String getBreakfastPrice();
        String getLunchPrice();
        String getTeatimePrice();
        void showBreakfastPriceAlert(String text);
        void showLunchPriceAlert(String text);
        void showTeatimePriceAlert(String text);
        void hideAllAlerts();
        User getUser();
        void setPricesData(int a, int b, int c);
        Context getContext();
        void leaveAccount();
    }
}
