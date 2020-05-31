package com.example.myfood.components.studentFood.backstage;

import com.example.myfood.abstracts.presenter.BaseMvpPresenter;
import com.example.myfood.abstracts.view.BaseMvpView;

public interface StudentFoodContract {
    interface Presenter extends BaseMvpPresenter {
        Boolean hasNext();
        Boolean hasPrevious();
        String getCurrentMonthAndYear();
        String getCursor();
        void cursorUp();
        void cursorDown();
        String[] getData();
        void sendFood(String breakfast, String teatime, String lunch);
        Boolean hasTodayData();
        void prepareData();
    }
    interface View extends BaseMvpView {
        void changeButton();
    }
}
