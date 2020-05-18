package com.example.myfood.components.login.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    Fragment fragmentNow, fragment3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        presenter = new LoginPresenter();
        presenter.attach(this);
        fragment1 = new LoginFirstFragment();
        replaceFragment(fragment1, false);
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void onClick(int id) {
        presenter.onClickButton(id);
    }

    @Override
    public void onClickLogin(String email, String password) {
        presenter.login(email, password);
    }

    @Override
    public void onClickSignUpFirst(String email, String password, String school, String name, String numberClass) {
        presenter.signUpFirst(email, password, school, name, numberClass);
    }

    @Override
    public void onClickSignUpSecond(String toString, String toString1, String toString2, String toString3) {
        presenter.signUpSecond(toString, toString1, toString2, toString3);
    }

    @Override
    public void showLogInFragment() {
        fragment2 = new LogInSecondFragment();
        replaceFragment(fragment2, true);
    }

    @Override
    public void showSignUpFirstFragment() {
        fragment3 = new SignUpFirstFragment();
        replaceFragment(fragment3, true);
    }

    @Override
    public void nextSceenAfterLogIn(String string) {
        SharedPreferences sharedPreferences = getSharedPreferences("token", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", string);
        editor.apply();
        startActivity(new Intent(this, CheckActivity.class));
    }

    @Override
    public void nextSceenAfterSignUp() {
        startActivity(new Intent(this, CheckActivity.class));
    }

    @Override
    public void showSignUpFirstTypeFragment() {
        Fragment fragment4 = new SignUpFirstTypeFragment();
        replaceFragment(fragment4, true);
    }

    @Override
    public void showSignUpSecondTypeFragment() {
        Fragment fragment5 = new SignUpSecondTypeFragment();
        replaceFragment(fragment5, true);
    }

    @Override
    public void previousFragment() {
        getSupportFragmentManager().popBackStack();
    }

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
