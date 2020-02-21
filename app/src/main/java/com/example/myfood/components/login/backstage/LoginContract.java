package com.example.myfood.components.login.backstage;

import androidx.fragment.app.Fragment;

import com.example.myfood.abstracts.presenter.BaseMvpPresenter;
import com.example.myfood.abstracts.view.BaseMvpView;

public interface LoginContract {
    interface Presenter extends BaseMvpPresenter {
        void onClickButton(int id);
        void login(String email, String password);
        void signUpFirst(String email, String password, String school, String name, String numberClass);
        void signUpSecond(String email, String password, String name, String ink);
    }
    interface View extends BaseMvpView {
        void replaceFragment(Fragment fragmentAdd, boolean isAddBackStage);
        void previousFragment();
        void showLogInFragment();
        void showSignUpFirstTypeFragment();
        void showSignUpSecondTypeFragment();
        void showSignUpFirstFragment();
        Fragment getFragmentNow();
        Fragment getFragment3();
    }
}
