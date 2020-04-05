package com.example.myfood.components.teacherFood.backstage;

import com.example.myfood.abstracts.presenter.BaseMvpPresenter;
import com.example.myfood.abstracts.view.BaseMvpView;

import java.util.ArrayList;

public interface TeacherFoodContract {
    interface Presenter extends BaseMvpPresenter {
        ArrayList<String[]> getData();
        ArrayList<String[]> getDataNext();
        ArrayList<String[]> getDataPrevious();
        Boolean hasNext();
        Boolean hasPrevious();
        String getCurrentMonth();
        String getCursor();
        void cursorUp();
        void cursorDown();
    }
    interface View extends BaseMvpView {
        void openMenu(android.view.View view);
        void setFoodList();
        void changeButton();
    }
}
