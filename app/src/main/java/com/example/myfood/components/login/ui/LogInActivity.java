package com.example.myfood.components.login.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myfood.CheckActivity;
import com.example.myfood.R;
import com.example.myfood.abstracts.view.BaseCompatActivity;
import com.example.myfood.components.login.backstage.LoginContract;
import com.example.myfood.components.login.backstage.LoginPresenter;

public class LogInActivity extends BaseCompatActivity implements LoginContract.View, UiContract.View {

    LoginPresenter presenter;
    LoginFirstFragment fragment1;
    LogInSecondFragment fragment2;
    SignUpFirstFragment fragment3;
    Fragment fragmentNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        // Находит нужный presenter и прикрепляется к нему
        presenter = new LoginPresenter();
        presenter.attach(this);

        fragment1 = new LoginFirstFragment();
        replaceFragment(fragment1, false);
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    // Передает все клики по кнопкам в обработчик, который находится в presenter
    @Override
    public void onClick(int id) {
        presenter.onClickButton(id);
    }

    // Передает клик по кнопке "авторизоваться" в обработчик в presenter
    @Override
    public void onClickLogin(String email, String password) {
        presenter.login(email, password);
    }

    // Передает клик по кнопке "зарегистрироваться, как дежурный" в обработчик в presenter
    @Override
    public void onClickSignUpFirst(String email, String password, String school, String name, String numberClass) {
        presenter.signUpFirst(email, password, school, name, numberClass);
    }

    // Передает клик по кнопке "зарегистрироваться, как питающийся" в обработчик в presenter
    @Override
    public void onClickSignUpSecond(String email, String password, String name, String ink) {
        presenter.signUpSecond(email, password, name, ink);
    }

    // Перенаправляет пользователя на фрагмент с авторизацией
    @Override
    public void showLogInFragment() {
        fragment2 = new LogInSecondFragment();
        replaceFragment(fragment2, true);
    }

    // Перенаправляет пользователя на фрагмент с выбором типа аккаунта
    @Override
    public void showSignUpFirstFragment() {
        fragment3 = new SignUpFirstFragment();
        replaceFragment(fragment3, true);
    }

    // Перенаправляет пользователя на CheckActivity
    @Override
    public void ReloadApp() {
        startActivity(new Intent(this, CheckActivity.class));
    }

    // Перенаправляет пользователя на фрагмент с регистрацией для аккаунта типа дежурный
    @Override
    public void showSignUpFirstTypeFragment() {
        Fragment fragment4 = new SignUpFirstTypeFragment();
        replaceFragment(fragment4, true);
    }

    // Перенаправляет пользователя на фрагмент с регистрацией для аккаунта типа питающийся
    @Override
    public void showSignUpSecondTypeFragment() {
        Fragment fragment5 = new SignUpSecondTypeFragment();
        replaceFragment(fragment5, true);
    }

    // Перенаправляет пользователя на прошлый фрагмент
    @Override
    public void previousFragment() {
        getSupportFragmentManager().popBackStack();
    }

    // Добавляет новый фрагмент в стэк фрагментов
    @Override
    public void replaceFragment(Fragment fragmentAdd, boolean isAddBackStage) {
        Log.d("MYTAG", "Replace or add fragment");
        fragmentNow = fragmentAdd;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.log_in_activity, fragmentAdd);
        if (isAddBackStage) fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public Fragment getFragmentNow() {
        return fragmentNow;
    }

    public Fragment getFragment3() {
        return fragment3;
    }
}
