package com.example.myfood.components.teacherFood.backstage;

import android.content.Context;

import com.example.myfood.abstracts.presenter.BaseMvpPresenter;
import com.example.myfood.abstracts.view.BaseMvpView;

import java.util.ArrayList;

public interface TeacherFoodContract {
    interface Presenter extends BaseMvpPresenter {
        ArrayList<String[]> getData();
        Boolean hasNext();
        Boolean hasPrevious();
        String getCurrentMonthAndYear();
        String getCursor();
        void cursorUp();
        void cursorDown();
        void prepareData();
    }
    interface View extends BaseMvpView {
        void setFoodList();
        void changeButton();
        Context getContext();
    }
}
